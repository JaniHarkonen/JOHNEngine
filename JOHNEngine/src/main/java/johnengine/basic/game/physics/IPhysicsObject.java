package johnengine.basic.game.physics;

import johnengine.basic.game.components.geometry.CTransform;

public interface IPhysicsObject {

        // Returns the associated physics component
    public CPhysics getPhysics();
    
        // Returns the transform representing the position, rotation and
        // scaling of the physics object
    public CTransform getTransform();
}
