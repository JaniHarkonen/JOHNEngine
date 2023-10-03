package johnengine.utils.counter;

public class NanoCounter extends ACounter {

    protected NanoCounter(long interval) {
        super(interval);
    }

    @Override
    protected long getTime() {
        return System.nanoTime();
    }
}
