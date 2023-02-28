package model;

import java.time.Duration;

public class CallCounter {
    private Duration duration;
    private int count;

    public CallCounter() {
        this.duration = Duration.ofMillis(0);
        this.count = 0;
    }

    public Duration getDuration() {
        return duration;
    }

    public int getCount() {
        return count;
    }

    public void call(Duration duration) {
        this.count++;
        this.duration = this.duration.plus(duration);
    }

    @Override
    public String toString() {
        return String.format(
                        "Total calls:\t%d\n" +
                        "Total duration:\t%dms\n" +
                        "Mean duration:\t%dms",
                getCount(), getDuration().toMillis(), (getDuration().dividedBy(getCount())).toMillis());
    }
}
