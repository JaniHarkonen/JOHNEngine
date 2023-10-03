package johnengine.utils.counter;

public class MilliCounter extends ACounter {

    protected MilliCounter(long interval) {
        super(interval);
    }

    @Override
    protected long getTime() {
        return System.currentTimeMillis();
    }
}
