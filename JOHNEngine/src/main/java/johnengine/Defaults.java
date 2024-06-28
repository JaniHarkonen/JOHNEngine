package johnengine;

import java.nio.ByteBuffer;

import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;

import johnengine.basic.assets.mesh.MeshInfo;
import johnengine.basic.assets.sceneobj.Material;
import johnengine.basic.assets.texture.Texture;

public final class Defaults {
    
    /******************************* TEXTURES *******************************/
    
    /**
     * Texture information object containing the pixel data as well
     * as the width and the height of the default texture asset. 
     */
    public static final Texture.Info DEFAULT_TEXTURE_INFO;
    static {
            // T_T
        String bytes = 
            "0000000000000000" + 
            "0000000000000000" + 
            "0000000000000000" + 
            "0111110000111110" + 
            "0001000000001000" + 
            "0001000000001000" + 
            "0001000000001000" + 
            "0001000000001000" + 
            "0001000000001000" + 
            "0001000000001000" + 
            "0001000000001000" + 
            "0001000000001000" + 
            "0000011111100000" + 
            "0000000000000000" + 
            "0000000000000000" + 
            "0000000000000000";
        
            // WTF
        /*
        String bytes = 
            "0001000000001000" + 
            "0001000110001000" + 
            "0000101001010000" + 
            "0000010000100000" + 
            "0000000000000000" + 
            "0000011111110000" + 
            "0000000010000000" + 
            "0000000010000000" + 
            "0000000010000000" + 
            "0000000010000000" + 
            "0000000000000000" + 
            "0000011111100000" + 
            "0000000000100000" + 
            "0000000111100000" + 
            "0000000000100000" + 
            "0000000000100000";
          */  
        
        int width = 16;
        int height = 16;
        int channelCount = 4;
        ByteBuffer pixels = MemoryUtil.memAlloc(bytes.length() * channelCount);
        
            // Populate pixels upside-down due to OpenGL
        for( int i = bytes.length() - 1; i >= 0; i-- )
        {
            byte value = (bytes.charAt(i) == '1') ? (byte) 255 : 0;
            
            pixels.put((byte) value);
            pixels.put((byte) value);
            pixels.put((byte) value);
            pixels.put((byte) 255);
        }
        
        pixels.flip();
        
        DEFAULT_TEXTURE_INFO = new Texture.Info(pixels, width, height);
    }
    
    
    /**
     * Default texture asset.
     */
    public static final Texture DEFAULT_TEXTURE;
    static {
        DEFAULT_TEXTURE = new Texture("default-texture");
    }
    
    
    /******************************* SHADOWS *******************************/
    
    public static final int SHADOW_MAP_WIDTH;
    static {
        SHADOW_MAP_WIDTH = 4096;
    }
    
    public static final int SHADOW_MAP_HEIGHT;
    static {
        SHADOW_MAP_HEIGHT = 4096;
    }
    
    public static final int SHADOW_DEPTH_MAP_LEVEL_COUNT;
    static {
        SHADOW_DEPTH_MAP_LEVEL_COUNT = 4;
    }
    
    
    /******************************* MATERIALS *******************************/
    
    /**
     * Default diffuse color of a material.
     */
    public static final Vector4f DEFAULT_DIFFUSE_COLOR;
    static {
        DEFAULT_DIFFUSE_COLOR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    
    /**
     * Default specular highlight color of a material.
     */
    public static final Vector4f DEFAULT_SPECULAR_COLOR;
    static {
        DEFAULT_SPECULAR_COLOR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    
    /**
     * Default reflectance factor of a material.
     */
    public static final float DEFAULT_REFLECTANCE;
    static {
        DEFAULT_REFLECTANCE = 1.0f;
    }
    
    
    /**
     * Default material asset.
     */
    public static final Material DEFAULT_MATERIAL;
    static {
        DEFAULT_MATERIAL = new Material(
            DEFAULT_TEXTURE,
            DEFAULT_DIFFUSE_COLOR,
            DEFAULT_SPECULAR_COLOR,
            DEFAULT_REFLECTANCE
        );
    }
    
    
    /******************************* MESHES *******************************/
    
    /**
     * Default data of the default mesh information object.
     */
    public static final MeshInfo.Data DEFAULT_MESHINFO_DATA;
    static {
        DEFAULT_MESHINFO_DATA = new MeshInfo.Data();
    }
    
    
    /**
     * Default mesh information object containing the vertices, 
     * normals, UVs, faces, tangents and bitangents.
     */
    public static final MeshInfo DEFAULT_MESH_INFO;
    static {
        DEFAULT_MESH_INFO = new MeshInfo("default-mesh-info", true, DEFAULT_MESHINFO_DATA);
        MeshInfo.generateDefaults();
    }
    
    
    /******************************* GUI *******************************/
    
    /**
     * Default RGBA-color of a GUI-element.
     */
    public static final Vector4f DEFAULT_GUI_ELEMENT_COLOR;
    static {
        DEFAULT_GUI_ELEMENT_COLOR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    /**
     * Default RGBA-color of the text of a GUI-element.
     */
    public static final Vector4f DEFAULT_GUI_TEXT_COLOR;
    static {
        DEFAULT_GUI_TEXT_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    
    /******************************* WINDOW *******************************/
    
    public static final long WINDOW_MAX_FPS = 300;
}
