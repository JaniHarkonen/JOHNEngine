package johnengine.basic.opengl.renderer.gui;

import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderSubmissionStrategy;

public abstract class AGUISubmission<T extends IRenderable> 
    implements IRenderSubmissionStrategy<T> 
{
    protected final GUIRenderPass renderPass;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    
    protected AGUISubmission(
        float x,
        float y,
        float width,
        float height
    ) {
        this.renderPass = null;
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
