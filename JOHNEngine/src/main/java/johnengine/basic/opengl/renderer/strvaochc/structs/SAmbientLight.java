package johnengine.basic.opengl.renderer.strvaochc.structs;

import org.joml.Vector3f;

import johnengine.basic.opengl.renderer.uniforms.IStruct;

public class SAmbientLight implements IStruct {

    public Vector3f c3Ambient;
    public float fIntensity;
    
    public SAmbientLight(Vector3f c3Ambient, float fIntensity) {
        this.c3Ambient = c3Ambient;
        this.fIntensity = fIntensity;
    }
    
    public SAmbientLight() {
        this(null, 0.0f);
    }
}
