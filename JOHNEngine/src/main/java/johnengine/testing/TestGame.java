package johnengine.testing;

import johnengine.basic.game.JCamera;
import johnengine.basic.game.JWorld;
import johnengine.basic.window.Window;
import johnengine.core.AGame;
import johnengine.core.IEngineComponent;
import johnengine.core.assetmngr.AssetManager;
import johnengine.core.assetmngr.asset.AssetGroup;
import johnengine.core.engine.Engine;
import johnengine.core.renderer.Renderer3D;
import johnengine.utils.counter.MilliCounter;

public class TestGame extends AGame {

    //private Networker networker;

    private MilliCounter timer;
    private AssetGroup agMain;
    
    @Override
    public void onStart(Engine engine, IEngineComponent[] engineComponents) {
        this.engine = engine;
        this.window = (Window) engineComponents[0];
        this.assetManager = (AssetManager) engineComponents[1];
        
        JWorld wMain = new JWorld(this, 0);
        JCamera cam = new JCamera(wMain);
        wMain.createInstance(cam);
        
        Renderer3D.class.cast(this.window.getRenderer()).setActiveCamera(cam);
        
        /*FragmentShader fsDefault = new FragmentShader("shader-fragment", "shaders/fragmentShader.frag");
        VertexShader vsDefault = new VertexShader("shader-vertex", "shaders/vertexShader.vert");
        
        AssetGroup agShaders = this.assetManager
        .createAssetGroup("shaders")
        .putAndDeclare(fsDefault)
        .putAndDeclare(vsDefault);
        agShaders.load();
        
        ShaderProgram defaultProgram = new ShaderProgram();
        defaultProgram.setFragmentShader(fsDefault);
        defaultProgram.setVertexShader(vsDefault);
        
        ((Renderer3D) this.gameWindow.getRenderer())*/
        
        
        //this.networker      = (Networker)       engineComponents[2];
        
        /*this.timer = new MilliCounter(1000) {
            @Override
            protected void performAction() {
                
            }
        };*/
    }

    @Override
    public void tick(float deltaTime) {
        if( this.window.hasWindowClosed() )
        this.engine.stop();
        
        
        //this.timer.count();
    }

    @Override
    public void onClose() {
        this.assetManager.stop();
        this.window.stop();
        DebugUtils.log(this, "BYEEEE");
    }
}
