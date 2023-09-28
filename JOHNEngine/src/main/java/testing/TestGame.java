package testing;

import org.lwjgl.glfw.GLFW;

import core.AbstractGame;
import core.IEngineComponent;
import core.assetmngr.AssetManager;
import core.engine.Engine;
import core.networker.Networker;
import core.window.Window;

public class TestGame extends AbstractGame {

    private Window gameWindow;
    private AssetManager assetManager;
    private Networker networker;
    private Engine engine;
    
    private long DEBUG_fpsCounter;

    @Override
    public void onStart(Engine engine, IEngineComponent[] engineComponents) {
        this.engine         = engine;
        this.gameWindow     = (Window)          engineComponents[0];
        this.assetManager   = (AssetManager)    engineComponents[1];
        this.networker      = (Networker)       engineComponents[2];
        
        this.engine.setTickRate(60);
        this.gameWindow.setTitle("ezzzpzzz B)");
        this.gameWindow.resize(640, 480);
        this.gameWindow.enable();
        
        this.DEBUG_fpsCounter = 0;
    }

    @Override
    public void tick(float deltaTime) {
        if( this.gameWindow.hasWindowClosed() )
        this.engine.stop();
        
        if( this.gameWindow.getRenderer() != null )
        {
            this.gameWindow.setTitle(""+this.gameWindow.getFPS());
            float same = (float)Math.random();
            this.gameWindow.getRenderer().r = same;
            this.gameWindow.getRenderer().g = same;
            this.gameWindow.getRenderer().b =same;
            this.gameWindow.getRenderer().a = same;
        }
        
        this.DEBUG_fpsCounter++;
        
        //DebugUtils.log(this, this.DEBUG_fpsCounter);
        
        if( this.gameWindow.getInput() != null )
        {
            //if( this.gameWindow.getInput().isKeyPressed(GLFW.GLFW_KEY_A) )
                //DebugUtils.log(this, "AAAAAAAAAAA");
            
            if( this.gameWindow.getInput().isKeyReleased(GLFW.GLFW_KEY_A) )
            DebugUtils.log(this, "aaaa");
            
            DebugUtils.log(this, this.gameWindow.getInput().getMouseX() + ", " + this.gameWindow.getInput().getMouseY());
            
            /*if( this.gameWindow.getInput().isKeyReleased(GLFW.GLFW_KEY_A) )
                DebugUtils.log(this, "BBBBB");
            
            if( this.gameWindow.getInput().isKeyDown(GLFW.GLFW_KEY_A) )
                DebugUtils.log(this, "?????????");*/
        }
        
    }

    @Override
    public void onClose() {
        DebugUtils.log(this, "BYEEEE");
    }
}
