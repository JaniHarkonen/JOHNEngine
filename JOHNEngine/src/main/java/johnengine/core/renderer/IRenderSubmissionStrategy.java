package johnengine.core.renderer;

import johnengine.core.IRenderable;

public interface IRenderSubmissionStrategy<T extends IRenderable> {

    public void execute(T target);
}
