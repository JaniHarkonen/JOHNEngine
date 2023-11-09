package johnengine.core;

import java.util.concurrent.atomic.AtomicLong;

public final class UUID {

    private static AtomicLong currentUUID = new AtomicLong(0);
    
    
    public static long newLongUUID() {
        return currentUUID.incrementAndGet();
    }
    
    public static long peekLongUUID() {
        return currentUUID.get();
    }
}
