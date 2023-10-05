package johnengine.testing;

import org.lwjgl.glfw.GLFW;

import johnengine.core.AGame;
import johnengine.core.IEngineComponent;
import johnengine.core.assetmngr.AssetManager;
import johnengine.core.engine.Engine;
import johnengine.core.networker.Networker;
import johnengine.core.window.Window;
import johnengine.utils.counter.MilliCounter;

public class TestGame extends AGame {

    private Window gameWindow;
    private AssetManager assetManager;
    private Networker networker;
    private Engine engine;
    private MilliCounter timer;
    
    @Override
    public void onStart(Engine engine, IEngineComponent[] engineComponents) {
        this.engine         = engine;
        this.gameWindow     = (Window)          engineComponents[0];
        this.assetManager   = (AssetManager)    engineComponents[1];
        this.networker      = (Networker)       engineComponents[2];
        
        this.engine.setTickRate(60);
        
        this.gameWindow.changeTitle("ezzzzpzzz B)").disableVSync();
        //this.gameWindow.resize(1000, 1000);
        try {
            Thread.sleep(1000);
        }
        catch(Exception e) {}
        //this.gameWindow.lockCursorToCenter();
        //this.gameWindow.resize(640, 480);
        //this.gameWindow.setCursorVisibility(true);
        this.timer = new MilliCounter(1000) {
            @Override
            protected void performAction() {
                //gameWindow.changeTitle(""+this.getLastCount());
                gameWindow.changeTitle("FPS: "+gameWindow.getFPS() + " | Tick rate: " + this.getLastCount());
                //gameWindow.changeTitle(""+gameWindow.getFPS());
            }
        };
    }

    @Override
    public void tick(float deltaTime) {
        if( this.gameWindow.hasWindowClosed() )
        this.engine.stop();
        
        //this.gameWindow.changeTitle(""+this.gameWindow.getFPS());
        //this.gameWindow.changeTitle(this.gameWindow.getWidth() + ", " + this.gameWindow.getHeight() + " | FPS: " + this.gameWindow.getFPS() + " | Maximized: " + this.gameWindow.isMaximized());
        
        
        /*if( this.gameWindow.getRenderer() != null )
        {
            //this.gameWindow.setTitle(""+this.gameWindow.getFPS());
            float same = (float)Math.random();
            this.gameWindow.getRenderer().r = same;
            this.gameWindow.getRenderer().g = same;
            this.gameWindow.getRenderer().b =same;
            this.gameWindow.getRenderer().a = same;
        }*/
        
        if( this.gameWindow.getInput() != null )
        {
            if( this.gameWindow.getInput().isKeyReleased(GLFW.GLFW_KEY_A) )
            this.gameWindow.enterFullscreen();
            //this.gameWindow.changeTitle(this.gameWindow.getInput().getMouseX() + ", " + this.gameWindow.getInput().getMouseY());
            //if( this.gameWindow.getInput().isKeyDown(GLFW.GLFW_KEY_A) )
            //this.gameWindow.changeTitle(title);
            //this.gameWindow.setFullscreen(true);
        }
        
        this.timer.count();
    }

    @Override
    public void onClose() {
        DebugUtils.log(this, "BYEEEE");
    }
}