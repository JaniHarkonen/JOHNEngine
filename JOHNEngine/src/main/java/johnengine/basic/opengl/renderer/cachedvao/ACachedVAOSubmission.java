package johnengine.basic.opengl.renderer.cachedvao;

import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderSubmissionStrategy;

public abstract class ACachedVAOSubmission<T extends IRenderable> implements IRenderSubmissionStrategy<T> {

    protected CachedVAORenderPass renderPass;
    
    public ACachedVAOSubmission(CachedVAORenderPass renderPass) {
        this.renderPass = renderPass;
    }
}
