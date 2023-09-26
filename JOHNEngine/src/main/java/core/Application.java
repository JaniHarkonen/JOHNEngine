package core;

import testing.DebugUtils;

public class Application {
	
	public Application() {
		DebugUtils.log(this, "hello again");
	}

	public static void main(String[] args) {
		new Application();
	}
}
