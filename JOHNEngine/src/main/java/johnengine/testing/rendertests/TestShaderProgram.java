package johnengine.testing.rendertests;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import johnengine.basic.renderer.asset.Mesh.Face;
import johnengine.testing.DebugUtils;

public class TestShaderProgram {
    
    public static class Mesh {
        private Vector3f[] vertices = new Vector3f[] {
            new Vector3f(-0.5f, 0.5f, 0.0f),        // top left
            new Vector3f(-0.5f, -0.5f, 0.0f),       // bottom left
            new Vector3f(0.5f, -0.5f, 0.0f),        // bottom right
            new Vector3f(0.5f, 0.5f, 0.0f)          // top right
        };
        
        /*private float[] vertices = new float[] {
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f
        };*/
        
        /*private float[] colors = new float[] {
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f
        };*/
        
        private Vector2f[] uvs = new Vector2f[] {
            new Vector2f(0.0f, 1.0f), 
            new Vector2f(0.0f, 0.0f), 
            new Vector2f(1.0f, 0.0f), 
            new Vector2f(1.0f, 1.0f),
        };
        
        private Face[] faces = new Face[] {
            new Face(new int[] {0, 1, 3}),
            new Face(new int[] {3, 1, 2})
        };
        
        /*private int[] faces = new int[] {
            0, 1, 3,
            3, 1, 2
        };*/
        
        private int vao;
        
        public Mesh() {
            this.vao = GL30.glGenVertexArrays();
            GL30.glBindVertexArray(this.vao);
            
            int vbo = GL30.glGenBuffers();
            
            FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(this.vertices.length * 3);
            for( Vector3f vertex : this.vertices )
            {
                vertexBuffer.put(vertex.x());
                vertexBuffer.put(vertex.y());
                vertexBuffer.put(vertex.z());
            }
            
            vertexBuffer.flip();
            
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo);
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertexBuffer, GL30.GL_STATIC_DRAW);
            GL30.glEnableVertexAttribArray(0);
            GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0);
            
            MemoryUtil.memFree(vertexBuffer);
            
            /*vbo = GL30.glGenBuffers();
            FloatBuffer colorsBuffer = MemoryUtil.memAllocFloat(this.colors.length);
            colorsBuffer.put(0, colors);
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo);
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, colorsBuffer, GL30.GL_STATIC_DRAW);
            GL30.glEnableVertexAttribArray(1);
            GL30.glVertexAttribPointer(1, 3, GL30.GL_FLOAT, false, 0, 0);
            
            MemoryUtil.memFree(colorsBuffer);*/
            
            
            vbo = GL30.glGenBuffers();
            
            FloatBuffer uvBuffer = MemoryUtil.memAllocFloat(this.uvs.length * 2);
            for( Vector2f uv : this.uvs )
            {
                uvBuffer.put(uv.x());
                uvBuffer.put(uv.y());
            }
            
            uvBuffer.flip();

            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo);
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, uvBuffer, GL30.GL_STATIC_DRAW);
            GL30.glEnableVertexAttribArray(1);
            GL30.glVertexAttribPointer(1, 2, GL30.GL_FLOAT, false, 0, 0);
            
            MemoryUtil.memFree(uvBuffer);
            
            vbo = GL30.glGenBuffers();
            
            IntBuffer indexBuffer = MemoryUtil.memAllocInt(this.faces.length * 3);
            for( Face index : this.faces )
            {
                indexBuffer.put(index.get(0));
                indexBuffer.put(index.get(1));
                indexBuffer.put(index.get(2));
            }
            
            indexBuffer.flip();
            
            GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, vbo);
            GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL30.GL_STATIC_DRAW);
            MemoryUtil.memFree(indexBuffer);
            
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
            GL30.glBindVertexArray(0);
        }
        
        public void render() {
            GL30.glBindVertexArray(this.vao);
            GL30.glDrawElements(GL30.GL_TRIANGLES, this.vertices.length*3, GL30.GL_UNSIGNED_INT, 0);
        }
    }

    public static final String CAMERA_PROJECTION_MATRIX = "cameraProjectionMatrix";
    public static final String CAMERA_ORIENTATION_MATRIX = "cameraOrientationMatrix";
    
    private int handle;
    private final Map<String, Integer> uniforms;
    private Mesh mesh;
    private int textureHandle;
    private int textureSamplerUniform;
    
    public TestShaderProgram() {
        this.handle = GL30.glCreateProgram();
        
        try
        {
            int vertexShader = GL30.glCreateShader(GL30.GL_VERTEX_SHADER);
            GL30.glShaderSource(vertexShader, new String(Files.readAllBytes(Paths.get("C:\\Users\\User\\git\\JOHNEngine\\JOHNEngine\\src\\main\\resources\\test\\default.vert"))));
            GL30.glCompileShader(vertexShader);
            
            int fragmentShader = GL30.glCreateShader(GL30.GL_FRAGMENT_SHADER);
            GL30.glShaderSource(fragmentShader, new String(Files.readAllBytes(Paths.get("C:\\Users\\User\\git\\JOHNEngine\\JOHNEngine\\src\\main\\resources\\test\\default.frag"))));
            GL30.glCompileShader(fragmentShader);
            
            GL30.glAttachShader(this.handle, vertexShader);
            GL30.glAttachShader(this.handle, fragmentShader);
            
            GL30.glLinkProgram(this.handle);
            
            GL30.glDetachShader(this.handle, vertexShader);
            GL30.glDeleteShader(vertexShader);
            GL30.glDetachShader(this.handle, fragmentShader);
            GL30.glDeleteShader(fragmentShader);
        }
        catch( Exception e )
        {
            DebugUtils.log(this, "failed to load shaders");
        }
        
        this.uniforms = new HashMap<>();
        String name = "cameraProjectionMatrix";
        //this.uniforms.put("cameraProjectionMatrix", GL20.glGetUniformLocation(this.handle, "cameraProjectionMatrix"));
        this.mesh = new Mesh();
        
        int texWidth;
        int texHeight;
        ByteBuffer imageBuffer;
        try( MemoryStack memoryStack = MemoryStack.stackPush() )
        {
            IntBuffer widthBuffer = memoryStack.mallocInt(1);
            IntBuffer heightBuffer = memoryStack.mallocInt(1);
            IntBuffer channelCountBuffer = memoryStack.mallocInt(1);
            
            imageBuffer = STBImage.stbi_load(
                "C:\\Users\\User\\git\\JOHNEngine\\JOHNEngine\\src\\main\\resources\\test\\creep.png", 
                widthBuffer, 
                heightBuffer, 
                channelCountBuffer,
                4
            );
            
            texWidth = widthBuffer.get();
            texHeight = heightBuffer.get();
        }
        
        this.textureHandle = GL30.glGenTextures();
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, this.textureHandle);
        GL30.glPixelStorei(GL30.GL_UNPACK_ALIGNMENT, 1);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_NEAREST);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_NEAREST);
        GL30.glTexImage2D(
            GL30.GL_TEXTURE_2D,
            0, 
            GL30.GL_RGBA, 
            texWidth, 
            texHeight, 
            0, 
            GL30.GL_RGBA, 
            GL30.GL_UNSIGNED_BYTE, 
            imageBuffer
        );
        GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
        
        this.textureSamplerUniform = GL30.glGetUniformLocation(this.handle, "texSampler");
    }

    
    public void render() {
        this.bind();
        
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, this.textureHandle);
        this.mesh.render();
        
        this.unbind();
    }
    
    public void bind() {
        GL30.glUseProgram(this.handle);
    }
    
    public void unbind() {
        GL30.glUseProgram(0);
    }
    
    public void dispose() {
        GL30.glDeleteProgram(this.handle);
    }
    
    public void setUniform(String name, Matrix4f mat) {
        
    }
}
