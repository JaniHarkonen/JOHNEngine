package johnengine.basic.game.rewrite;

public interface IControllable {

    public void moveForward(float intensity);
    public void moveBackward(float intensity);
    public void moveLeft(float intensity);
    public void moveRight(float intensity);
    public void turn(float deltaX, float deltaY);
}
