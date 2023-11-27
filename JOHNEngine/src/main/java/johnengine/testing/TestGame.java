package johnengine.testing;

import johnengine.basic.assets.imgasset.AImageAsset;
import johnengine.basic.assets.imgasset.Texture;
import johnengine.basic.assets.sceneobj.SceneObjectLoader;
import johnengine.basic.game.CModel;
import johnengine.basic.game.JCamera;
import johnengine.basic.game.JWorld;
import johnengine.basic.renderer.Renderer3D;
import johnengine.basic.renderer.asset.Mesh;
import johnengine.basic.window.Window;
import johnengine.core.AGame;
import johnengine.core.IEngineComponent;
import johnengine.core.assetmngr.AssetManager;
import johnengine.core.engine.Engine;
import johnengine.core.winframe.AWindowFramework;
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
        /*
            // Declare assets
        AssetManager am = this.assetManager;
        am.declareAsset(new Mesh("defaultMesh"));   // Mesh
        am.declareAsset(new Texture("defaultTexture")); // Texture
        
            // Load Assimp scene (including mesh)
        SceneObjectLoader sl = new SceneObjectLoader();
        sl.expectMesh((Mesh) am.getAsset("defaultMesh"));
        am.loadFrom("C:\\Users\\User\\git\\JOHNEngine\\JOHNEngine\\src\\main\\resources\\test\\man.fbx", sl);
        
            // Load an image asset (texture)
        AImageAsset.Loader il = new AImageAsset.Loader((Texture) am.getAsset("defaultTexture"));
        am.loadFrom("C:\\Users\\User\\git\\JOHNEngine\\JOHNEngine\\src\\main\\resources\\test\\creep.png", il);
        
            // Create a model with the mesh and the texture
        CModel modelDefault = new CModel();
        modelDefault.setMesh((Mesh) am.getAsset("defaultMesh"));
        modelDefault.setTexture((Texture) am.getAsset("defaultTexture"));
        
            // Create the game world and create the camera and the
            // test box (with the created model)
        this.worldMain = new JWorld(this);
        
        JCamera camMain = new JCamera(this.worldMain);
        this.worldMain.createInstance(camMain);
        this.worldMain.createInstance(new JTestBox(this.worldMain, modelDefault));
        this.worldMain.setActiveCamera(camMain);
        
        this.window.disableVSync();
        */
        
        
            // Update the active world of the renderer
        Renderer3D.class.cast(this.window.getRenderer()).setActiveWorld(this.worldMain);
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
        this.window.changeTitle(""+this.window.getFPS());
        
        //this.timer.count();
    }

    @Override
    public void onClose() {
        this.assetManager.stop();
        this.window.stop();
        DebugUtils.log(this, "BYEEEE");
    }
}
