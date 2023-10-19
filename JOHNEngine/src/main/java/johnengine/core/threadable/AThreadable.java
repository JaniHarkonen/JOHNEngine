package johnengine.core.threadable;

public abstract class AThreadable implements IThreadable {
    
    private class Process extends Thread {
        @Override
        public void run() {
            loop();
        }
    }
    
    protected void startProcess() {
        (new Process()).start();
    }
    
    @Override
    public void start() {
        this.startProcess();
    }
}
