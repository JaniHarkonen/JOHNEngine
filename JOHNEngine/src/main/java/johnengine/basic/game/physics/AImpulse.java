package johnengine.basic.game.physics;

import johnengine.core.ITickable;

public abstract class AImpulse implements ITickable {

    protected final Force force;
    protected boolean hasExpired;
    
    public AImpulse(Force force) {
        this.force = force;
        this.hasExpired = false;
    }
    
    
    public Force getForce() {
        return this.force;
    }
    
    public boolean hasExpired() {
        return this.hasExpired;
    }
}
