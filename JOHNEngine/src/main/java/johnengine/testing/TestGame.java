package johnengine.testing;

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
        
        this.gameWindow.changeTitle("ezzzzpzzz B)");//setTitle("ezzzpzzz B)");
        //this.gameWindow.resize(640, 480);
        //this.gameWindow.setCursorVisibility(true);
        this.timer = new MilliCounter(1000) {
            @Override
            protected void performAction() {
                //gameWindow.setTitle(""+this.getLastCount());
                //gameWindow.setTitle(""+gameWindow.getFPS());
                //gameWindow.changeTitle(""+gameWindow.getFPS());
            }
        };
    }

    @Override
    public void tick(float deltaTime) {
        if( this.gameWindow.hasWindowClosed() )
        this.engine.stop();
        
        this.gameWindow.changeTitle(""+this.gameWindow.getFPS());
        
        
        if( this.gameWindow.getRenderer() != null )
        {
            //this.gameWindow.setTitle(""+this.gameWindow.getFPS());
            float same = (float)Math.random();
            this.gameWindow.getRenderer().r = same;
            this.gameWindow.getRenderer().g = same;
            this.gameWindow.getRenderer().b =same;
            this.gameWindow.getRenderer().a = same;
        }
        
        if( this.gameWindow.getInput() != null )
        {
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
