import actors.MasterActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import search.SearchEngineResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import static akka.pattern.Patterns.ask;

public class MasterActorTest {
    private static final Duration TIMEOUT_1 = Duration.ofMillis(1000);
    private static final Duration TIMEOUT_2 = Duration.ofMillis(600);

    private static ActorSystem system;
    private static Gson gson;

    @BeforeAll
    public static void setup() {
        system = ActorSystem.create();
        gson = new Gson();
    }

    @AfterAll
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testMakeRequest() {
        new TestKit(system) {
            {
                final ActorRef master = system.actorOf(Props.create(MasterActor.class, TIMEOUT_1));
                Object response = ask(
                        master,
                        "some_request",
                        Duration.ofMinutes(1))
                        .toCompletableFuture()
                        .join();

                MasterActor.SearchResult result = (MasterActor.SearchResult) response;
                MasterActor.SearchResult expectedResult = getExpectedResult();

                var got = result.getResults();
                var expected = expectedResult.getResults();

                Assertions.assertEquals(3, got.size());
                Assertions.assertTrue(got.size() == expected.size() &&
                        got.containsAll(expected) && expected.containsAll(got));
            }
        };
    }

    @Test
    public void testWithTimeout() {
        new TestKit(system) {
            {
                final ActorRef master = system.actorOf(Props.create(MasterActor.class, TIMEOUT_2));

                Object response = ask(master,
                        "some request",
                        Duration.ofMinutes(1))
                        .toCompletableFuture()
                        .join();

                MasterActor.SearchResult results = (MasterActor.SearchResult) response;
                Assertions.assertEquals(2, results.getResults().size());
            }
        };
    }


    MasterActor.SearchResult getExpectedResult() {
        return new MasterActor.SearchResult(List.of(
                getResult("google"),
                getResult("yandex"),
                getResult("bing")));
    }

    SearchEngineResult getResult(String engine) {
        return convertToResult(readContent(String.format("src/main/resources/search/%s.json", engine)));
    }

    SearchEngineResult convertToResult(String json) {
        return gson.fromJson(json, SearchEngineResult.class);
    }

    String readContent(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
