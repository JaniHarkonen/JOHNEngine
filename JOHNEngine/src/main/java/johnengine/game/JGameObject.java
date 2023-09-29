package johnengine.game;

import johnengine.core.IHasTick;

public abstract class JGameObject implements IHasTick {

    private long id;
    private boolean isActive;
    private boolean isDestroyed;

    protected JGameObject(long id) {
        this.id = id;
        this.isActive = true;
        this.isDestroyed = false;
    }

    public void destroy() {
        this.isDestroyed = true;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public long getID() {
        return this.id;
    }
}
