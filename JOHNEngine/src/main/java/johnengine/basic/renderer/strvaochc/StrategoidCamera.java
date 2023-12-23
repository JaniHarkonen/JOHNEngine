package johnengine.basic.renderer.strvaochc;

import org.joml.Matrix4f;

import johnengine.basic.game.JCamera;
import johnengine.core.winframe.AWindowFramework;

public class StrategoidCamera extends ACachedVAOStrategoid<JCamera> {

    public StrategoidCamera(CachedVAORenderStrategy strategy) {
        super(strategy);
    }

    
    @Override
    public void execute(JCamera instance) {
        AWindowFramework window = strategy.getRenderer().getWindow();
        instance.getProjection().setViewDimensions(window.getWidth(), window.getHeight());
        instance.getProjection().calculate();
        
        this.strategy.setProjectionMatrix(instance.getProjection().get());
        
        Matrix4f cameraMatrix = (new Matrix4f())
        .rotate(instance.getRotation().get())
        .translate(instance.getPosition().get());
        
            // camera matrix: projection matrix * view matrix
            // view matrix:   matrix rotated and translated according to camera
            //                rotation and position
        this.strategy.setCameraMatrix(cameraMatrix);
    }
}
