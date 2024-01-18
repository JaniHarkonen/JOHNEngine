package johnengine.basic.game.physics.shapes.plane;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.shapes.ICollisionCheck;

public class CheckPlaneAndPlane implements ICollisionCheck {

    @Override
    public boolean checkCollision(CTransform t1, CTransform t2) {
        return false;
    }
}
