package johnengine.basic.game;

public interface IControllable {
    
    public void rotateX(float angle);
    
    public void moveForward();
    
    public void moveBackward();
    
    public CController getController();
    
    public void setController(CController controller);
}
