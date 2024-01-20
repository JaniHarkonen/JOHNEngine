package johnengine.basic.game.physics;

import johnengine.basic.game.components.CPhysics;
import johnengine.basic.game.components.geometry.CTransform;

public interface IPhysicsObject {

    public CPhysics getPhysics();
    public CTransform getTransform();
}
