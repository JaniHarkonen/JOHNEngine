package johnengine.basic.opengl.renderer.gui;

import johnengine.basic.assets.font.Font;
import johnengine.basic.opengl.renderer.ShaderProgram;
import johnengine.basic.opengl.renderer.vaocache.VAOCache;

class RendererContext {

    public ShaderProgram shaderProgram;
    public VAOCache vaoCache;
    public Font font;
    
    public RendererContext(
        ShaderProgram shaderProgram, 
        VAOCache vaoCache
    ) {
        this.shaderProgram = shaderProgram;
        this.vaoCache = vaoCache;
        this.font = null;
    }
    
    
    void setFont(Font font, Font defaultFont) {
        this.font = (font != null) ? font : defaultFont;
    }
}
