package johnengine.testing;


import java.io.File;

import org.lwjgl.glfw.GLFW;

import johnengine.basic.assets.font.Font;
import johnengine.basic.assets.sceneobj.Material;
import johnengine.basic.assets.sceneobj.SceneObjectLoader;
import johnengine.basic.game.JCamera;
import johnengine.basic.game.JGUI;
import johnengine.basic.game.JWorld;
import johnengine.basic.game.components.CController;
import johnengine.basic.game.components.CModel;
import johnengine.basic.game.gui.CText;
import johnengine.basic.game.input.ControlSchema;
import johnengine.basic.game.input.actions.ACTMoveBackward;
import johnengine.basic.game.input.actions.ACTMoveForward;
import johnengine.basic.game.input.actions.ACTMoveLeft;
import johnengine.basic.game.input.actions.ACTMoveRight;
import johnengine.basic.game.input.actions.ACTTurn;
import johnengine.basic.game.input.cvrters.MouseKeyboardBooleanConverter;
import johnengine.basic.game.input.cvrters.MouseKeyboardPointConverter;
import johnengine.basic.game.lights.JAmbientLight;
import johnengine.basic.game.lights.JPointLight;
import johnengine.basic.game.physics.Physics;
import johnengine.basic.opengl.WindowGL;
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
    private JGUI gui;
    private long tickCounter;
    private Physics physics;
    private Physics.World physicsWorld;
    
    @Override
    public void onStart(Engine engine, IEngineComponent[] engineComponents) {
        this.engine = engine;
        this.window = (WindowGL) engineComponents[0];
        this.assetManager = (AssetManager) engineComponents[1];
        
        this.tickCounter = 0;
        
        RendererGL renderer = this.window.getRenderer();
        renderer.setResourceRootFolder((new File("src/main/resources/test")).getAbsolutePath());
        
        this.window
        .hideCursor()
        .disableVSync();
        //.resize(1000, 1000);
        
        //this.window.enterFullscreen();
        //this.window.resize(1000, 1000);
        //this.engine.setTickRate(24);
        this.window.lockCursorToCenter();
        
        AssetManager am = this.assetManager;
        am.setRootDirectory((new File("src/main/resources/test")).getAbsolutePath());
        
        Mesh mesh = new Mesh("man");
        this.loadMesh("brick/Brick.fbx", mesh, am);
        
        Texture texture = new Texture("creep");
        this.loadTexture("brick/Bricks082B_4K_Color.jpg", texture, am);
        
        Texture normalMap = new Texture("normale");
        this.loadTexture("brick/Bricks082B_4K_NormalDX.jpg", normalMap, am);
        
        Texture testFont = new Texture("fon");
        this.loadTexture("font.png", testFont, am);
        
        Material material = new Material();
        material.setTexture(texture);
        material.setNormalMap(normalMap);
        mesh.setMaterial(material);
        
        this.worldMain = new JWorld(this);
        this.gui = new JGUI(this);
        
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
        JTestPlayer player = new JTestPlayer(this.worldMain);
        CController controller = new CController();
        controller.setSchema(cs);
        controller.setSource(this.window.getInput());
        player.attach(camera);
        player.setController(controller);
        
        //this.worldMain.createInstance(camera);
        this.worldMain.createInstance(player);
        
        JAmbientLight ambientLight = new JAmbientLight(this.worldMain);
        this.worldMain.createInstance(ambientLight);
        /*
        JDirectionalLight directionalLight = new JDirectionalLight(this.worldMain);
        this.worldMain.createInstance(directionalLight);*/
        
        JPointLight pointLight = new JPointLight(this.worldMain);
        this.worldMain.createInstance(pointLight);
        /*JSpotLight spotLight = new JSpotLight(this.worldMain);
        spotLight.setPointLight(pointLight);
        camera.attach(spotLight);*/
        //camera.attach(pointLight);
        
            // Populate GUI
        Font textFont = new Font(
            "gui-font", 
            testFont, 
            " !\"#$%&'()*+´-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~", 
            17, 
            8
        );
        textFont.setGlyphMeshLoaderMonitor(RendererGL.class.cast(this.window.getRenderer()).getGraphicsAssetProcessor());
        textFont.generate();
        
        try {
        Thread.sleep(1000);
        }
        catch(Exception e) {}
        
        CText guiText = new CText("hello world :)", this.window.getInput());
        guiText.setFont(textFont);
        this.gui.addElement(guiText);
        
            // Update the active world and the GUI of the renderer
        //RendererGL renderer = RendererGL.class.cast(this.window.getRenderer());
        renderer
        .getStrategyOfRenderingPass("scene-renderer")
        .setRenderContext(this.worldMain);
        
        renderer
        .getStrategyOfRenderingPass("gui-renderer")
        .setRenderContext(this.gui);
        /*this.timer = new MilliCounter(1000) {
            @Override
            protected void performAction() {
                window.changeTitle(""+tickCounter);
                tickCounter = 0;
            }
        };*/
        
        this.physics = new Physics();
        this.physicsWorld = new Physics.World();
        this.physicsWorld.addObject(player);
        this.physicsWorld.addObject(box);
    }
    
    private void loadMesh(String relativePath, Mesh mesh, AssetManager am) {
        am.declareAsset(mesh);
        SceneObjectLoader objLoader = new SceneObjectLoader();
        objLoader.expectMesh(mesh);
        objLoader.setMonitor(this.window.getRenderer().getGraphicsAssetProcessor());
        am.loadFrom(relativePath, objLoader);
    }
    
    private void loadTexture(String relativePath, Texture texture, AssetManager am) {
        am.declareAsset(texture);
        
        Texture.Loader textureLoader = new Texture.Loader(texture);
        textureLoader.setMonitor(this.window.getRenderer().getGraphicsAssetProcessor());
        am.loadFrom(relativePath, textureLoader);
    }

    @Override
    public void tick(float deltaTime) {
        if( this.window.hasWindowClosed() )
        this.engine.stop();
        
        this.worldMain.tick(deltaTime);
        this.physics.update(deltaTime, this.physicsWorld);
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
