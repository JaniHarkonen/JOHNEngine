package johnengine.testing;

import java.io.File;

import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

import johnengine.basic.assets.IGraphicsStrategy;
import johnengine.basic.assets.font.Font;
import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.assets.sceneobj.Material;
import johnengine.basic.assets.sceneobj.SceneObjectLoadTask;
import johnengine.basic.assets.textasset.TextAsset;
import johnengine.basic.assets.texture.Texture;
import johnengine.basic.game.JCamera;
import johnengine.basic.game.JWorld;
import johnengine.basic.game.components.CController;
import johnengine.basic.game.components.CModel;
import johnengine.basic.game.gui.JForm;
import johnengine.basic.game.gui.JFrame;
import johnengine.basic.game.gui.JGUI;
import johnengine.basic.game.gui.JText;
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
import johnengine.basic.opengl.renderer.RendererGL;
import johnengine.core.AGame;
import johnengine.core.IEngineComponent;
import johnengine.core.assetmngr.AssetManager;
import johnengine.core.engine.Engine;
import johnengine.extra.jegmd.GUIBuilder;
import johnengine.utils.FontUtils;
import johnengine.utils.counter.MilliCounter;

public class TestGame extends AGame {

    //private Networker networker;

    private MilliCounter timer;
    private JWorld worldMain;
    private JGUI gui;
    private long tickCounter;
    private Physics physics;
    private Physics.World physicsWorld;
    private JText testText;
    private Material material;
    
    @Override
    public void onStart(Engine engine, IEngineComponent[] engineComponents) {
        this.engine = engine;
        this.window = (WindowGL) engineComponents[0];
        this.assetManager = (AssetManager) engineComponents[1];
        
        this.tickCounter = 0;
        
        RendererGL renderer = this.window.getRenderer();
        renderer.setResourceRootFolder((new File("src/main/resources/test")).getAbsolutePath());
        
        this.window
        .lockCursorToCenter()
        .disableVSync()
        .resize(800, 600);
        
        //this.window.enterFullscreen();
        //this.window.resize(1000, 1000);
        //this.engine.setTickRate(24);
        //this.window.lockCursorToCenter();
        //.enterFullscreen();
        
        AssetManager am = this.assetManager;
        am.setRootDirectory((new File("src/main/resources/test")).getAbsolutePath());
        
        Mesh mesh = new Mesh("man");
        this.loadMesh("brick/Brick.fbx", mesh);
        
        Mesh manMesh = new Mesh("manman");
        this.loadMesh("man.fbx", manMesh);
        
        Texture texture = new Texture("creep");
        this.loadTexture("brick/Bricks082B_4K_Color.jpg", texture);
        
        Texture normalMap = new Texture("normale");
        this.loadTexture("brick/Bricks082B_4K_NormalDX.jpg", normalMap);
        
        Texture roughnessMap = new Texture("rough");
        this.loadTexture("brick/Bricks082B_4K_Roughness.jpg", roughnessMap);
        
        Texture creepTexture = new Texture("creeper");
        this.loadTexture("creep.png", creepTexture);
        
        Texture testFont = new Texture("fon");
        this.loadTexture("font_arial20.png", testFont);
        
        Texture testFontBig = new Texture("big");
        this.loadTexture("font_irregular.png", testFontBig);
        
        Material manMaterial = new Material();
        manMaterial.setTexture(creepTexture);
        manMesh.setMaterial(manMaterial);
        
        Material material = new Material();
        this.material = material;
        material.setTexture(texture);
        material.setNormalMap(normalMap);
        material.setRoughnessMap(roughnessMap);
        mesh.setMaterial(material);
        
        this.worldMain = new JWorld(this);
        this.gui = new JGUI(this, this.window.getInput());
        
        CModel model = new CModel();
        model.setMesh(mesh);
        JTestBox box = new JTestBox(this.worldMain);
        box.attach(model);
        DebugUtils.log(this, model.getMesh().getInfo());
        model.getTransform().getScale().inherit();
        
        CModel manModel = new CModel();
        manModel.setMesh(manMesh);
        JTestMan man = new JTestMan(this.worldMain, manModel);
        man.attach(manModel);
        DebugUtils.log(this, manModel.getMesh().getInfo());
        manModel.getTransform().getScale().inherit();
        
        this.worldMain.createInstance(box);
        this.worldMain.createInstance(man);
        
        ControlSchema cs = new ControlSchema();
        cs.bind(
            new MouseKeyboardInputGL.KeyHeld(GLFW.GLFW_KEY_W),
            new ACTMoveForward(), 
            new MouseKeyboardBooleanConverter()
        ).bind(
            new MouseKeyboardInputGL.KeyHeld(GLFW.GLFW_KEY_S),
            new ACTMoveBackward(), 
            new MouseKeyboardBooleanConverter()
        ).bind(
            new MouseKeyboardInputGL.KeyHeld(GLFW.GLFW_KEY_A),
            new ACTMoveLeft(), 
            new MouseKeyboardBooleanConverter()
        ).bind(
            new MouseKeyboardInputGL.KeyHeld(GLFW.GLFW_KEY_D),
            new ACTMoveRight(), 
            new MouseKeyboardBooleanConverter()
        ).bind(
            new MouseKeyboardInputGL.MouseMove(),
            new ACTTurn(), 
            new MouseKeyboardPointConverter()
        );
        
        JCamera camera = new JCamera(this.worldMain);
        JTestPlayer player = new JTestPlayer(this.worldMain);
        CController controller = new CController();
        controller.setSchema(cs);
        controller.setSource(this.window.getInput());
        player.attach(camera);
        player.setController(controller);
        
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
        Font textFont = FontUtils.jsonToFont(
            "gui-font", 
            testFont,
            "0123456789 !\"#$%&'()*+,-./:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~", 
            DebugUtils.createDefaultFontJson()
        );
        textFont.setMeshGraphicsStrategy(renderer.getGraphicsStrategy(new Mesh("temp")));
        textFont.generate();
        
        Font bigFont = FontUtils.jsonToFont(
            "gui-font-big", 
            testFontBig, 
            "0123456789 !\"#$%&'()*+,-./:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~", 
            DebugUtils.createDefaultFontJsonBig()
        );
        bigFont.setMeshGraphicsStrategy(renderer.getGraphicsStrategy(new Mesh("temp3")));
        bigFont.generate();
        
        Mesh imageMesh = new Mesh("temp2");
        imageMesh.setGraphicsStrategy(renderer.getGraphicsStrategy(imageMesh));
        
        JFrame frame = new JFrame(this.gui, 0, 0, 640, 480);
            JForm form = new JForm(this.gui, 2, 2);
                this.testText = new JText(this.gui, "");
                this.testText.setTextColor(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f));
            form.addComponentAndFinalize(this.testText, 0, 0, 1, 1);
        frame.addAndFinalize(form);
        frame.setFont(textFont);
        this.gui.addFrame(frame);
        
            // Update the active world and the GUI of the renderer
        renderer
        .getRenderPassManager()
        .getPass("scene-renderer")
        .setRenderContext(this.worldMain);
        
        renderer
        .getRenderPassManager()
        .getPass("gui-renderer")
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
        
        TextAsset guiSource = new TextAsset("gui-source-code");
        TextAsset.LoadTask guiLoadTask = new TextAsset.LoadTask(am.getRootDirectory() + "\\gui.gui", guiSource);
        guiLoadTask.load();
        
        GUIBuilder guiBuilder = new GUIBuilder(guiSource.getAsset().get());
        guiBuilder.buildGUI();
    }
    
    private void loadMesh(String relativePath, Mesh mesh) {
        this.assetManager.declareAsset(mesh);
        
        IGraphicsStrategy graphicsStrategy = this.window.getRenderer().getGraphicsStrategy(mesh);
        mesh.setGraphicsStrategy(graphicsStrategy);
        SceneObjectLoadTask objLoadTask = new SceneObjectLoadTask();
        objLoadTask.expectMesh(
            mesh,
            graphicsStrategy
        );
        this.assetManager.scheduleFrom(relativePath, objLoadTask);
    }
    
    private void loadTexture(String relativePath, Texture texture) {
        this.assetManager.declareAsset(texture);
        
        IGraphicsStrategy graphicsStrategy = this.window.getRenderer().getGraphicsStrategy(texture);
        texture.setGraphicsStrategy(graphicsStrategy);
        Texture.LoadTask textureLoadTask = new Texture.LoadTask(
            graphicsStrategy, 
            texture
        );
        this.assetManager.scheduleFrom(relativePath, textureLoadTask);
    }

    @Override
    public void tick(float deltaTime) {
        if( this.window.hasWindowClosed() )
        this.engine.stop();
        
        this.worldMain.tick(deltaTime);
        this.physics.update(deltaTime, this.physicsWorld);
        this.tickCounter++;
        //this.window.moveMouse(this.window.getWidth() / 2, this.window.getHeight() / 2);
        //this.window.changeTitle("FPS: " + this.window.getFPS());
        this.testText.setTextString(
            "FPS: " + this.window.getFPS() + 
            "\nTICK: " + this.engine.getTickRate() + 
            "\nHEAP: " + this.convertToLargestByte(Runtime.getRuntime().totalMemory())
        );
        //this.timer.count();
        this.gui.tick(deltaTime);
        
        if( this.window.getInput().getEvents().contains(new MouseKeyboardInputGL.KeyPressed(GLFW.GLFW_KEY_ESCAPE)) )
        {
            if( this.window.isCursorLockedToCenter() )
            this.window.releaseCursor();
            else
            this.window.lockCursorToCenter();
        }
        
        if( this.window.getInput().getEvents().contains(new MouseKeyboardInputGL.KeyHeld(GLFW.GLFW_KEY_KP_ADD)) )
        {
            //this.material.setReflectance(this.material.getReflectance() + 0.1f);
            //this.material.setDiffuseColor(new Vector4f(this.material.getDiffuseColor().x + 0.1f, this.material.getDiffuseColor().y + 0.1f, this.material.getDiffuseColor().z + 0.1f, 1.0f));
            //this.material.setDiffuseColor(diffuseColor);
            this.material.setSpecularColor(new Vector4f(this.material.getSpecularColor().x + 0.1f, this.material.getSpecularColor().y + 0.1f, this.material.getSpecularColor().z + 0.1f, 1.0f));
            DebugUtils.log(this, "reflectance increased: " + this.material.getReflectance(), "diffuse color increased: " + this.material.getDiffuseColor().toString());
        }
        
        if( this.window.getInput().getEvents().contains(new MouseKeyboardInputGL.KeyHeld(GLFW.GLFW_KEY_KP_SUBTRACT)) )
        {
            //this.material.setReflectance(this.material.getReflectance() - 0.1f);
            //this.material.setDiffuseColor(new Vector4f(this.material.getDiffuseColor().x - 0.1f, this.material.getDiffuseColor().y - 0.1f, this.material.getDiffuseColor().z - 0.1f, 1.0f));
            this.material.setSpecularColor(new Vector4f(this.material.getSpecularColor().x - 0.1f, this.material.getSpecularColor().y - 0.1f, this.material.getSpecularColor().z - 0.1f, 1.0f));
            DebugUtils.log(this, "reflectance increased: " + this.material.getReflectance());
        }
    }
    
    private String convertToLargestByte(long bytes) {
        String[] abbreviations = new String[] {
            " bytes",
            "KB",
            "MB",
            "GB"
        };
        
        long divisor = (long) Math.pow(1000, abbreviations.length - 1);
        int s = abbreviations.length - 1;
        for( int i = s; i > 0; i-- )
        {
            long largest = bytes / divisor;
            
            if( largest > 0 )
            return largest + abbreviations[i];
            
            divisor /= 1000;
        }
        
        return bytes + abbreviations[0];
    }

    @Override
    public void onClose() {
        this.assetManager.stop();
        this.window.stop();
        DebugUtils.log(this, "BYEEEE");
    }
}
