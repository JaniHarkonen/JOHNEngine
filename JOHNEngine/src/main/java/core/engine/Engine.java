package core.engine;

import core.AbstractGame;
import testing.DebugUtils;

public final class Engine {
	
	public enum STATE {
		START_FAILED,
		START_FAILED_NO_GAME,
		RUNNING,
		STOPPED
	}
	
	public static final float TICK_SPEED_CAP = Float.POSITIVE_INFINITY;	// Positive infinity so that 1/TICK_SPEED_CAP = 0.0, making the loop run faster
	
	

	/*public static final int SETUP_WINDOW = 2;

	public static final int SETUP_ASSET_MANAGER = 2;
	

	public static final int SETUP_NETWORKER = 4;
	
	
	public static final int SETUP_SERVER = 8;*/
	
	public static Engine engine;
	
	private STATE state;
	private float ticksPerSecond;
	private IEngineComponent[] engineComponents;
	private AbstractGame game;

	private Engine() {
		this.state = Engine.STATE.STOPPED;
		this.ticksPerSecond = TICK_SPEED_CAP;
	}
	
	public static STATE start(AbstractGame game, IEngineComponent ...engineComponents) {
		if( engine != null )
		return Engine.STATE.START_FAILED;
		
		if( game == null )
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
	
	/*private void initializeEngineComponents(int setupMask) {
		if( CommonUtils.checkBitPattern(setupMask, SETUP_WINDOW) )
		{
			
		}
		
		if( CommonUtils.checkBitPattern(setupMask, SETUP_ASSET_MANAGER) )
		{
			
		}
		
		if( CommonUtils.checkBitPattern(setupMask, SETUP_NETWORKER) )
		{
			
		}
		
		if( CommonUtils.checkBitPattern(setupMask, SETUP_SERVER) )
		{
			
		}
		
		DebugUtils.log(this,
			"setup renderer: " + CommonUtils.checkBitPattern(setupMask, SETUP_WINDOW),
			"setup asset manager: " + CommonUtils.checkBitPattern(setupMask, SETUP_ASSET_MANAGER),
			"setup networker: " + CommonUtils.checkBitPattern(setupMask, SETUP_NETWORKER),
			"setup server: " + CommonUtils.checkBitPattern(setupMask, SETUP_SERVER)
		);
	}*/
	
	private void run() {
		this.state = Engine.STATE.RUNNING;
		DebugUtils.log(this, "number of engine components: " + this.engineComponents.length);
		this.loop();
	}
	
	private void loop() {
		game.onCreate(engineComponents);	// Create the game
		
		long lastTime = System.nanoTime();
		float tickInterval = 1 / this.ticksPerSecond;
		float deltaTime;
		
		while( this.state == Engine.STATE.RUNNING )
		{
			long currentTime = System.nanoTime();
			deltaTime = (currentTime - lastTime) / 1000000000.0f;
			
			if( deltaTime < tickInterval )
			continue;
			
			lastTime = currentTime;
			
				// (BEFORE TICK) Poll engine components
			for( IEngineComponent ec : this.engineComponents )
			ec.beforeTick();
			
			game.tick(deltaTime);	// Run game logic
			
				// (AFTER TICK) Update engine components
			for( IEngineComponent ec : this.engineComponents )
			ec.afterTick();
		}
		
		game.onClose();	// Close the game and free memory
	}
	
	private void setEngineComponents(IEngineComponent ...engineComponents) {
		this.engineComponents = engineComponents;
	}
	
	private void setGame(AbstractGame game) {
		this.game = game;
	}
}
