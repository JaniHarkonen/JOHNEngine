package johnengine.basic.game;

import johnengine.core.AGame;
import johnengine.core.ITickable;

public abstract class AGameObject implements ITickable {

    private final AGame game;
    private long id;
    private boolean isActive;
    private boolean isDestroyed;
    
    protected AGameObject(AGame game, long id) {
        this.game = game;
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
    
    void setID(long id) {
        this.id = id;
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
