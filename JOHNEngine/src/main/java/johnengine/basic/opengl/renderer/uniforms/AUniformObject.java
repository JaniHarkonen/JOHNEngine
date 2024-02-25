package johnengine.basic.opengl.renderer.uniforms;

import java.util.HashMap;
import java.util.Map;

import johnengine.basic.opengl.renderer.ShaderProgram;

public abstract class AUniformObject<T extends IStruct> implements IUniform<T> {

    protected String name;
    protected String identifier;
    protected Map<String, IUniform<?>> uniformFields;
    
    public AUniformObject(String name, String identifier, IUniform<?>... uniforms) {
        this.name = name;
        this.identifier = identifier;
        this.uniformFields = new HashMap<>();
        
        for( IUniform<?> uniform : uniforms )
        this.uniformFields.put(uniform.getName(), uniform);
    }
    
    
    @Override
    public void declare(ShaderProgram shaderProgram) {
        this.updateFieldIdentifiers();
        
        for( Map.Entry<String, IUniform<?>> en : this.uniformFields.entrySet() )
        en.getValue().declare(shaderProgram);
    }
    
    protected void updateFieldIdentifiers() {
        for( Map.Entry<String, IUniform<?>> en : this.uniformFields.entrySet() )
        {
            IUniform<?> uniform = en.getValue();
            uniform.setIdentifier(UniformUtils.addFieldNameToId(this.identifier, uniform.getIdentifier()));
        }
    }

    @Override
    public void set() {
        for( Map.Entry<String, IUniform<?>> en : this.uniformFields.entrySet() )
        en.getValue().set();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
