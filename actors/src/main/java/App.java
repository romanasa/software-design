import actors.MasterActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import search.SearchEngineResult;

import java.time.Duration;
import java.util.Scanner;
import java.util.stream.IntStream;

import static akka.pattern.Patterns.ask;

public class App {


    private static final String EXIT_MSG = "exit()";
    private static final Duration TIMEOUT = Duration.ofMillis(1000);


    public static void main(String[] args) {
        final var scanner = new Scanner(System.in);
        ActorSystem system = ActorSystem.create("system");

        while (true) {
            System.out.println("Search: ");
            final var request = scanner.nextLine();

            if (request.equals(EXIT_MSG)) {
                break;
            }

            ActorRef master = system.actorOf(Props.create(MasterActor.class, TIMEOUT), "master");
            Object response = ask(master, request, Duration.ofMinutes(1))
                    .toCompletableFuture()
                    .join();

            MasterActor.SearchResult result = (MasterActor.SearchResult) response;
            printResult(result);
        }
    }

    private static void printResult(MasterActor.SearchResult result) {
        result.getResults().forEach(item -> {
            System.out.println("\tResults from " + item.getEngineName());
            IntStream.range(0, item.getUrls().size()).forEach(i -> {
                System.out.format("    #%d: \n", i);
                System.out.println("      Title: " + item.getUrls().get(i).getTitle());
                System.out.println("      URL: " + item.getUrls().get(i).getUrl());
            });
        });
    }
}