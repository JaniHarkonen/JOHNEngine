package johnengine.basic.game.input;

import johnengine.basic.game.components.CController;

public interface IControllable {

    public void control(AControllerAction action);
    
    /*public void moveForward(float intensity);
    public void moveBackward(float intensity);
    public void moveLeft(float intensity);
    public void moveRight(float intensity);
    public void turn(float deltaX, float deltaY);

    */
    public default void setController(CController controller) {
        controller.setTarget(IControllable.this);
    }
}
