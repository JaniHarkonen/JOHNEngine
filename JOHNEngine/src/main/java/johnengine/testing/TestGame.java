package johnengine.testing;

import johnengine.basic.assets.sceneobj.Material;
import johnengine.basic.assets.sceneobj.SceneObjectLoader;
import johnengine.basic.game.JCamera;
import johnengine.basic.game.JWorld;
import johnengine.basic.game.components.CController;
import johnengine.basic.game.components.CModel;
import johnengine.basic.game.lights.JAmbientLight;
import johnengine.basic.game.lights.JDirectionalLight;
import johnengine.basic.game.lights.JPointLight;
import johnengine.basic.game.lights.JSpotLight;
import johnengine.basic.renderer.RendererGL;
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
        .disableVSync();
        
        //Window.class.cast(this.window).enterFullscreen();
        //Window.class.cast(this.window).resize(1000, 1000);
        
        AssetManager am = this.assetManager;
        
        Mesh mesh = new Mesh("man");
        am.declareAsset(mesh);
        
        SceneObjectLoader objLoader = new SceneObjectLoader();
        objLoader.expectMesh(mesh);
        objLoader.setMonitor(RendererGL.class.cast(this.window.getRenderer()).getGraphicsAssetProcessor());
        am.loadFrom("C:\\Users\\User\\git\\JOHNEngine\\JOHNEngine\\src\\main\\resources\\test\\man.fbx", objLoader);
        
        Texture texture = new Texture("creep");
        Texture.Loader textureLoader = new Texture.Loader(texture);
        textureLoader.setMonitor(RendererGL.class.cast(this.window.getRenderer()).getGraphicsAssetProcessor());
        am.loadFrom("C:\\Users\\User\\git\\JOHNEngine\\JOHNEngine\\src\\main\\resources\\test\\creep.png", textureLoader);
        //am.loadFrom("D:\\jastur mille\\DeivantArt\\jastur retarted crop.png", textureLoader);
        
        Material material = new Material();
        material.setTexture(texture);
        mesh.setMaterial(material);
        
        this.worldMain = new JWorld(this);
        
        CModel model = new CModel();
        model.setMesh(mesh);
        JTestBox box = new JTestBox(this.worldMain, model);
        box.attach(model);
        model.getTransform().getScale().inherit();
        //model.getTransform().inheritScale();
        
        //DebugUtils.log(this, model.getTransform().getScale());
        
        this.worldMain.createInstance(box);
        
        CModel debugmodel = new CModel();
        debugmodel.setMesh(mesh);
        debugmodel.setTexture(texture);
        
        JCamera camera = new JCamera(this.worldMain);
        camera.setController(new CController(this.window.getInput(), camera));
        this.worldMain.createInstance(camera);
        
        JAmbientLight ambientLight = new JAmbientLight(this.worldMain);
        this.worldMain.createInstance(ambientLight);
        
        JDirectionalLight directionalLight = new JDirectionalLight(this.worldMain);
        this.worldMain.createInstance(directionalLight);
        
        JPointLight pointLight = new JPointLight(this.worldMain);
        JSpotLight spotLight = new JSpotLight(this.worldMain);
        spotLight.setPointLight(pointLight);
        camera.attach(spotLight);
        
            // Update the active world of the renderer
        RendererGL.class.cast(this.window.getRenderer()).setActiveWorld(this.worldMain);
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
        
        this.worldMain.tick(deltaTime);
        this.tickCounter++;
        this.window.moveMouse(this.window.getWidth() / 2, this.window.getHeight() / 2);
        this.window.changeTitle("FPS: " + this.window.getFPS());
        //this.timer.count();
    }

    @Override
    public void onClose() {
        this.assetManager.stop();
        this.window.stop();
        DebugUtils.log(this, "BYEEEE");
    }
}
