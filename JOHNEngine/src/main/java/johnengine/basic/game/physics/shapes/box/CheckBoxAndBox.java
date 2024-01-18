package johnengine.basic.game.physics.shapes.box;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.shapes.ICollisionCheck;

public class CheckBoxAndBox implements ICollisionCheck {

    @Override
    public boolean checkCollision(CTransform t1, CTransform t2) {
        return false;
    }
}
