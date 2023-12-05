package johnengine.basic.renderer.strvaochc;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

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
        
        Vector3f viewPosition = instance.getPosition().get();
        Vector2f viewRotation = instance.getRotation().get();
        Matrix4f viewMatrix = (new Matrix4f())
        .identity()
        .rotateX(viewRotation.x)
        .rotateY(viewRotation.y)
        .translate(viewPosition.x, viewPosition.y, viewPosition.z);
        
        this.strategy.setViewMatrix(instance.getProjection().getMatrix().mul(viewMatrix));
    }
}
