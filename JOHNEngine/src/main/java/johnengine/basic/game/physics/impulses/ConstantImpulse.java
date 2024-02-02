package johnengine.basic.game.physics.impulses;

import johnengine.basic.game.physics.AImpulse;
import johnengine.basic.game.physics.Force;

public class ConstantImpulse extends AImpulse {

    public ConstantImpulse() {
        super(new Force());
    }

    @Override
    public void tick(float deltaTime) { }   // Ignored, as there is no expiration condition
}
