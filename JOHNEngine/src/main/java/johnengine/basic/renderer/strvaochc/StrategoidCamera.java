package johnengine.basic.renderer.strvaochc;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import johnengine.basic.game.JCamera;
import johnengine.basic.game.components.geometry.CProjection;
import johnengine.basic.game.components.geometry.CTransform;
import johnengine.core.winframe.AWindowFramework;

public class StrategoidCamera extends ACachedVAOStrategoid<JCamera> {

    public StrategoidCamera(CachedVAORenderStrategy strategy) {
        super(strategy);
    }

    
    @Override
    public void execute(JCamera instance) {
        AWindowFramework window = strategy.getRenderer().getWindow();
        CProjection cameraProjection = instance.getProjection();
        cameraProjection.setViewDimensions(window.getWidth(), window.getHeight());
        cameraProjection.calculate();
        
        this.strategy.setProjectionMatrix(instance.getProjection().get());
        
            // camera matrix: projection matrix * view matrix
            // view matrix:   matrix rotated and translated according to camera
            //                rotation and position
        CTransform cameraTransform = instance.getTransform();
        cameraTransform.update();

        Matrix4f cameraMatrix = (new Matrix4f())
        .rotate(cameraTransform.getRotation().getUnsafe())
        .translate(cameraTransform.getPosition().getUnsafe());
        //.translate(new Vector3f(cameraTransform.getPosition().getUnsafe()).negate());
        
        this.strategy.setCameraMatrix(cameraMatrix);
    }
}
