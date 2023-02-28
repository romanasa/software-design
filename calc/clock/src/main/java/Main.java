import clock.Clock;
import clock.RealWorldClock;
import statistic.EventsStatisticImpl;
import statistic.EventsStatistic;

public class Main {
    public static void main(String[] args) {
        Clock clock = new RealWorldClock();
        EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);

        eventsStatistic.incEvent("a");
        eventsStatistic.incEvent("a");
        eventsStatistic.incEvent("a");
        eventsStatistic.incEvent("a");
        eventsStatistic.incEvent("a");
        eventsStatistic.incEvent("b");
        eventsStatistic.incEvent("b");
        eventsStatistic.incEvent("b");

        eventsStatistic.printStatistic();
    }
}