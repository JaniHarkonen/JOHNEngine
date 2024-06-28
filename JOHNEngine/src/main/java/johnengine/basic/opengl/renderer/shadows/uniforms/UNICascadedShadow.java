package johnengine.basic.opengl.renderer.shadows.uniforms;

import johnengine.basic.opengl.renderer.shadows.structs.SCascadedShadow;
import johnengine.basic.opengl.renderer.uniforms.AUniformObject;
import johnengine.basic.opengl.renderer.uniforms.UNIFloat;
import johnengine.basic.opengl.renderer.uniforms.UNIMatrix4f;

public class UNICascadedShadow extends AUniformObject<SCascadedShadow> {

    private UNIMatrix4f m4ProjectionMatrix;
    private UNIFloat fPortionLength;
    
    public UNICascadedShadow(String name, String identifier) {
        super(
            name, 
            identifier, 
            new UNIMatrix4f("projectionMatrix", "m4ProjectionMatrix"),
            new UNIFloat("portionLength", "fPortionLength")
        );
        
        this.m4ProjectionMatrix = (UNIMatrix4f) this.uniformFields.get("projectionMatrix");
        this.fPortionLength = (UNIFloat) this.uniformFields.get("portionLength");
    }
    
    public UNICascadedShadow() {
        this("", "");
    }

    
    @Override
    public void setValue(SCascadedShadow value) {
        SCascadedShadow struct = (SCascadedShadow) value;
        this.m4ProjectionMatrix.setValue(struct.m4ProjectionMatrix);
        this.fPortionLength.setValue(struct.fPortionLength);
    }
    
    
    public UNIMatrix4f getProjectionMatrix() {
        return this.m4ProjectionMatrix;
    }
    
    public UNIFloat getPortionLength() {
        return this.fPortionLength;
    }
}
