package johnengine.basic.renderer.strvaochc;

import org.joml.Matrix4f;

import johnengine.basic.game.lights.JPointLight;
import johnengine.basic.renderer.strvaochc.structs.SAmbientLight;
import johnengine.basic.renderer.strvaochc.structs.SPointLight;

public interface IHasRenderBuffer {

    public void addRenderUnit(RenderUnit unit);
    
    public void setProjectionMatrix(Matrix4f projectionMatrix);
    
    public void setCameraMatrix(Matrix4f cameraMatrix);
    
    public void setAmbientLight(SAmbientLight ambientLight);
    
    public void addPointLight(JPointLight pointLight, SPointLight pointLightStruct);
}
