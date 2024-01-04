package johnengine.basic.renderer.strvaochc.structs;

import org.joml.Vector3f;

import johnengine.basic.renderer.uniforms.IStruct;

public class SSpotLight implements IStruct {

    public Vector3f v3Direction;
    public float fCutOff;
    public SPointLight pointLight;
}
