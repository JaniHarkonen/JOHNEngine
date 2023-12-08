package johnengine.basic.game;

import org.lwjgl.glfw.GLFW;

import johnengine.core.ITickable;
import johnengine.core.input.IInput;

public class CController implements ITickable {

    private IInput inputSource;
    private IControllable controlledInstance;
    
    public CController(IInput inputSource, IControllable controlledInstance) {
        this.inputSource = inputSource;
        this.controlledInstance = controlledInstance;
    }
    
    
    @Override
    public void tick(float deltaTime) {
        IInput.State<?> state = this.inputSource.getState();
        double mouseX = state.getMouseX();
        double mouseY = state.getMouseY();
        double mouseDeltaX = mouseX - (AGameObject.class.cast(this.controlledInstance).getGame().getWindow().getWidth() / 2);
        double mouseDeltaY = mouseY - (AGameObject.class.cast(this.controlledInstance).getGame().getWindow().getHeight() / 2);
        float sensitivity = 0.1f;
        
        if( mouseDeltaX != 0 )
        this.controlledInstance.rotateY((float) mouseDeltaX * sensitivity);
        
        if( mouseDeltaY != 0 )
        this.controlledInstance.rotateX((float) mouseDeltaY * sensitivity);
        
        float speed = 20.0f;
        if( state.isKeyDown(GLFW.GLFW_KEY_LEFT) )
        this.controlledInstance.rotateY(-speed * sensitivity);
        
        if( state.isKeyDown(GLFW.GLFW_KEY_RIGHT) )
        this.controlledInstance.rotateY(speed * sensitivity);
        
        if( state.isKeyDown(GLFW.GLFW_KEY_UP) )
        this.controlledInstance.rotateX(-speed * sensitivity);
        //this.controlledInstance.moveForward();
        
        if( state.isKeyDown(GLFW.GLFW_KEY_DOWN) )
        this.controlledInstance.rotateX(speed * sensitivity);
        
        if( state.isKeyDown(GLFW.GLFW_KEY_W) )
        this.controlledInstance.moveForward();
            
        if( state.isKeyDown(GLFW.GLFW_KEY_S) )
        this.controlledInstance.moveBackward();
        //this.controlledInstance.moveBackward();
    }
    
}
