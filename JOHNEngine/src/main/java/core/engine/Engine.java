package core.engine;

import core.AbstractGame;

public final class Engine {

    /**
     * State of the game engine.
     * 
     * @author User
     *
     */
    public enum STATE {
        START_FAILED, START_FAILED_NO_GAME, RUNNING, STOPPED
    }

    public static final float TICK_SPEED_CAP = Float.POSITIVE_INFINITY; // Positive infinity so that 1/TICK_SPEED_CAP =
                                                                        // 0.0, making the loop run faster

    public static Engine engine;

    private STATE state;
    private float tickRate;
    private IEngineComponent[] engineComponents;
    private AbstractGame game;

    private Engine() {
        this.state = Engine.STATE.STOPPED;
        this.tickRate = TICK_SPEED_CAP;
    }

    public static STATE start(AbstractGame game, IEngineComponent... engineComponents) {
        if (engine != null)
        return Engine.STATE.START_FAILED;

        if (game == null)
        return Engine.STATE.START_FAILED_NO_GAME;

        engine = new Engine();
        engine.setEngineComponents(engineComponents);
        engine.setGame(game);
        engine.run();

        return Engine.STATE.STOPPED;
    }

    public void stop() {
        this.state = Engine.STATE.STOPPED;
    }

    private void run() {
        this.state = Engine.STATE.RUNNING;
        this.loop();
    }

    private void loop() {
        game.onStart(this, engineComponents); // Start the game

        long lastTime = System.nanoTime();
        float tickInterval = 1 / this.tickRate;
        float deltaTime;

        while (this.state == Engine.STATE.RUNNING) {
            long currentTime = System.nanoTime();
            deltaTime = (currentTime - lastTime) / 1000000000.0f;

            if (deltaTime < tickInterval)
            continue;

            lastTime = currentTime;

            // (BEFORE TICK) Poll engine components
            for (IEngineComponent ec : this.engineComponents)
            ec.beforeTick(deltaTime);

            game.tick(deltaTime); // Run game logic

            // (AFTER TICK) Update engine components
            for (IEngineComponent ec : this.engineComponents)
            ec.afterTick(deltaTime);
        }

        game.onClose(); // Close the game and free memory
    }

    private void setEngineComponents(IEngineComponent... engineComponents) {
        this.engineComponents = engineComponents;
    }

    private void setGame(AbstractGame game) {
        this.game = game;
    }
    
    public void setTickRate(float tickRate) {
        this.tickRate = tickRate;
    }
}
