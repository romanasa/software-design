package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import search.SearchEngineResult;
import search.StubSearchClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MasterActor extends AbstractActor {
    private final List<SearchEngineResult> resultList;
    private ActorRef mainSender;
    private final Duration timeout;
    private final List<String> SEARCH_ENGINES = List.of("google", "yandex", "bing");
    private static final TimeoutMessage TIMEOUT_MESSAGE = new TimeoutMessage();

    public MasterActor(Duration timeout) {
        this.timeout = timeout;
        this.resultList = new ArrayList<>();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg -> {
                    mainSender = getSender();
                    SEARCH_ENGINES.forEach(searchEngine -> createChildActor(searchEngine, msg));
                    context().system().scheduler().scheduleOnce(
                            timeout,
                            () -> self().tell(TIMEOUT_MESSAGE, ActorRef.noSender()),
                            context().system().dispatcher());

                })
                .match(TimeoutMessage.class, msg -> sendResult())
                .match(SearchEngineResult.class, msg -> {
                    resultList.add(msg);
                    if (resultList.size() == SEARCH_ENGINES.size()) {
                        sendResult();
                    }
                })
                .build();
    }

    private void createChildActor(String searchEngine, String request) {
        getContext().actorOf(Props.create(ChildActor.class, new StubSearchClient(searchEngine)),
                searchEngine).tell(request, self());
    }

    private void sendResult() {
        mainSender.tell(new SearchResult(resultList), self());
        getContext().stop(self());
    }

    public static final class SearchResult {
        final List<SearchEngineResult> results;

        public SearchResult(List<SearchEngineResult> results) {
            this.results = results;
        }

        public List<SearchEngineResult> getResults() {
            return results;
        }
    }

    private static final class TimeoutMessage {
    }
}
