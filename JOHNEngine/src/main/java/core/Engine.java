package core;

import testing.DebugUtils;
import utils.CommonUtils;

public final class Engine {
	
	public enum STATE {
		START_FAILED,
		RUNNING,
		STOPPED
	}
	
	public static final float TICK_SPEED_CAP = Float.POSITIVE_INFINITY;	// Positive infinity so that 1/TICK_SPEED_CAP = 0.0, making the loop run faster
	
	/**
	 * Sets up the Renderer process that can be utilized to display 
	 * graphics in a game window.
	 */
	public static final int SETUP_RENDERER = 2;
	
	/**
	 * Sets up the AssetManager that can be utilized to load in 
	 * and manage external assets.
	 */
	public static final int SETUP_ASSET_MANAGER = 2;
	
	/**
	 * Sets up the client-side networking process that can be 
	 * utilized to send and receive IP-packets between machines
	 * and services.
	 */
	public static final int SETUP_NETWORKER = 4;
	
	/**
	 * Sets up the server process that can be utilized to send
	 * and receive IP-packets between the host machine and the
	 * clients.
	 */
	public static final int SETUP_SERVER = 8;
	
	public static Engine engine;
	
	private STATE isRunning;
	private float ticksPerSecond;

	private Engine() {
		this.isRunning = Engine.STATE.STOPPED;
		this.ticksPerSecond = TICK_SPEED_CAP;
	}
	
	public static STATE start(int setupMask) {
		if( engine != null )
		return Engine.STATE.START_FAILED;
		
		engine = new Engine();
		engine.initializeEngineComponents(setupMask);
		engine.isRunning = Engine.STATE.RUNNING;
		engine.loop();
		
		return Engine.STATE.STOPPED;
	}
	
	public void stop() {
		this.isRunning = Engine.STATE.STOPPED;
	}
	
	private void initializeEngineComponents(int setupMask) {
		if( CommonUtils.checkBitPattern(setupMask, SETUP_RENDERER) )
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
			"setup renderer: " + CommonUtils.checkBitPattern(setupMask, SETUP_RENDERER),
			"setup asset manager: " + CommonUtils.checkBitPattern(setupMask, SETUP_ASSET_MANAGER),
			"setup networker: " + CommonUtils.checkBitPattern(setupMask, SETUP_NETWORKER),
			"setup server: " + CommonUtils.checkBitPattern(setupMask, SETUP_SERVER)
		);
	}
	
	private void loop() {
		long lastTime = System.nanoTime();
		float tickInterval = 1 / this.ticksPerSecond;
		float deltaTime;
		
		while( this.isRunning == Engine.STATE.RUNNING )
		{
			long currentTime = System.nanoTime();
			deltaTime = (currentTime - lastTime) / 1000000000.0f;
			
			if( deltaTime < tickInterval )
			continue;
			
			lastTime = currentTime;
			
			
		}
	}
}
