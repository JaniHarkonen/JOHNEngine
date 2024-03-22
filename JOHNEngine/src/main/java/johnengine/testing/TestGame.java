package johnengine.testing;

import java.io.File;

import org.json.JSONObject;
import org.lwjgl.glfw.GLFW;

import johnengine.basic.assets.IGraphicsStrategy;
import johnengine.basic.assets.font.Font;
import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.assets.sceneobj.Material;
import johnengine.basic.assets.sceneobj.SceneObjectLoadTask;
import johnengine.basic.assets.texture.Texture;
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
import johnengine.basic.opengl.renderer.RendererGL;
import johnengine.core.AGame;
import johnengine.core.IEngineComponent;
import johnengine.core.assetmngr.AssetManager;
import johnengine.core.engine.Engine;
import johnengine.utils.FontUtils;
import johnengine.utils.counter.MilliCounter;

public class TestGame extends AGame {

    //private Networker networker;

    private MilliCounter timer;
    private JWorld worldMain;
    private JGUI gui;
    private CText text;
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
        this.loadMesh("brick/Brick.fbx", mesh, am);
        
        Texture texture = new Texture("creep");
        this.loadTexture("brick/Bricks082B_4K_Color.jpg", texture, am);
        
        Texture normalMap = new Texture("normale");
        this.loadTexture("brick/Bricks082B_4K_NormalDX.jpg", normalMap, am);
        
        Texture roughnessMap = new Texture("rough");
        this.loadTexture("brick/Bricks082B_4K_Roughness", roughnessMap, am);
        
        Texture testFont = new Texture("fon");
        this.loadTexture("font_irregular.png", testFont, am);
        
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
        JSONObject fontJson = new JSONObject(
            "{\r\n" + 
            "  \"name\": \"Arial\",\r\n" + 
            "  \"size\": 32,\r\n" + 
            "  \"bold\": false,\r\n" + 
            "  \"italic\": false,\r\n" + 
            "  \"width\": 350,\r\n" + 
            "  \"height\": 125,\r\n" + 
            "  \"characters\": {\r\n" + 
            "    \"0\":{\"x\":116,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"1\":{\"x\":107,\"y\":81,\"width\":12,\"height\":25,\"originX\":-2,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"2\":{\"x\":40,\"y\":56,\"width\":19,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"3\":{\"x\":134,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"4\":{\"x\":59,\"y\":56,\"width\":19,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"5\":{\"x\":152,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"6\":{\"x\":78,\"y\":56,\"width\":19,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"7\":{\"x\":170,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"8\":{\"x\":188,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"9\":{\"x\":206,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \" \":{\"x\":272,\"y\":106,\"width\":3,\"height\":3,\"originX\":1,\"originY\":1,\"advance\":9},\r\n" + 
            "    \"!\":{\"x\":130,\"y\":81,\"width\":7,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"\\\"\":{\"x\":178,\"y\":106,\"width\":12,\"height\":10,\"originX\":0,\"originY\":24,\"advance\":11},\r\n" + 
            "    \"#\":{\"x\":312,\"y\":31,\"width\":20,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"$\":{\"x\":117,\"y\":0,\"width\":19,\"height\":29,\"originX\":1,\"originY\":25,\"advance\":18},\r\n" + 
            "    \"%\":{\"x\":194,\"y\":0,\"width\":28,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":28},\r\n" + 
            "    \"&\":{\"x\":97,\"y\":31,\"width\":22,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"'\":{\"x\":197,\"y\":106,\"width\":6,\"height\":10,\"originX\":0,\"originY\":24,\"advance\":6},\r\n" + 
            "    \"(\":{\"x\":59,\"y\":0,\"width\":11,\"height\":31,\"originX\":0,\"originY\":24,\"advance\":11},\r\n" + 
            "    \")\":{\"x\":70,\"y\":0,\"width\":11,\"height\":31,\"originX\":0,\"originY\":24,\"advance\":11},\r\n" + 
            "    \"*\":{\"x\":164,\"y\":106,\"width\":14,\"height\":12,\"originX\":1,\"originY\":24,\"advance\":12},\r\n" + 
            "    \"+\":{\"x\":109,\"y\":106,\"width\":19,\"height\":17,\"originX\":0,\"originY\":20,\"advance\":19},\r\n" + 
            "    \",\":{\"x\":190,\"y\":106,\"width\":7,\"height\":10,\"originX\":-1,\"originY\":4,\"advance\":9},\r\n" + 
            "    \"-\":{\"x\":231,\"y\":106,\"width\":12,\"height\":5,\"originX\":1,\"originY\":11,\"advance\":11},\r\n" + 
            "    \".\":{\"x\":243,\"y\":106,\"width\":7,\"height\":5,\"originX\":-1,\"originY\":4,\"advance\":9},\r\n" + 
            "    \"/\":{\"x\":68,\"y\":81,\"width\":13,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":9},\r\n" + 
            "    \":\":{\"x\":64,\"y\":106,\"width\":7,\"height\":19,\"originX\":-1,\"originY\":18,\"advance\":9},\r\n" + 
            "    \";\":{\"x\":158,\"y\":81,\"width\":7,\"height\":24,\"originX\":-1,\"originY\":18,\"advance\":9},\r\n" + 
            "    \"<\":{\"x\":71,\"y\":106,\"width\":19,\"height\":18,\"originX\":0,\"originY\":20,\"advance\":19},\r\n" + 
            "    \"=\":{\"x\":145,\"y\":106,\"width\":19,\"height\":12,\"originX\":0,\"originY\":17,\"advance\":19},\r\n" + 
            "    \">\":{\"x\":90,\"y\":106,\"width\":19,\"height\":18,\"originX\":0,\"originY\":20,\"advance\":19},\r\n" + 
            "    \"?\":{\"x\":224,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"@\":{\"x\":0,\"y\":0,\"width\":33,\"height\":31,\"originX\":0,\"originY\":24,\"advance\":32},\r\n" + 
            "    \"A\":{\"x\":222,\"y\":0,\"width\":25,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"B\":{\"x\":0,\"y\":56,\"width\":20,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"C\":{\"x\":50,\"y\":31,\"width\":24,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"D\":{\"x\":119,\"y\":31,\"width\":22,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"E\":{\"x\":20,\"y\":56,\"width\":20,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"F\":{\"x\":97,\"y\":56,\"width\":19,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":20},\r\n" + 
            "    \"G\":{\"x\":247,\"y\":0,\"width\":25,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":25},\r\n" + 
            "    \"H\":{\"x\":207,\"y\":31,\"width\":21,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"I\":{\"x\":137,\"y\":81,\"width\":7,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"J\":{\"x\":52,\"y\":81,\"width\":16,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":16},\r\n" + 
            "    \"K\":{\"x\":141,\"y\":31,\"width\":22,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"L\":{\"x\":18,\"y\":81,\"width\":17,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"M\":{\"x\":272,\"y\":0,\"width\":25,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":27},\r\n" + 
            "    \"N\":{\"x\":228,\"y\":31,\"width\":21,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"O\":{\"x\":297,\"y\":0,\"width\":25,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":25},\r\n" + 
            "    \"P\":{\"x\":249,\"y\":31,\"width\":21,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"Q\":{\"x\":136,\"y\":0,\"width\":25,\"height\":26,\"originX\":0,\"originY\":24,\"advance\":25},\r\n" + 
            "    \"R\":{\"x\":74,\"y\":31,\"width\":23,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"S\":{\"x\":270,\"y\":31,\"width\":21,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"T\":{\"x\":163,\"y\":31,\"width\":22,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":20},\r\n" + 
            "    \"U\":{\"x\":291,\"y\":31,\"width\":21,\"height\":25,\"originX\":-1,\"originY\":24,\"advance\":23},\r\n" + 
            "    \"V\":{\"x\":322,\"y\":0,\"width\":25,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"W\":{\"x\":161,\"y\":0,\"width\":33,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":30},\r\n" + 
            "    \"X\":{\"x\":0,\"y\":31,\"width\":25,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"Y\":{\"x\":25,\"y\":31,\"width\":25,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":21},\r\n" + 
            "    \"Z\":{\"x\":185,\"y\":31,\"width\":22,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":20},\r\n" + 
            "    \"[\":{\"x\":81,\"y\":0,\"width\":10,\"height\":31,\"originX\":0,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"\\\\\":{\"x\":81,\"y\":81,\"width\":13,\"height\":25,\"originX\":2,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"]\":{\"x\":91,\"y\":0,\"width\":10,\"height\":31,\"originX\":1,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"^\":{\"x\":128,\"y\":106,\"width\":17,\"height\":15,\"originX\":1,\"originY\":24,\"advance\":15},\r\n" + 
            "    \"_\":{\"x\":250,\"y\":106,\"width\":22,\"height\":4,\"originX\":2,\"originY\":-3,\"advance\":18},\r\n" + 
            "    \"`\":{\"x\":222,\"y\":106,\"width\":9,\"height\":6,\"originX\":0,\"originY\":24,\"advance\":11},\r\n" + 
            "    \"a\":{\"x\":218,\"y\":81,\"width\":19,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"b\":{\"x\":242,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"c\":{\"x\":0,\"y\":106,\"width\":17,\"height\":19,\"originX\":0,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"d\":{\"x\":260,\"y\":56,\"width\":18,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"e\":{\"x\":237,\"y\":81,\"width\":19,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"f\":{\"x\":94,\"y\":81,\"width\":13,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"g\":{\"x\":278,\"y\":56,\"width\":18,\"height\":25,\"originX\":1,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"h\":{\"x\":35,\"y\":81,\"width\":17,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":18},\r\n" + 
            "    \"i\":{\"x\":144,\"y\":81,\"width\":7,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":7},\r\n" + 
            "    \"j\":{\"x\":101,\"y\":0,\"width\":10,\"height\":31,\"originX\":3,\"originY\":24,\"advance\":7},\r\n" + 
            "    \"k\":{\"x\":296,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":16},\r\n" + 
            "    \"l\":{\"x\":151,\"y\":81,\"width\":7,\"height\":25,\"originX\":0,\"originY\":24,\"advance\":7},\r\n" + 
            "    \"m\":{\"x\":192,\"y\":81,\"width\":26,\"height\":19,\"originX\":0,\"originY\":18,\"advance\":27},\r\n" + 
            "    \"n\":{\"x\":17,\"y\":106,\"width\":17,\"height\":19,\"originX\":0,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"o\":{\"x\":256,\"y\":81,\"width\":19,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"p\":{\"x\":314,\"y\":56,\"width\":18,\"height\":25,\"originX\":0,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"q\":{\"x\":332,\"y\":56,\"width\":18,\"height\":25,\"originX\":1,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"r\":{\"x\":51,\"y\":106,\"width\":13,\"height\":19,\"originX\":0,\"originY\":18,\"advance\":11},\r\n" + 
            "    \"s\":{\"x\":275,\"y\":81,\"width\":18,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"t\":{\"x\":119,\"y\":81,\"width\":11,\"height\":25,\"originX\":1,\"originY\":24,\"advance\":9},\r\n" + 
            "    \"u\":{\"x\":34,\"y\":106,\"width\":17,\"height\":19,\"originX\":0,\"originY\":18,\"advance\":18},\r\n" + 
            "    \"v\":{\"x\":293,\"y\":81,\"width\":18,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"w\":{\"x\":165,\"y\":81,\"width\":27,\"height\":19,\"originX\":2,\"originY\":18,\"advance\":23},\r\n" + 
            "    \"x\":{\"x\":311,\"y\":81,\"width\":18,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"y\":{\"x\":0,\"y\":81,\"width\":18,\"height\":25,\"originX\":1,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"z\":{\"x\":329,\"y\":81,\"width\":18,\"height\":19,\"originX\":1,\"originY\":18,\"advance\":16},\r\n" + 
            "    \"{\":{\"x\":33,\"y\":0,\"width\":13,\"height\":31,\"originX\":1,\"originY\":24,\"advance\":11},\r\n" + 
            "    \"|\":{\"x\":111,\"y\":0,\"width\":6,\"height\":31,\"originX\":-1,\"originY\":24,\"advance\":8},\r\n" + 
            "    \"}\":{\"x\":46,\"y\":0,\"width\":13,\"height\":31,\"originX\":1,\"originY\":24,\"advance\":11},\r\n" + 
            "    \"~\":{\"x\":203,\"y\":106,\"width\":19,\"height\":7,\"originX\":0,\"originY\":15,\"advance\":19}\r\n" + 
            "  }\r\n" + 
            "}"
        );
        
        Font textFont = FontUtils.jsonToFont(
            "gui-font", 
            testFont,
            "0123456789 !\"#$%&'()*+,-./:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~", 
            fontJson
        );
        textFont.setMeshGraphicsStrategy(renderer.getGraphicsStrategy(new Mesh("temp")));
        textFont.generate();
        
        /*try {
        Thread.sleep(1000);
        }
        catch(Exception e) {}*/
        
        CText guiText = new CText("", this.window.getInput());
        guiText.setFont(textFont);
        this.gui.addElement(guiText);
        this.text = guiText;
        
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
    }
    
    private void loadMesh(String relativePath, Mesh mesh, AssetManager am) {
        am.declareAsset(mesh);
        
        IGraphicsStrategy graphicsStrategy = this.window.getRenderer().getGraphicsStrategy(mesh);
        mesh.setGraphicsStrategy(graphicsStrategy);
        SceneObjectLoadTask objLoadTask = new SceneObjectLoadTask();
        objLoadTask.expectMesh(
            mesh, 
            this.window.getRenderer().getGraphicsStrategy(mesh)
        );
        am.scheduleFrom(relativePath, objLoadTask);
    }
    
    private void loadTexture(String relativePath, Texture texture, AssetManager am) {
        am.declareAsset(texture);
        
        IGraphicsStrategy graphicsStrategy = this.window.getRenderer().getGraphicsStrategy(texture);
        texture.setGraphicsStrategy(graphicsStrategy);
        Texture.LoadTask textureLoadTask = new Texture.LoadTask(
            graphicsStrategy, 
            texture
        );
        am.scheduleFrom(relativePath, textureLoadTask);
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
        this.text.setText(
            "fps: " + this.window.getFPS() + 
            "\ntick: " + this.engine.getTickRate() + 
            "\nheap: " + this.convertToLargestByte(Runtime.getRuntime().totalMemory())
        );
        //this.timer.count();
        
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
