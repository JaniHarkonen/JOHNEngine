package johnengine.basic.renderer.strvaochc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joml.Matrix4f;

import johnengine.basic.game.lights.JAmbientLight;
import johnengine.basic.game.lights.JPointLight;
import johnengine.basic.renderer.strvaochc.structs.SAmbientLight;
import johnengine.basic.renderer.strvaochc.structs.SPointLight;

public class RenderBuffer implements IHasRenderBuffer {
    private static final SAmbientLight STRUCT_DEFAULT_AMBIENT_LIGHT = new SAmbientLight(
        JAmbientLight.DEFAULT_COLOR,
        JAmbientLight.DEFAULT_INTENSITY
    );
    
    private List<RenderUnit> buffer;
    private Matrix4f projectionMatrix;
    private Matrix4f cameraMatrix;
        
    private SAmbientLight ambientLight;
    private Map<JPointLight, SPointLight> pointLights;
    
    public RenderBuffer() {
        this.buffer = new ArrayList<>();
        this.projectionMatrix = new Matrix4f();
        this.cameraMatrix = new Matrix4f();
        this.ambientLight = STRUCT_DEFAULT_AMBIENT_LIGHT;
        this.pointLights = new HashMap<>();
    }
    
    
    @Override
    public void addRenderUnit(RenderUnit unit) {
        this.buffer.add(unit);
    }
    
    
    public List<RenderUnit> getBuffer() {
        return this.buffer;
    }


    @Override
    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }


    @Override
    public void setCameraMatrix(Matrix4f cameraMatrix) {
        this.cameraMatrix = cameraMatrix;
    }


    @Override
    public void setAmbientLight(SAmbientLight ambientLight) {
        this.ambientLight = ambientLight;
    }


    @Override
    public void addPointLight(JPointLight pointLight, SPointLight pointLightStruct) {
        this.pointLights.put(pointLight, pointLightStruct);
    }
    
    
    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }
    
    public Matrix4f getCameraMatrix() {
        return this.cameraMatrix;
    }
    
    public SAmbientLight getAmbientLight() {
        return this.ambientLight;
    }
    
    public Set<Map.Entry<JPointLight, SPointLight>> getPointLights() {
        return this.pointLights.entrySet();
    }
}
