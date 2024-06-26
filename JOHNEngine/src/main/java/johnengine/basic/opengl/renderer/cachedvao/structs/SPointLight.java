package johnengine.basic.opengl.renderer.cachedvao.structs;

import org.joml.Vector3f;

import johnengine.basic.opengl.renderer.uniforms.IStruct;

public class SPointLight implements IStruct {
    public Vector3f c3Light = null;
    public Vector3f v3Position = null;
    public float fIntensity = 0.0f;
    public SAttenuation attenuation = null;    
}
