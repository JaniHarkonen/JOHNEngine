package johnengine.basic.opengl.renderer.strvaochc;

import org.joml.Matrix4f;

import johnengine.basic.game.JCamera;
import johnengine.basic.game.components.geometry.CProjection;
import johnengine.basic.game.components.geometry.CTransform;
import johnengine.core.window.IWindow;

public class StrategoidCamera extends ACachedVAOStrategoid<JCamera> {

    public StrategoidCamera(CachedVAORenderPass strategy) {
        super(strategy);
    }

    
    @Override
    public void execute(JCamera instance) {
        IWindow window = strategy.getRenderer().getWindow();
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
        
        this.strategy.setCameraMatrix(cameraMatrix);
    }
}
