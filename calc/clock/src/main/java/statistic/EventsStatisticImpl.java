package statistic;

import clock.Clock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class EventsStatisticImpl implements EventsStatistic {

    private static final int WINDOW = 60 * 60 * 1000;

    private final Map<String, Queue<Long>> events = new HashMap<>();
    private final Clock clock;

    public EventsStatisticImpl(Clock clock) {
        this.clock = clock;
    }


    @Override
    public void incEvent(String name) {
        events.putIfAbsent(name, new LinkedList<>());
        events.get(name).add(clock.getNowInMillis());
    }

    @Override
    public double getEventStatisticByName(String name) {
        if (!events.containsKey(name)) {
            return 0;
        }
        Queue<Long> queue = events.get(name);
        updateQueue(queue, clock.getNowInMillis());
        if (queue.isEmpty()) {
            events.remove(name);
        }
        return queue.size() / 60.0;
    }

    private void updateQueue(Queue<Long> queue, long currentTime) {
        while (!queue.isEmpty() && queue.peek() + WINDOW < currentTime) {
            queue.poll();
        }
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        return events.keySet().stream()
                .collect(Collectors.toMap(name -> name, this::getEventStatisticByName, (a, b) -> b));
    }

    @Override
    public void printStatistic() {
        Map<String, Double> statistic = getAllEventStatistic();
        System.out.println("Statistic:");
        statistic.forEach((key, value) -> System.out.printf("%s:\t%.3f rpm\n", key, value));
    }
}
