package statistic;

import clock.SetableClock;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

public class EventsStatisticTest {
    private static final double EPS = 1e-8;

    @Test
    public void statisticTest() {
        SetableClock clock = new SetableClock(Instant.now());
        EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);

        for (int i = 0; i < 120; i++) {
            eventsStatistic.incEvent("a");
        }
        for (int i = 0; i < 10; i++) {
            eventsStatistic.incEvent("b");
        }
        for (int i = 0; i < 5; i++) {
            eventsStatistic.incEvent("c");
        }

        Assert.assertEquals(120.0 / 60, eventsStatistic.getEventStatisticByName("a"), EPS);
        Assert.assertEquals(10.0 / 60, eventsStatistic.getEventStatisticByName("b"), EPS);
        Assert.assertEquals(5.0 / 60, eventsStatistic.getEventStatisticByName("c"), EPS);

        clock.setNow(Instant.now().plus(Duration.ofHours(1)).plus(Duration.ofMinutes(1)));

        for (int i = 0; i < 8; i++) {
            eventsStatistic.incEvent("c");
        }

        Assert.assertEquals(0, eventsStatistic.getEventStatisticByName("a"), EPS);
        Assert.assertEquals(0, eventsStatistic.getEventStatisticByName("b"), EPS);
        Assert.assertEquals(8.0 / 60, eventsStatistic.getEventStatisticByName("c"), EPS);
    }
}
