package johnengine.core.engine;

import johnengine.core.AGame;
import johnengine.core.AThreadable;
import johnengine.core.IEngineComponent;
import johnengine.testing.DebugUtils;

public final class Engine extends AThreadable {

    /**
     * State of the game engine.
     * 
     * @author User
     *
     */
    public enum STATE {
        START_FAILED, START_FAILED_NO_GAME, RUNNING, STOPPED
    }

    public static final float TICK_SPEED_CAP = Float.POSITIVE_INFINITY; // Positive infinity so that 1/TICK_SPEED_CAP = 0.0, making the loop run faster

    public static Engine engine;

    private STATE state;
    private float tickRate;
    private IEngineComponent[] engineComponents;
    private AGame game;

    private Engine() {
        this.state = Engine.STATE.STOPPED;
        this.tickRate = TICK_SPEED_CAP;
    }
    
    public static void start(AGame game, IEngineComponent[] engineComponents) {
        if( engine != null )
        throw new RuntimeException("Engine instance is already running!");

        if( game == null )
        throw new RuntimeException("Trying to run a NULL game!");

        engine = new Engine();
        engine.setEngineComponents(engineComponents);
        engine.setGame(game);
        
        engine.start();
        return;
    }
    
    @Override
    public void start() {
        this.state = Engine.STATE.RUNNING;
        
        this.startProcess();
        return;
    }

    @Override
    protected void loop() {
        try {
        Thread.sleep(1000);
        }catch(Exception e) {}
        
        game.onStart(this, engineComponents); // Start the game

        long lastTime = System.nanoTime();
        float tickInterval = 1 / this.tickRate;
        float deltaTime;

        while( this.state == Engine.STATE.RUNNING ) {
            long currentTime = System.nanoTime();
            deltaTime = (currentTime - lastTime) / 1000000000.0f;

            if( deltaTime < tickInterval )
            continue;

            lastTime = currentTime;

                // Update engine components
            for( IEngineComponent ec : this.engineComponents )
            {
                ec.afterTick(deltaTime);
                ec.beforeTick(deltaTime);
            }

            game.tick(deltaTime); // Run game logic
        }

        game.onClose(); // Close the game and free memory
    }

    @Override
    public void stop() {
        this.state = Engine.STATE.STOPPED;
    }

    private void setEngineComponents(IEngineComponent... engineComponents) {
        this.engineComponents = engineComponents;
    }

    private void setGame(AGame game) {
        this.game = game;
    }
    
    public void setTickRate(float tickRate) {
        this.tickRate = tickRate;
    }
}
