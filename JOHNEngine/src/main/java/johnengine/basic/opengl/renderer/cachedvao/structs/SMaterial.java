package johnengine.basic.opengl.renderer.cachedvao.structs;

import org.joml.Vector4f;

import johnengine.basic.opengl.renderer.uniforms.IStruct;

public class SMaterial implements IStruct {

    public Vector4f c4Diffuse = null;
    public Vector4f c4Specular = null;
    public float fReflectance = 0.0f;
}
