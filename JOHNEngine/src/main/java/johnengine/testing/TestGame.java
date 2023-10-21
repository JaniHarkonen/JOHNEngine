package johnengine.testing;

import johnengine.basic.window.Window;
import johnengine.core.AGame;
import johnengine.core.IEngineComponent;
import johnengine.core.assetmngr.AssetManager;
import johnengine.core.assetmngr.asset.AssetGroup;
import johnengine.core.engine.Engine;
import johnengine.core.renderer.shdprog.FragmentShader;
import johnengine.core.renderer.shdprog.ShaderProgram;
import johnengine.core.renderer.shdprog.VertexShader;
import johnengine.utils.counter.MilliCounter;

public class TestGame extends AGame {

    private Window gameWindow;
    private AssetManager assetManager;
    //private Networker networker;
    private Engine engine;
    private MilliCounter timer;
    private AssetGroup agMain;
    
    @Override
    public void onStart(Engine engine, IEngineComponent[] engineComponents) {
        this.engine         = engine;
        this.gameWindow     = (Window)          engineComponents[0];
        this.assetManager   = (AssetManager)    engineComponents[1];
        
        FragmentShader fsDefault = new FragmentShader("shader-fragment", "shaders/fragmentShader.frag");
        VertexShader vsDefault = new VertexShader("shader-vertex", "shaders/vertexShader.vert");
        
        AssetGroup agShaders = this.assetManager
        .createAssetGroup("shaders")
        .putAndDeclare(fsDefault)
        .putAndDeclare(vsDefault);
        agShaders.load();
        
        ShaderProgram defaultProgram = new ShaderProgram();
        defaultProgram.setFragmentShader(fsDefault);
        defaultProgram.setVertexShader(vsDefault);
        
        ((Renderer3D) this.gameWindow.getRenderer()).
        
        
        //this.networker      = (Networker)       engineComponents[2];
        
        /*this.timer = new MilliCounter(1000) {
            @Override
            protected void performAction() {
                
            }
        };*/
    }

    @Override
    public void tick(float deltaTime) {
        if( this.gameWindow.hasWindowClosed() )
        this.engine.stop();
        
        
        //this.timer.count();
    }

    @Override
    public void onClose() {
        this.assetManager.stop();
        DebugUtils.log(this, "BYEEEE");
    }
}
