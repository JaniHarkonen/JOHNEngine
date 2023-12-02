package johnengine.core.engine;

import johnengine.core.AGame;
import johnengine.core.IEngineComponent;
import johnengine.core.threadable.AThreadable;

public final class Engine extends AThreadable {
    
    /**
     * The engine failed to start the game.
     */
    public static final int STATE_START_FAILED = 1;
    
    /**
     * The engine is running a game.
     */
    public static final int STATE_RUNNING = 2;
    
    /**
     * The engine has stopped running a game or the engine
     * hasn't been started yet. 
     */
    public static final int STATE_STOPPED = 3;
    
    /**
     * Default number of ticks to evaluate per second.
     */
    public static final float DEFAULT_TICK_RATE = 60.0f;

    public static Engine engineSingleton;

    private int state;
    private float tickRate;
    private IEngineComponent[] engineComponents;
    private AGame game;
    
    public static void run(AGame game, IEngineComponent[] engineComponents) {
        if( engineSingleton != null )
        throw new RuntimeException("Engine instance is already running!");

        if( game == null )
        throw new RuntimeException("Trying to run a NULL game!");

        engineSingleton = new Engine();
        engineSingleton.setEngineComponents(engineComponents);
        engineSingleton.setGame(game);
        
        engineSingleton.start();
    }
    
    private Engine() {
        this.state = STATE_STOPPED;
        this.tickRate = DEFAULT_TICK_RATE;
    }
    
    
    @Override
    public void start() {
        this.state = STATE_RUNNING;
        this.startProcess();    // Enters thread
    }

    @Override
    public void loop() {
        this.game.onStart(this, engineComponents); // Start the game

        long lastTime = System.nanoTime();
        float tickInterval = 1 / this.tickRate;
        float deltaTime;

        while( this.isRunning() )
        {
            long currentTime = System.nanoTime();
            deltaTime = (currentTime - lastTime) / 1000000000.0f;

            if( deltaTime < tickInterval )
            continue;

                // Update engine components
                // (both afterTick() and beforeTick() are ran here in
                // said order in order to avoid a double loop; this
                // may have some subtle implications)
            for( IEngineComponent ec : this.engineComponents )
            {
                ec.afterTick(deltaTime);
                ec.beforeTick(deltaTime);
            }

            this.game.tick(deltaTime); // Run game logic
            lastTime = currentTime;
        }

        this.game.onClose(); // Close the game and free memory
    }

    @Override
    public void stop() {
        this.state = STATE_STOPPED;
    }
    
    private void setEngineComponents(IEngineComponent... engineComponents) {
        this.engineComponents = engineComponents;
    }

    private void setGame(AGame game) {
        this.game = game;
    }
    
    
    /*********************** SETTERS ************************/
    
    public void setTickRate(float tickRate) {
        if( tickRate <= 0 )
        tickRate = DEFAULT_TICK_RATE;
        
        this.tickRate = tickRate;
    }
    
    public void uncapTickRate() {
        
            // Positive infinity so that 1/this.tickRate = 0.0 -> loop() runs every iteration
        this.tickRate = Float.POSITIVE_INFINITY;
    }
    
    
    /*********************** GETTERS ************************/
    
    public boolean isRunning() {
        return (this.state == STATE_RUNNING);
    }
    
    public boolean isStopped() {
        return (this.state == STATE_STOPPED);
    }
}
