package johnengine.basic.opengl.renderer.shadows.structs;

import org.joml.Matrix4f;

import johnengine.basic.opengl.renderer.uniforms.IStruct;

public class SCascadedShadow implements IStruct {

    public Matrix4f m4ProjectionMatrix;
    public float fPortionLength;
    
    public SCascadedShadow(Matrix4f m4ProjectionMatrix, float fPortionLength) {
        this.m4ProjectionMatrix = m4ProjectionMatrix;
        this.fPortionLength = fPortionLength;
    }
}
