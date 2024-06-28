package johnengine.basic.opengl.renderer.gui;

import org.joml.Vector4f;

import johnengine.basic.assets.font.Font;
import johnengine.basic.opengl.renderer.ShaderProgram;
import johnengine.basic.opengl.renderer.vaocache.VAOCache;

class RendererContext {

    public ShaderProgram shaderProgram;
    public VAOCache vaoCache;
    public Font font;
    public Vector4f color;
    public Vector4f textColor;
    
    public RendererContext(
        ShaderProgram shaderProgram, 
        VAOCache vaoCache
    ) {
        this.shaderProgram = shaderProgram;
        this.vaoCache = vaoCache;
        this.font = null;
        this.color = null;
        this.textColor = null;
    }
    
    
    void setFont(Font font, Font defaultFont) {
        this.font = (font != null) ? font : defaultFont;
    }
    
    void setColor(
        Vector4f color, Vector4f defaultColor
    ) {
        this.color = (
            (color != null) ? 
            color : 
            defaultColor
        );
    }
    
    void setTextColor(
            Vector4f textColor, Vector4f defaultTextColor
        ) {
            this.textColor = (
                (textColor != null) ? 
                textColor : 
                defaultTextColor
            );
        }
}
