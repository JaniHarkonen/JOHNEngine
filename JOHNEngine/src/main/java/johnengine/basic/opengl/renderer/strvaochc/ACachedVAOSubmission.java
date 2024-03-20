package johnengine.basic.opengl.renderer.strvaochc;

import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderSubmissionStrategy;

public abstract class ACachedVAOSubmission<T extends IRenderable> implements IRenderSubmissionStrategy<T> {

    protected CachedVAORenderPass strategy;
    
    public ACachedVAOSubmission(CachedVAORenderPass strategy) {
        this.strategy = strategy;
    }
}
