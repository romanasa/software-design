package actors;

import akka.actor.AbstractActor;
import com.google.gson.Gson;
import search.SearchClient;
import search.SearchEngineResult;

import java.util.Map;

public class ChildActor extends AbstractActor {
    private final Map<String, Long> sleepDuration = Map.of(
            "Google", 500L,
            "Yandex", 600L,
            "Bing", 700L);

    SearchClient client;

    public ChildActor(SearchClient client) {
        this.client = client;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, this::getResult)
                .build();
    }

    private void getResult(String request) {
        String resultJson = client.makeRequest(request);
        Gson g = new Gson();
        SearchEngineResult result = g.fromJson(resultJson, SearchEngineResult.class);

        try {
            Thread.sleep(sleepDuration.get(result.getEngineName()));
            getSender().tell(result, getSender());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
