package johnengine.basic.renderer.strvaochc;

import org.joml.Matrix4f;

import johnengine.basic.game.rewrite.JCamera;
import johnengine.core.renderer.ARenderBufferStrategoid;
import johnengine.core.winframe.AWindowFramework;

public class StrategoidCamera extends ARenderBufferStrategoid<JCamera, CachedVAORenderBufferStrategy> {

    public StrategoidCamera(CachedVAORenderBufferStrategy strategy) {
        super(strategy);
    }

    
    @Override
    public void execute(JCamera instance) {
        AWindowFramework window = strategy.getRenderer().getWindow();
        instance.getProjection().setViewDimensions(window.getWidth(), window.getHeight());
        instance.getProjection().calculate();
        this.strategy.setActiveCamera(instance);
        
        Matrix4f projectionMatrix = instance.getProjection().get();
        Matrix4f viewMatrix = (new Matrix4f())
        .translationRotate(instance.getPosition().get(), instance.getRotation().get());
        
            // camera matrix: projection matrix * view matrix
            // view matrix:   matrix rotated and translated according to camera
            //                rotation and position
        this.strategy.setCameraMatrix(projectionMatrix.mul(viewMatrix));
    }
}
