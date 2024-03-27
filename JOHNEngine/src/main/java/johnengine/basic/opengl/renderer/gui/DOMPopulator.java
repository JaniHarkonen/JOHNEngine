package johnengine.basic.opengl.renderer.gui;

import java.util.List;

import johnengine.basic.game.gui.AGUIComponent;
import johnengine.basic.game.gui.JFrame;
import johnengine.basic.game.gui.JGUI;
import johnengine.core.renderer.IRenderBufferPopulator;
import johnengine.core.renderer.IRenderPass;

/**
 * Instead of calling submit() on each root node of the 
 * render context of the client render pass, this populator
 * traverses the scene graph of the GUI and constructs its
 * own tree consisting of render submissions. This is because 
 * submit() doesn't keep track of the branch of the scene 
 * graph that is currently being submitted, making it 
 * impossible to re-create what is, de facto, a copy of the 
 * graph. 
 * 
 * @author User
 *
 */
public class DOMPopulator implements IRenderBufferPopulator {
    
    private GUIRenderPass client;
    
    
    @Override
    public void execute(IRenderPass client) {
        this.client = (GUIRenderPass) client;
        
        JGUI gui = (JGUI) this.client.getRenderContext();
        for( JFrame frame : gui.getFrames() )
        {
            //client.executeSubmissionStrategy(frame);
            frame.submit(this.client);
            this.submitChildrenRecursively(frame.getChildren());
        }
        
        client.newBuffer();
    }
    
    
    private void submitChildrenRecursively(List<AGUIComponent> children) {
        for( AGUIComponent child : children )
        {
            //this.client.executeSubmissionStrategy(child);
            child.submit(this.client);
            this.submitChildrenRecursively(child.getChildren());
            this.client.getCurrentDOM().traverseUp();
        }
    }
}
