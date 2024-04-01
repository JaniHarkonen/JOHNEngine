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
import johnengine.basic.game.components.CMouseListener;
import johnengine.basic.game.gui.JButton;
import johnengine.basic.game.gui.JForm;
import johnengine.basic.game.gui.JFrame;
import johnengine.basic.game.gui.JGUI;
import johnengine.basic.game.gui.JImage;
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
        .disableVSync();
        //.resize(1000, 1000);
        
        //this.window.enterFullscreen();
        //this.window.resize(1000, 1000);
        //this.engine.setTickRate(24);
        //this.window.lockCursorToCenter();
        //.enterFullscreen();
        
        AssetManager am = this.assetManager;
        am.setRootDirectory((new File("src/main/resources/test")).getAbsolutePath());
        
        Mesh mesh = new Mesh("man");
        this.loadMesh("brick/Brick.fbx", mesh);
        
        Texture texture = new Texture("creep");
        this.loadTexture("brick/Bricks082B_4K_Color.jpg", texture);
        
        Texture normalMap = new Texture("normale");
        this.loadTexture("brick/Bricks082B_4K_NormalDX.jpg", normalMap);
        
        Texture roughnessMap = new Texture("rough");
        this.loadTexture("brick/Bricks082B_4K_Roughness", roughnessMap);
        
        Texture testFont = new Texture("fon");
        this.loadTexture("font_arial20.png", testFont);
        
        Texture testFontBig = new Texture("big");
        this.loadTexture("font_irregular.png", testFontBig);
        
        Material material = new Material();
        material.setTexture(texture);
        material.setNormalMap(normalMap);
        mesh.setMaterial(material);
        
        this.worldMain = new JWorld(this);
        this.gui = new JGUI(this, this.window.getInput());
        
        CModel model = new CModel();
        model.setMesh(mesh);
        JTestBox box = new JTestBox(this.worldMain, model);
        box.attach(model);
        model.getTransform().getScale().inherit();
        
        this.worldMain.createInstance(box);
        
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
            JForm topLevelForm = new JForm(this.gui, 10, 10);
                JForm leftForm = new JForm(this.gui, 20, 20);
                    JImage leftFormImage = new JImage(this.gui, imageMesh, texture);
                    //JText leftFormText = new JText(this.gui, "left");
                    this.testText = new JText(this.gui, "left");
                    JButton leftButton = new JButton(this.gui, "O_O", imageMesh);
                        CMouseListener leftButtonMouseListener = new CMouseListener(
                            this.window.getInput(), 
                            new MouseKeyboardInputGL.MouseMove(), 
                            leftButton
                        ) {
                            @Override
                            public void eventOccurred(CMouseListener.EventContext context) {
                                switch( context.type )
                                {
                                    case CMouseListener.EMIT_MOUSE_PRESSED:
                                        DebugUtils.log(this, "mouse has been pressed :)");
                                        break;
                                        
                                    case CMouseListener.EMIT_MOUSE_ENTER:
                                        context.source.setColor(new Vector4f(1.0f, 0.5f, 0.5f, 1.0f));
                                        break;
                                        
                                    case CMouseListener.EMIT_MOUSE_LEAVE:
                                        context.source.setColor(new Vector4f(1.0f, 0.0f, 0.0f, 1.0f));
                                        break;
                                }
                            }
                        };
                        leftButtonMouseListener.listen(new MouseKeyboardInputGL.MousePressed(GLFW.GLFW_MOUSE_BUTTON_1), CMouseListener.EMIT_MOUSE_PRESSED);
                    leftButton.setMouseListener(leftButtonMouseListener);
                    leftButton.setColor(new Vector4f(1.0f, 0.0f, 0.0f, 1.0f));
                leftForm.addComponent(leftFormImage, 0, 0, 1, 1);
                leftForm.addComponentAndFinalize(leftButton, 0, 0, 10, 10);
                //leftForm.addComponentAndFinalize(/*leftFormText*/this.testText, 0, 0, 1, 1);
                JForm rightForm = new JForm(this.gui, 20, 20);
                    JImage rightFormImage = new JImage(this.gui, imageMesh, texture);
                    JText rightFormText = new JText(this.gui, "right");
                    rightFormText.setFont(bigFont);
                rightForm.addComponent(rightFormImage, 0, 0, 1, 1);
                rightForm.addComponentAndFinalize(rightFormText, 0, 0, 1, 1);
                    
                topLevelForm.addComponent(leftForm, 0, 0, 2, 2);
                topLevelForm.addComponentAndFinalize(rightForm, 8, 0, 2, 2);
            frame.addAndFinalize(topLevelForm);
            frame.setFont(textFont);
        this.gui.addFrame(frame);
        
        /*try {
        Thread.sleep(1000);
        }
        catch(Exception e) {}*/
        
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
            this.window.getRenderer().getGraphicsStrategy(mesh)
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
