package johnengine.basic.game.gui;

import johnengine.basic.assets.mesh.Mesh;
import johnengine.basic.assets.texture.Texture;
import johnengine.core.renderer.IRenderPass;

public class JImage extends AGUIComponent {

    private Mesh mesh;
    private Texture imageTexture;
    
    public JImage(JGUI gui, Mesh mesh, Texture texture) {
        super(gui);
        this.mesh = mesh;
        this.imageTexture = texture;
    }

    
    @Override
    public void tick(float deltaTime) {
        
    }
    
    @Override
    public void submit(IRenderPass renderPass) {
        renderPass.executeSubmissionStrategy(this);
        
        for( AGUIComponent child : this.nodeManager.getChildren() )
        child.submit(renderPass);
    }
    
    
    public void setTexture(Texture imageTexture) {
        this.imageTexture = imageTexture;
    }
    
    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
    
    
    public Texture getTexture() {
        return this.imageTexture;
    }
    
    public Mesh getMesh() {
        return this.mesh;
    }
}
