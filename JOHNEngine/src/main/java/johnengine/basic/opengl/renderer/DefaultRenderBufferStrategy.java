package johnengine.basic.opengl.renderer;

import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderBufferStrategy;
import johnengine.core.renderer.IRenderContext;
import johnengine.core.renderer.IRenderPass;

public class DefaultRenderBufferStrategy implements IRenderBufferStrategy {
    
    @Override
    public void execute(IRenderPass client) {
        IRenderContext renderContext = client.getRenderContext();
        renderContext.startRenderBuffer();
        
        IRenderable instance;
        while( (instance = renderContext.nextInstance()) != null )
        instance.render(client);
        
        client.newBuffer();
    }
}
