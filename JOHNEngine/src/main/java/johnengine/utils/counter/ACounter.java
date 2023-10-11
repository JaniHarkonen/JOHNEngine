package johnengine.utils.counter;

public abstract class ACounter {

    private long lastTime;
    private long interval;
    private long counter;
    private long lastCount;
    
    protected ACounter(long interval) {
        this.lastTime = 0;
        this.interval = interval;
        this.counter = 0;
        this.lastCount = 0;
    }
    
    public void start() {
        this.lastTime = this.getTime();
        this.counter = 0;
        this.lastCount = 0;
    }
    
    public long count() {
        long timeNow = this.getTime();
        this.counter++;
        
        if( timeNow - this.lastTime >= this.interval )
        {
            this.lastTime = timeNow;
            this.lastCount = this.counter;
            this.performAction();
            
            this.counter = 0;
        }
        
        return this.lastCount;
    }
    
    protected abstract long getTime();
    
    protected void performAction() {
        
    }
    
    public long getLastTime() {
        return this.lastTime;
    }
    
    public long getLastCount() {
        return this.lastCount;
    }
    
    public long getInterval() {
        return this.interval;
    }
}
