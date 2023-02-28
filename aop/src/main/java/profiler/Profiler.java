package profiler;

import model.CallCounter;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Profiler {
    private final static Map<String, CallCounter> statistics = new HashMap<>();
    private final static Map<String, Instant> callsInProgress = new HashMap<>();

    private final static Clock clock = Clock.systemDefaultZone();

    static void start(String function) {
        callsInProgress.put(function, clock.instant());
    }

    static void end(String function) {
        statistics.putIfAbsent(function, new CallCounter());
        statistics.get(function).call(Duration.between(callsInProgress.get(function), clock.instant()));
    }

    public static void printStatistic() {
        System.out.println("Statistics:");
        statistics.forEach((function, statistic) -> {
            System.out.println("Function:\t" + function);
            System.out.println(statistic);
            System.out.println("_______________________");
        });
    }
}
