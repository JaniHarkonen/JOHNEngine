package johnengine.basic.opengl.renderer.cachedvao;

import org.joml.Matrix4f;

import johnengine.basic.game.JCamera;
import johnengine.basic.game.components.geometry.CProjection;
import johnengine.basic.game.components.geometry.CTransform;
import johnengine.core.window.IWindow;

public class SubmitCamera extends ACachedVAOSubmission<JCamera> {

    public SubmitCamera(CachedVAORenderPass renderPass) {
        super(renderPass);
    }

    
    @Override
    public void execute(JCamera instance) {
        IWindow window = renderPass.getRenderer().getWindow();
        CProjection cameraProjection = instance.getProjection();
        cameraProjection.setViewDimensions(window.getWidth(), window.getHeight());
        cameraProjection.calculate();
        
        this.renderPass
        .getCurrentRenderBuffer()
        .setProjectionMatrix(cameraProjection.get());
        
            // camera matrix: projection matrix * view matrix
            // view matrix:   matrix rotated and translated according to camera
            //                rotation and position
        CTransform cameraTransform = instance.getTransform();
        cameraTransform.update();

        Matrix4f cameraMatrix = (new Matrix4f())
        .rotate(cameraTransform.getRotation().getUnsafe())
        .translate(cameraTransform.getPosition().getUnsafe());
        
        this.renderPass.getCurrentRenderBuffer().setCameraMatrix(cameraMatrix);
    }
}
