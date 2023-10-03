package johnengine.core;

public abstract class AThreadable {
    
    private class Process extends Thread {
        @Override
        public void run() {
            loop();
        }
    }
    
    protected void startProcess() {
        (new Process()).start();
    }
    
    public abstract void start();
    protected abstract void loop();
    public abstract void stop();
}
