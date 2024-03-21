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
    
    /************************* Font-class *************************/
    
    public static class Glyph {
        private final char character;
        private final float x;
        private final float y;
        private final float width;
        private final float height;
        private final float originX;
        private final float originY;
        private final float advance;
        
        private Mesh mesh;
        private Font font;
        private float u0;
        private float v0;
        private float u1;
        private float v1;
        
        public Glyph(
            char character,
            float x,
            float y,
            float width,
            float height,
            float originX,
            float originY,
            float advance
        ) {
            this.character = character;
            this.mesh = null;
            this.font = null;
            
            this.x = x;
            this.y = y;
            
            this.width = width;
            this.height = height;
            
            this.originX = originX;
            this.originY = originY;
            this.advance = advance;
            
            this.u0 = 0.0f;
            this.v0 = 0.0f;
            this.u1 = 0.0f;
            this.v1 = 0.0f;
        }
        
        public Glyph(char character) {
            this(character, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        }
        
        
        public char getCharacter() {
            return this.character;
        }
        
        public Mesh getMesh() {
            return this.mesh;
        }
        
        public Font getFont() {
            return this.font;
        }
        
        public float getX() {
            return this.x;
        }
        
        public float getY() {
            return this.y;
        }
        
        public float getWidth() {
            return this.width;
        }
        
        public float getHeight() {
            return this.height;
        }
        
        public float getOriginX() {
            return this.originX;
        }
        
        public float getOriginY() {
            return this.originY;
        }
        
        public float getAdvance() {
            return this.advance;
        }
        
        public float getU0() {
            return this.u0;
        }
        
        public float getV0() {
            return this.v0;
        }
        
        public float getU1() {
            return this.u1;
        }
        
        public float getV1() {
            return this.v1;
        }
    }
    
    
    /************************* Font-class *************************/
    
    private Map<Character, Font.Glyph> glyphs;
    private String name;
    private Texture fontTexture;
    private float textureWidth;
    private float textureHeight;
    private IMeshGraphics meshGraphicsStrategy;
    
    public Font(
        String name,
        Texture fontTexture,
        float textureWidth,
        float textureHeight
    ) {
        this.name = name;
        this.fontTexture = fontTexture;
        this.glyphs = new HashMap<>();
        
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        
        this.meshGraphicsStrategy = null;
    }
    
    
    @Override
    public boolean generate() {
        for( Map.Entry<Character, Font.Glyph> en : this.glyphs.entrySet() )
        {
            Font.Glyph glyph = en.getValue();
            
            float glyphX = glyph.getX();
            float glyphY = glyph.getY();
            float glyphWidth = glyph.getWidth();
            float glyphHeight = glyph.getHeight();
            
            float x = 0.0f;
            float y = 0.0f;
            float w = glyphWidth;
            float h = glyphHeight;
            
            Vector3f[] vertices = new Vector3f[] {
                new Vector3f(x, y, 0.0f),           // top-left
                new Vector3f(x + w, y, 0.0f),       // top-right
                new Vector3f(x + w, y + h, 0.0f),   // bottom-right
                new Vector3f(x, y + h, 0.0f),       // bottom-left
            };
            
            float u0 = glyphX / this.textureWidth;
            float v0 = glyphY / this.textureHeight;
            float u1 = u0 + glyphWidth / this.textureWidth;
            float v1 = v0 + glyphHeight / this.textureHeight;
            
            Vector2f[] uvs = new Vector2f[] {
                new Vector2f(u0, v0),
                new Vector2f(u1, v0),
                new Vector2f(u1, v1),
                new Vector2f(u0, v1)
            };
            
            glyph.u0 = u0;
            glyph.v0 = v0;
            glyph.u1 = u1;
            glyph.v1 = v1;
            
            Mesh.Face[] faces = new Mesh.Face[] {
                new Mesh.Face(new int[] {0, 1, 2}),
                new Mesh.Face(new int[] {2, 3, 0})
            };
            
            glyph.mesh = Mesh.createAndLoadMesh(
                this.name + "-glyph-mesh-" + glyph.getCharacter(), 
                false, 
                this.meshGraphicsStrategy.duplicateStrategy(),
                vertices,
                new Vector3f[0], 
                uvs, 
                faces, 
                new Vector3f[0], 
                new Vector3f[0]
            );
            
            glyph.font = this;
        }
        
        return true;
    }
    
    @Override
    public boolean dispose() {
        for( Map.Entry<Character, Font.Glyph> en : this.glyphs.entrySet() )
        en.getValue().mesh.deload();
            
        return true;
    }
    
    public void addGlyph(char glyphCharacter, Font.Glyph glyph) {
        glyph.font = this;
        this.glyphs.put(glyphCharacter, glyph);
    }
    
    
    /************************ SETTERS ************************/
    
    public void setTexture(Texture fontTexture) {
        this.fontTexture = fontTexture;
    }
    
    public void setMeshGraphicsStrategy(IMeshGraphics graphicsStrategy) {
        this.meshGraphicsStrategy = graphicsStrategy;
    }
    
    
    /************************ GETTERS ************************/
    
    public Font.Glyph getGlyph(char character) {
        return this.glyphs.get(character);
    }
    
    public Texture getTexture() {
        return this.fontTexture;
    }
}
