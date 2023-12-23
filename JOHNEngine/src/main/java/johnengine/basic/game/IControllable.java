package johnengine.basic.game;

import johnengine.basic.game.components.CController;

public interface IControllable {
    
    public void rotateY(float angle);
    
    public void rotateX(float angle);
    
    public void moveForward();
    
    public void moveBackward();
    
    public void moveLeft();
    
    public void moveRight();
    
    public CController getController();
    
    public void setController(CController controller);
}
