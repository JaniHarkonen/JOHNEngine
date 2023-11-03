package johnengine.testing;

import johnengine.basic.assets.SceneObjectAsset;
import johnengine.basic.assets.rew.textasset.TextAsset;
import johnengine.basic.game.JCamera;
import johnengine.basic.game.JWorld;
import johnengine.basic.renderer.Renderer3D;
import johnengine.basic.renderer.asset.Mesh;
import johnengine.basic.window.Window;
import johnengine.core.AGame;
import johnengine.core.IEngineComponent;
import johnengine.core.assetmngr.asset.rew.AssetManager;
import johnengine.core.assetmngr.asset.rew.asset.AssetGroup;
import johnengine.core.engine.Engine;
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
        
        AssetGroup ag = this.assetManager.createAssetGroup("main");
        ag.putAndDeclare(new TextAsset("shader", true, null));
        
        this.assetManager.declareAsset(new TextAsset("shader", true, null));
        this.assetManager.load(loader)
        
        this.assetManager.load(new TextAsset.Loader("shader.exe", this.assetManager.getAsset("shader")));
        
        JWorld wMain = new JWorld(this, 0);
        JCamera cam = new JCamera(wMain);
        wMain.createInstance(cam);
        
        Renderer3D.class.cast(this.window.getRenderer()).setActiveCamera(cam);
        
        SceneObjectAsset scene = new SceneObjectAsset("trolled", "xd")
        .addMesh(new Mesh("mesh-getRIGHT"))
        .addMesh(new Mesh("mesh-man"));
        
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
