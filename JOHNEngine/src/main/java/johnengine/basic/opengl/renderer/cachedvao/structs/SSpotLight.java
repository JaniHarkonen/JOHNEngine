package johnengine.basic.opengl.renderer.cachedvao.structs;

import org.joml.Vector3f;

import johnengine.basic.opengl.renderer.uniforms.IStruct;

public class SSpotLight implements IStruct {

    public Vector3f v3Direction;
    public float fCutOff;
    public SPointLight pointLight;
}
