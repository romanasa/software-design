package clock;

import java.time.Instant;

public class RealWorldClock implements Clock {
    @Override
    public Instant now() {
        return Instant.now();
    }

    @Override
    public long getNowInMillis() {
        return now().toEpochMilli();
    }
}
