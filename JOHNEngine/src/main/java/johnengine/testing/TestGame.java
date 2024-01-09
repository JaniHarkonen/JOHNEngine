package johnengine.testing;

import org.lwjgl.glfw.GLFW;

import johnengine.basic.assets.sceneobj.Material;
import johnengine.basic.assets.sceneobj.SceneObjectLoader;
import johnengine.basic.game.JCamera;
import johnengine.basic.game.JWorld;
import johnengine.basic.game.components.CController;
import johnengine.basic.game.components.CModel;
import johnengine.basic.game.input.ControlSchema;
import johnengine.basic.game.input.actions.ACTMoveBackward;
import johnengine.basic.game.input.actions.ACTMoveForward;
import johnengine.basic.game.input.actions.ACTMoveLeft;
import johnengine.basic.game.input.actions.ACTMoveRight;
import johnengine.basic.game.input.actions.ACTTurn;
import johnengine.basic.game.input.cvrters.MouseKeyboardBooleanConverter;
import johnengine.basic.game.input.cvrters.MouseKeyboardPointConverter;
import johnengine.basic.game.lights.JAmbientLight;
import johnengine.basic.game.lights.JDirectionalLight;
import johnengine.basic.game.lights.JPointLight;
import johnengine.basic.opengl.rewrite.WindowGL;
import johnengine.basic.opengl.input.MouseKeyboardInputGL;
import johnengine.basic.renderer.RendererGL;
import johnengine.basic.renderer.asset.Mesh;
import johnengine.basic.renderer.asset.Texture;
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
        this.window = (WindowGL) engineComponents[0];
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
        
        Texture normalMap = new Texture("normale");
        Texture.Loader normalMapLoader = new Texture.Loader(normalMap);
        normalMapLoader.setMonitor(RendererGL.class.cast(this.window.getRenderer()).getGraphicsAssetProcessor());
        am.loadFrom("C:\\Users\\User\\git\\JOHNEngine\\JOHNEngine\\src\\main\\resources\\test\\normale.png", normalMapLoader);
        
        Material material = new Material();
        material.setTexture(texture);
        material.setNormalMap(normalMap);
        mesh.setMaterial(material);
        
        this.worldMain = new JWorld(this);
        
        CModel model = new CModel();
        model.setMesh(mesh);
        JTestBox box = new JTestBox(this.worldMain, model);
        box.attach(model);
        model.getTransform().getScale().inherit();

        this.worldMain.createInstance(box);
        
        ControlSchema cs = new ControlSchema();
        cs.addBinding(
            new ACTMoveForward(), 
            new MouseKeyboardBooleanConverter(), 
            new MouseKeyboardInputGL.KeyDown(GLFW.GLFW_KEY_W)
        ).addBinding(
            new ACTMoveBackward(), 
            new MouseKeyboardBooleanConverter(), 
            new MouseKeyboardInputGL.KeyDown(GLFW.GLFW_KEY_S)
        ).addBinding(
            new ACTMoveLeft(), 
            new MouseKeyboardBooleanConverter(), 
            new MouseKeyboardInputGL.KeyDown(GLFW.GLFW_KEY_A)
        ).addBinding(
            new ACTMoveRight(), 
            new MouseKeyboardBooleanConverter(), 
            new MouseKeyboardInputGL.KeyDown(GLFW.GLFW_KEY_D)
        ).addBinding(
            new ACTTurn(), 
            new MouseKeyboardPointConverter(), 
            new MouseKeyboardInputGL.MouseMove()
        );
        
        JCamera camera = new JCamera(this.worldMain);
        CController controller = new CController();
        controller.setSchema(cs);
        controller.setSource(this.window.getInput());
        controller.setTarget(camera);
        camera.setController(controller);
        
        this.worldMain.createInstance(camera);
        
        JAmbientLight ambientLight = new JAmbientLight(this.worldMain);
        this.worldMain.createInstance(ambientLight);
        
        JDirectionalLight directionalLight = new JDirectionalLight(this.worldMain);
        this.worldMain.createInstance(directionalLight);
        
        JPointLight pointLight = new JPointLight(this.worldMain);
        this.worldMain.createInstance(pointLight);
        /*JSpotLight spotLight = new JSpotLight(this.worldMain);
        spotLight.setPointLight(pointLight);
        camera.attach(spotLight);*/
        //camera.attach(pointLight);
        
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
        //this.window.moveMouse(this.window.getWidth() / 2, this.window.getHeight() / 2);
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
