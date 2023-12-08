package johnengine.testing;

import johnengine.basic.assets.sceneobj.SceneObjectLoader;
import johnengine.basic.game.CController;
import johnengine.basic.game.CModel;
import johnengine.basic.game.JCamera;
import johnengine.basic.game.JWorld;
import johnengine.basic.renderer.Renderer3D;
import johnengine.basic.renderer.asset.Mesh;
import johnengine.basic.renderer.asset.Texture;
import johnengine.basic.window.Window;
import johnengine.core.AGame;
import johnengine.core.IEngineComponent;
import johnengine.core.assetmngr.AssetManager;
import johnengine.core.engine.Engine;
import johnengine.utils.counter.MilliCounter;

public class TestGame extends AGame {

    //private Networker networker;

    private MilliCounter timer;
    private JWorld worldMain;
    private long tickCounter;
    
    @Override
    public void onStart(Engine engine, IEngineComponent[] engineComponents) {
        this.engine = engine;
        this.window = (Window) engineComponents[0];
        this.assetManager = (AssetManager) engineComponents[1];
        
        this.tickCounter = 0;
        
        this.window
        .hideCursor()
        //.enterFullscreen()
        .disableVSync();
        
        AssetManager am = this.assetManager;
        
        Mesh mesh = new Mesh("man");
        am.declareAsset(mesh);
        
        SceneObjectLoader objLoader = new SceneObjectLoader();
        objLoader.expectMesh(mesh);
        objLoader.setMonitor(Renderer3D.class.cast(this.window.getRenderer()).getRenderBufferStrategy());
        am.loadFrom("C:\\Users\\User\\git\\JOHNEngine\\JOHNEngine\\src\\main\\resources\\test\\man.fbx", objLoader);
        
        
        Texture texture = new Texture("creep");
        Texture.Loader textureLoader = new Texture.Loader(texture);
        textureLoader.setMonitor(Renderer3D.class.cast(this.window.getRenderer()).getRenderBufferStrategy());
        //am.loadFrom("C:\\Users\\User\\git\\JOHNEngine\\JOHNEngine\\src\\main\\resources\\test\\creep.png", textureLoader);
        //am.loadFrom("D:\\jastur mille\\DeivantArt\\jastur retarted crop.png", textureLoader);
        
        this.worldMain = new JWorld(this);
        
        for( int i = 0; i < 1; i++ )
        {
            
            CModel model = new CModel();
            model.setMesh(mesh);
            JTestBox box = new JTestBox(this.worldMain, model);
            model.setPosition(box.getPosition());
            model.setRotation(box.getRotation());
            model.setScale(box.getScale());
            //model.setTexture(texture);
            this.worldMain.createInstance(box);
        }
        
        JCamera camera = new JCamera(this.worldMain);
        camera.setController(new CController(this.window.getInput(), camera));
        this.worldMain.createInstance(camera);
        
            // Update the active world of the renderer
        Renderer3D.class.cast(this.window.getRenderer()).setActiveWorld(this.worldMain);
        /*this.timer = new MilliCounter(1000) {
            @Override
            protected void performAction() {
                window.changeTitle(""+tickCounter);
                tickCounter = 0;
            }
        };*/
        
    }

    @Override
    public void tick(float deltaTime) {
        if( this.window.hasWindowClosed() )
        this.engine.stop();
        
        //this.worldMain.tick(deltaTime);
        this.worldMain.tick(deltaTime);
        //this.tickCounter++;
        this.window.moveMouse(this.window.getWidth() / 2, this.window.getHeight() / 2);
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
