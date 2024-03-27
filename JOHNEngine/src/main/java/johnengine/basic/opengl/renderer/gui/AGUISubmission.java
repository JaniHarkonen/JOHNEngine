package johnengine.basic.opengl.renderer.gui;

import johnengine.basic.assets.font.Font;
import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderSubmissionStrategy;

public abstract class AGUISubmission<T extends IRenderable> 
    implements IRenderSubmissionStrategy<T> 
{
    protected final GUIRenderPass renderPass;
    protected Font font;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    
    protected AGUISubmission(
        Font font,
        float x,
        float y,
        float width,
        float height
    ) {
        this.renderPass = null;
        this.font = font;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    AGUISubmission(GUIRenderPass renderPass) {
        this.renderPass = renderPass;
    }
    
    
    public abstract void render(RendererContext context);
}
