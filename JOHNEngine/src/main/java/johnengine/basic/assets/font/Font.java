package johnengine.basic.assets.font;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector2f;
import org.joml.Vector3f;

import johnengine.basic.assets.IGeneratable;
import johnengine.basic.assets.mesh.IMeshGraphics;
import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.assets.texture.Texture;

public class Font implements IGeneratable {
    private Map<Character, Mesh> glyphMeshes;
    private Texture fontTexture;
    private String characterSet;
    private String name;
    private int cellWidth;
    private int cellHeight;
    private float cellUWidth;
    private float cellVHeight;
    private int numberOfColumns;
    private int numberOfRows;
    private IMeshGraphics meshGraphicsStrategy;
    
    public Font(
        String name,
        Texture fontTexture,
        String characterSet,
        int numberOfColumns,
        int numberOfRows
    ) {
        this.setTexture(fontTexture, numberOfColumns, numberOfRows);
        this.glyphMeshes = new HashMap<>();
        this.characterSet = characterSet;
        this.name = name;
        this.meshGraphicsStrategy = null;
    }
    
    
    @Override
    public boolean generate() {
        this.generateGlyphMeshes();
        return true;
    }
    
    @Override
    public boolean dispose() {
        for( Map.Entry<Character, Mesh> en : this.glyphMeshes.entrySet() )
        en.getValue().deload();
            
        return true;
    }
    
    private void generateGlyphMeshes() {
        for( int i = 0; i < this.characterSet.length(); i++ )
        {
            char character = this.characterSet.charAt(i);
            Mesh glyphMesh = this.createGlyphMesh(character);
            this.glyphMeshes.put(character, glyphMesh);
        }
    }
    
    private Mesh createGlyphMesh(char character) {
        float x = 0.0f;
        float y = 0.0f;
        float w = 16.0f;
        float h = 16.0f;
        
        Vector3f[] vertices = new Vector3f[] {
            new Vector3f(x, y, 0.0f),           // top-left
            new Vector3f(x + w, y, 0.0f),       // top-right
            new Vector3f(x + w, y + h, 0.0f),   // bottom-right
            new Vector3f(x, y + h, 0.0f),       // bottom-left
        };
        
        float u0 = character % this.numberOfColumns * this.cellUWidth;
        float v0 = character / this.numberOfColumns * this.cellVHeight;
        float u1 = u0 + this.cellUWidth;
        float v1 = v0 + this.cellVHeight;
        
        Vector2f[] uvs = new Vector2f[] {
            new Vector2f(u0, v0),
            new Vector2f(u1, v0),
            new Vector2f(u1, v1),
            new Vector2f(u0, v1)
        };
        
        Mesh.Face[] faces = new Mesh.Face[] {
            new Mesh.Face(new int[] {0, 1, 2}),
            new Mesh.Face(new int[] {2, 3, 0})
        };
        
        IMeshGraphics graphicsStrategy = this.meshGraphicsStrategy.duplicateStrategy();
        Mesh glyphMesh = Mesh.createMesh(
            this.name + "-glyph-mesh", 
            false, 
            vertices,
            new Vector3f[0], 
            uvs, 
            faces, 
            new Vector3f[0], 
            new Vector3f[0]
        );
        graphicsStrategy.setMesh(glyphMesh);
        glyphMesh.setGraphicsStrategy(graphicsStrategy);
        
        return glyphMesh;
    }
    
    
    /************************ SETTERS ************************/
    
    public void setTexture(Texture fontTexture, int numberOfColumns, int numberOfRows) {
        this.fontTexture = fontTexture;
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.cellUWidth = 1.0f / this.numberOfColumns;
        this.cellVHeight = 1.0f / this.numberOfRows;
    }
    
    public void setMeshGraphicsStrategy(IMeshGraphics graphicsStrategy) {
        this.meshGraphicsStrategy = graphicsStrategy;
    }
    
    
    /************************ GETTERS ************************/
    
    public Mesh getGlyphMesh(char character) {
        return this.glyphMeshes.get(character);
    }
    
    public Mesh[] getGlyphMeshes(String text) {
        Mesh[] meshes = new Mesh[text.length()];
        for( int i = 0; i < text.length(); i++ )
        meshes[i] = this.glyphMeshes.get(text.charAt(i));
        
        return meshes;
    }
    
    public Texture getTexture() {
        return this.fontTexture;
    }
    
    public int getFontWidth() {
        return this.cellWidth;
    }
    
    public int getFontHeight() {
        return this.cellHeight;
    }
}
