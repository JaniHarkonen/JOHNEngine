package johnengine.basic.game;

import org.lwjgl.glfw.GLFW;

import johnengine.core.ITickable;
import johnengine.core.input.IInput;

public class CController implements ITickable {

    private IInput inputSource;
    private IControllable controlledInstance;
    private double previousMouseX;
    private double previousMouseY;
    
    public CController(IInput inputSource, IControllable controlledInstance) {
        this.inputSource = inputSource;
        this.controlledInstance = controlledInstance;
        this.previousMouseX = 0;
        this.previousMouseY = 0;
    }
    
    
    @Override
    public void tick(float deltaTime) {
        IInput.State<?> state = this.inputSource.getState();
        double mouseX = state.getMouseX();
        double mouseY = state.getMouseY();
        double mouseDeltaX = mouseX - this.previousMouseX;
        double mouseDeltaY = mouseY - this.previousMouseY;
        float sensitivity = 0.001f;
        
        if( mouseDeltaX != 0 )
        this.controlledInstance.rotateX((float) mouseDeltaX * sensitivity);
        
        float speed = 20.0f;
        if( state.isKeyDown(GLFW.GLFW_KEY_LEFT) )
        this.controlledInstance.rotateX(-speed * sensitivity);
        
        if( state.isKeyDown(GLFW.GLFW_KEY_RIGHT) )
        this.controlledInstance.rotateX(speed * sensitivity);
        
        if( state.isKeyDown(GLFW.GLFW_KEY_UP) )
        this.controlledInstance.moveForward();
        
        if( state.isKeyDown(GLFW.GLFW_KEY_DOWN) )
        this.controlledInstance.moveBackward();
        
        this.previousMouseX = mouseX;
        this.previousMouseY = mouseY;
    }
    
}
