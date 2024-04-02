package johnengine.core.threadable;

public abstract class AThreadable implements IThreadable {
    
    private class Process extends Thread {
        
        private Process(String name) {
            super(name);
        }
        
        @Override
        public void run() {
            loop();
        }
    }
    
    
    private final String threadName;
    
    public AThreadable(String threadName) {
        this.threadName = threadName;
    }
    
    
    protected void startProcess() {
        (new Process(this.threadName)).start();
    }
    
    @Override
    public void start() {
        this.startProcess();
    }
}
