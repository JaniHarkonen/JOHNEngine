package johnengine.basic.game;

import johnengine.basic.game.components.geometry.CProjection;

public class JCamera extends AWorldObject {
    
    private CProjection viewProjection;

    public JCamera(JWorld world) {
        super(world);
        this.viewProjection = new CProjection();
    }


    @Override
    public void tick(float deltaTime) {
        
    }
    
    
    public CProjection getProjection() {
        return this.viewProjection;
    }
}
