package johnengine.basic.game.physics.shapes.sphere;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.shapes.ICollisionCheck;

public class CheckSphereAndSphere implements ICollisionCheck {

    @Override
    public boolean checkCollision(CTransform t1, CTransform t2) {
        return false;
    }
}
