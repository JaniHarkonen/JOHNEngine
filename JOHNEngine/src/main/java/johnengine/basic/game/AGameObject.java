package johnengine.basic.game;

import johnengine.core.AGame;
import johnengine.core.ITickable;
import johnengine.core.UUID;

public abstract class AGameObject implements ITickable {

    protected final AGame game;
    protected long id;
    protected boolean isActive;
    protected boolean isDestroyed;
    
    protected AGameObject(AGame game) {
        this.game = game;
        this.id = UUID.newLongUUID();   // Assign a unique ID using the global UUID-class
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
    
    public AGame getGame() {
        return this.game;
    }
}
