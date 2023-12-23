package johnengine.basic.renderer.strvaochc;

import org.joml.Matrix4f;

import johnengine.basic.game.lights.JPointLight;
import johnengine.basic.game.lights.JSpotLight;
import johnengine.basic.renderer.strvaochc.structs.SAmbientLight;
import johnengine.basic.renderer.strvaochc.structs.SDirectionalLight;
import johnengine.basic.renderer.strvaochc.structs.SPointLight;
import johnengine.basic.renderer.strvaochc.structs.SSpotLight;

public interface IHasRenderBuffer {

    public void addRenderUnit(RenderUnit unit);
    
    public void setProjectionMatrix(Matrix4f projectionMatrix);
    
    public void setCameraMatrix(Matrix4f cameraMatrix);
    
    public void setAmbientLight(SAmbientLight ambientLight);
    
    public void setDirectionalLight(SDirectionalLight directionalLight);
    
    public void addPointLight(JPointLight pointLight, SPointLight pointLightStruct);
    
    public void addSpotLight(JSpotLight spotLight, SSpotLight spotLightStruct);
    
    public SPointLight getPointLightStruct(JPointLight pointLight);
}
