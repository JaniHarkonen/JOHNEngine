package johnengine.basic.opengl.renderer;

import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderBufferPopulator;
import johnengine.core.renderer.IRenderContext;
import johnengine.core.renderer.IRenderPass;

public class DefaultRenderBufferPopulator implements IRenderBufferPopulator {
    
    @Override
    public void execute(IRenderPass client) {
        IRenderContext renderContext = client.getRenderContext();
        renderContext.startRenderBuffer();
        
        IRenderable instance;
        while( (instance = renderContext.nextInstance()) != null )
        instance.submit(client);
        
        client.newBuffer();
    }
}
