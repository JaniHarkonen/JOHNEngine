package johnengine.testing;

import org.lwjgl.glfw.GLFW;

import johnengine.basic.window.Window;
import johnengine.core.AGame;
import johnengine.core.IEngineComponent;
import johnengine.core.assetmngr.AssetManager;
import johnengine.core.assetmngr.asset.AssetGroup;
import johnengine.core.engine.Engine;
import johnengine.core.networker.Networker;
import johnengine.utils.counter.MilliCounter;

public class TestGame extends AGame {

    private Window gameWindow;
    private AssetManager assetManager;
    private Networker networker;
    private Engine engine;
    private MilliCounter timer;
    private AssetGroup agMain;
    
    @Override
    public void onStart(Engine engine, IEngineComponent[] engineComponents) {
        this.engine         = engine;
        this.gameWindow     = (Window)          engineComponents[0];
        this.assetManager   = (AssetManager)    engineComponents[1];
        this.networker      = (Networker)       engineComponents[2];
        
            // Engine settings
        this.engine.setTickRate(60);
        
            // Game window settings
        this.gameWindow
        .changeTitle("ezzzzpzzz B)")
        .disableVSync();
        
            // Asset declarations/loading
        this.agMain = this.assetManager
        .createAssetGroup("main")
        .putAndDeclare(new TestAsset("test", "C:/Users/User/Desktop/copemax.txt"));
        this.agMain.load();
        
        //this.gameWindow.resize(1000, 1000);
        /*try {
            Thread.sleep(1000);
        }
        catch(Exception e) {}*/
        //this.gameWindow.lockCursorToCenter();
        //this.gameWindow.resize(640, 480);
        //this.gameWindow.setCursorVisibility(true);
        this.timer = new MilliCounter(1000) {
            @Override
            protected void performAction() {
                //gameWindow.changeTitle(""+this.getLastCount());
                //gameWindow.changeTitle("FPS: "+gameWindow.getFPS() + " | Tick rate: " + this.getLastCount());
                //gameWindow.changeTitle(""+gameWindow.getFPS());
                gameWindow.changeTitle("(" + gameWindow.getInput().getMouseX() + ", " + gameWindow.getInput().getMouseY() + ")");
            }
        };
    }

    @Override
    public void tick(float deltaTime) {
        if( this.gameWindow.hasWindowClosed() )
        this.engine.stop();
        
        /*this.gameWindow.changeTitle(
            this.gameWindow.getWidth() + ", " + 
            this.gameWindow.getHeight() + " | FPS: " + 
            this.gameWindow.getFPS() + " | Maximized: " + 
            this.gameWindow.isMaximized()
        );*/
        
        if( this.gameWindow.getInput() != null )
        {
            if( this.gameWindow.getInput().isKeyReleased(GLFW.GLFW_KEY_A) )
            this.gameWindow.enterFullscreen();
        }
        
        TestAsset asset = (TestAsset) this.assetManager.getAsset("test");
        //DebugUtils.log(this, asset.getStatus());
        this.agMain.deload();
        
        this.gameWindow.moveMouse((int) (Math.random() * 640), (int) (Math.random() * 480));
        
        this.timer.count();
    }

    @Override
    public void onClose() {
        DebugUtils.log(this, "BYEEEE");
    }
}
