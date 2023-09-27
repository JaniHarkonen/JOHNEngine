package core.engine;

import testing.DebugUtils;
import utils.CommonUtils;

public final class Engine {
	
	public enum STATE {
		START_FAILED,
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

	private Engine() {
		this.state = Engine.STATE.STOPPED;
		this.ticksPerSecond = TICK_SPEED_CAP;
	}
	
	public static STATE start(IEngineComponent ...engineComponents) {
		if( engine != null )
		return Engine.STATE.START_FAILED;
		
		engine = new Engine();
		//engine.initializeEngineComponents(setupMask);
		engine.setEngineComponents(engineComponents);
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
	
	private class loller {
		public void leller() {
			
		}
	}
	
	private void run() {
		this.state = Engine.STATE.RUNNING;
		DebugUtils.log(this, "number of engine components: " + this.engineComponents.length);
		this.loop();
	}
	
	private void loop() {
		long lastTime = System.nanoTime();
		float tickInterval = 1 / this.ticksPerSecond;
		float deltaTime;
		long counter = 0;
		loller lollerino = null;
		
		if( Math.random() > 0.5 )
		lollerino = new loller();
			
		long testtime = System.currentTimeMillis();
		
		while( this.state == Engine.STATE.RUNNING )
		{
			long currentTime = System.nanoTime();
			deltaTime = (currentTime - lastTime) / 1000000000.0f;
			
			if( deltaTime < tickInterval )
			continue;
			
			lastTime = currentTime;
			
			if( lollerino != null )
			lollerino.leller();
			
			counter++;
			
			if( System.currentTimeMillis() - testtime >= 1000 )
			{
				DebugUtils.log(this, counter);
				counter = 0;
				testtime = System.currentTimeMillis();
			}
		}
	}
	
	private void setEngineComponents(IEngineComponent ...engineComponents) {
		this.engineComponents = engineComponents;
	}
}
