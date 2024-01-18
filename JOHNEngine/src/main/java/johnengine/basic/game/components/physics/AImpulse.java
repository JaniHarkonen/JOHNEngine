package johnengine.basic.game.components.physics;

import johnengine.core.ITickable;

public abstract class AImpulse implements ITickable {
    protected float magnitude;
    protected boolean hasExpired;

    public AImpulse() {
        this.magnitude = 0.0f;
        this.hasExpired = false;
    }
    
    
    protected void expire(float currentTime, float duration) {
        if( currentTime >= duration )
        this.hasExpired = true;
    }
    
    
    public boolean hasExpired() {
        return this.hasExpired;
    }
    
    public float getMagnitude() {
        return this.magnitude;
    }
}
