package johnengine.basic.opengl.renderer.gui;

import johnengine.basic.opengl.renderer.ShaderProgram;
import johnengine.basic.opengl.renderer.vaocache.VAOCache;

class RendererContext {

    public ShaderProgram shaderProgram;
    public VAOCache vaoCache;
    public float topLevelX;
    public float topLevelY;
    public float topLevelWidth;
    public float topLevelHeight;
    public float topLevelColumnWidth;
    public float topLevelRowHeight;
    public int topLevelColumns;
    public int topLevelRows;
    
    public RendererContext(ShaderProgram shaderProgram, VAOCache vaoCache) {
        this.shaderProgram = shaderProgram;
        this.vaoCache = vaoCache;
        this.topLevelX = 0.0f;
        this.topLevelY = 0.0f;
        this.topLevelWidth = 0.0f;
        this.topLevelHeight = 0.0f;
        this.topLevelColumnWidth = 0.0f;
        this.topLevelRowHeight = 0.0f;
        this.topLevelColumns = 0;
        this.topLevelRows = 0;
    }
    
    
    public String stringify() {
        return (
            this + "\n" +
            "shaderProgram: " + this.shaderProgram + "\n" +
            "vaoCache: " + this.vaoCache + "\n" +
            "containerX: " + this.topLevelX + "\n" +
            "containerY: " + this.topLevelY + "\n" +
            "containerWidth: " + this.topLevelWidth + "\n" +
            "containerHeight: " + this.topLevelHeight + "\n" +
            "containerColumnWidth: " + this.topLevelColumnWidth + "\n" +
            "containerRowHeight: " + this.topLevelRowHeight + "\n" +
            "containerColumns: " + this.topLevelColumns + "\n" + 
            "containerRows: " + this.topLevelRows
        );
    }
}
