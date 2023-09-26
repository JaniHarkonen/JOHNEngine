package game;

public abstract class JGameObject {

	protected long id;
	protected boolean isActive;
	protected boolean isDestroyed;
	
	protected JGameObject(long id) {
		this.id = id;
		this.isActive = true;
		this.isDestroyed = false;
	}
	
	public abstract void tick(float deltaTime);
	
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
