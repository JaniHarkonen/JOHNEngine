package johnengine.basic.renderer.strvaochc;

import org.joml.Matrix4f;

import johnengine.basic.game.JCamera;
import johnengine.basic.game.components.geometry.CProjection;
import johnengine.basic.game.components.geometry.rewrite.CTransform;
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
        /*
        Matrix4f cameraMatrix = (new Matrix4f())
        .rotate(instance.getRotation().get())
        .translate(instance.getPosition().get());
        */
        CTransform cameraTransform = instance.getTransform();
        cameraTransform.update();

        Matrix4f cameraMatrix = (new Matrix4f())
        .rotate(cameraTransform.getRotation().getUnsafe())
        .translate(cameraTransform.getPosition().getUnsafe());
        //.rotate(cameraTransform.getRotationStale())
        //.translate(cameraTransform.getPositionStale());
        
        this.strategy.setCameraMatrix(/*instance.getTransform().get());*/cameraMatrix);
    }
}
