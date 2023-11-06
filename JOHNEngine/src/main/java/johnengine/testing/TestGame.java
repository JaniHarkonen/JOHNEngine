package johnengine.testing;

import johnengine.basic.assets.rew.imgasset.AImageAsset;
import johnengine.basic.assets.rew.imgasset.Texture;
import johnengine.basic.assets.rew.sceneobj.SceneObjectLoader;
import johnengine.basic.game.CModel;
import johnengine.basic.game.JCamera;
import johnengine.basic.game.JWorld;
import johnengine.basic.renderer.asset.rew.Mesh;
import johnengine.basic.renderer.rew.Renderer3D;
import johnengine.basic.window.Window;
import johnengine.core.AGame;
import johnengine.core.IEngineComponent;
import johnengine.core.assetmngr.asset.rew.AssetManager;
import johnengine.core.engine.Engine;
import johnengine.utils.counter.MilliCounter;

public class TestGame extends AGame {

    //private Networker networker;

    private MilliCounter timer;
    private JWorld worldMain;
    
    @Override
    public void onStart(Engine engine, IEngineComponent[] engineComponents) {
        this.engine = engine;
        this.window = (Window) engineComponents[0];
        this.assetManager = (AssetManager) engineComponents[1];
        
            // Declare assets
        AssetManager am = this.assetManager;
        am.declareAsset(new Mesh("defaultMesh"));   // Mesh
        am.declareAsset(new Texture("defaultTexture")); // Texture
        
            // Load Assimp scene (including mesh)
        SceneObjectLoader sl = new SceneObjectLoader();
        sl.expectMesh((Mesh) am.getAsset("defaultMesh"));
        am.loadFrom("", sl);
        
            // Load an image asset (texture)
        AImageAsset.Loader il = new AImageAsset.Loader((Texture) am.getAsset("defaultTexture"));
        am.loadFrom("", il);
        
            // Create a model with the mesh and the texture
        CModel modelDefault = new CModel();
        modelDefault.setMesh((Mesh) am.getAsset("defaultMesh"));
        modelDefault.setTexture((Texture) am.getAsset("defaultTexture"));
        
            // Create the game world and create the camera and the
            // test box (with the created model)
        this.worldMain = new JWorld(this);
        this.worldMain.createInstance(new JCamera(this.worldMain));
        this.worldMain.createInstance(new JTestBox(this.worldMain, modelDefault));
        
            // Update the active world of the renderer
        Renderer3D.class.cast(this.window.getRenderer()).setActiveWorld(this.worldMain);
        /*AssetGroup ag = this.assetManager.createAssetGroup("main");
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
        
        Renderer3D.class.cast(this.window.getRenderer()).setActiveWorld(wMain);*/
        
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
        
        this.worldMain.tick(deltaTime);
        
        //this.timer.count();
    }

    @Override
    public void onClose() {
        this.assetManager.stop();
        this.window.stop();
        DebugUtils.log(this, "BYEEEE");
    }
}
