package johnengine.basic.opengl.renderer.strvaochc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joml.Matrix4f;

import johnengine.basic.game.lights.JAmbientLight;
import johnengine.basic.game.lights.JDirectionalLight;
import johnengine.basic.game.lights.JPointLight;
import johnengine.basic.game.lights.JSpotLight;
import johnengine.basic.opengl.renderer.strvaochc.structs.SAmbientLight;
import johnengine.basic.opengl.renderer.strvaochc.structs.SDirectionalLight;
import johnengine.basic.opengl.renderer.strvaochc.structs.SPointLight;
import johnengine.basic.opengl.renderer.strvaochc.structs.SSpotLight;
import johnengine.core.renderer.IRenderBuffer;

public class RenderBuffer implements IRenderBuffer<RenderBuffer> {
    private static final SAmbientLight STRUCT_DEFAULT_AMBIENT_LIGHT = 
        new SAmbientLight(
            JAmbientLight.DEFAULT_COLOR,
            JAmbientLight.DEFAULT_INTENSITY
        );
    
    private static final SDirectionalLight STRUCT_DEFAULT_DIRECTIONAL_LIGHT = 
        new SDirectionalLight(
            JDirectionalLight.DEFAULT_COLOR,
            JDirectionalLight.DEFAULT_DIRECTION,
            JDirectionalLight.DEFAULT_INTENSITY
        );
    
    private List<RenderUnit> buffer;
    private Matrix4f projectionMatrix;
    private Matrix4f cameraMatrix;
    private SDirectionalLight directionalLight;
    
    private SAmbientLight ambientLight;
    private Map<JPointLight, SPointLight> pointLights;
    private Map<JSpotLight, SSpotLight> spotLights;
    
    public RenderBuffer() {
        this.buffer = new ArrayList<>();
        this.projectionMatrix = new Matrix4f();
        this.cameraMatrix = new Matrix4f();
        this.ambientLight = STRUCT_DEFAULT_AMBIENT_LIGHT;
        this.directionalLight = STRUCT_DEFAULT_DIRECTIONAL_LIGHT;
        this.pointLights = new HashMap<>();
        this.spotLights = new HashMap<>();
    }
    
    
    public void addRenderUnit(RenderUnit unit) {
        this.buffer.add(unit);
    }
    
    void addPointLight(
        JPointLight pointLight, 
        SPointLight pointLightStruct
    ) {
        this.pointLights.put(pointLight, pointLightStruct);
    }
    
    void addSpotLight(
        JSpotLight spotLight, 
        SSpotLight spotLightStruct
    ) {
        this.spotLights.put(spotLight, spotLightStruct);
    }
    
    @Override
    public RenderBuffer createInstance() {
        return new RenderBuffer();
    }
    
    
    public List<RenderUnit> getBuffer() {
        return this.buffer;
    }

    void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    void setCameraMatrix(Matrix4f cameraMatrix) {
        this.cameraMatrix = cameraMatrix;
    }

    void setAmbientLight(SAmbientLight ambientLight) {
        this.ambientLight = ambientLight;
    }
    
    void setDirectionalLight(SDirectionalLight directionalLight) {
        this.directionalLight = directionalLight;
    }
    
    
    Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }
    
    Matrix4f getCameraMatrix() {
        return this.cameraMatrix;
    }
    
    SAmbientLight getAmbientLight() {
        return this.ambientLight;
    }
    
    SDirectionalLight getDirectionalLight() {
        return this.directionalLight;
    }
    
    Set<Map.Entry<JPointLight, SPointLight>> getPointLights() {
        return this.pointLights.entrySet();
    }
    
    Set<Map.Entry<JSpotLight, SSpotLight>> getSpotLights() {
        return this.spotLights.entrySet();
    }
    
    SPointLight getPointLightStruct(JPointLight pointLight) {
        return this.pointLights.get(pointLight);
    }
}
