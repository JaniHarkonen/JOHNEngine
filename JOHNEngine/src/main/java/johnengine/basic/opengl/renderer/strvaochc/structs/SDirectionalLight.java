package johnengine.basic.opengl.renderer.strvaochc.structs;

import org.joml.Vector3f;

import johnengine.basic.opengl.renderer.uniforms.IStruct;

public class SDirectionalLight implements IStruct {

    public Vector3f c3Light;
    public Vector3f v3Direction;
    public float fIntensity;
    
    public SDirectionalLight(Vector3f c3Light, Vector3f v3Direction, float fIntensity) {
        this.c3Light = c3Light;
        this.v3Direction = v3Direction;
        this.fIntensity = fIntensity;
    }
    
    public SDirectionalLight() {
        this(null, null, 0.0f);
    }
}
