package johnengine.basic.renderer.uniforms;

import java.util.function.Supplier;

import johnengine.basic.renderer.ShaderProgram;

public class UniformArray<T, U extends IUniform<T>> implements IUniform<U[]> {
    
    private U[] array;
    private String name;
    private String identifier;
    
    public UniformArray(String name, String identifier, U[] array) {
        this.name = name;
        this.identifier = identifier;
        this.array = array;
    }
    
    public UniformArray(String nameAndIdentifier, U[] array) {
        this(nameAndIdentifier, nameAndIdentifier, array);
    }

    
    @Override
    public void declare(ShaderProgram shaderProgram) {
        for( int i = 0; i < this.array.length; i++ )
        {
            U uniform = this.array[i];
            uniform.setName(UniformUtils.addArrayIndex(this.name, i) + uniform.getName());
            uniform.setIdentifier(UniformUtils.addArrayIndexToId(this.identifier, i) + uniform.getIdentifier());
            uniform.declare(shaderProgram);
        }
    }
    
    @Override
    public void set() {
        for( U uniform : this.array )
        uniform.set();
    }
    
    public void fill(Supplier<U> uniformGenerator) {
        for( int i = 0; i < this.array.length; i++ )
        this.setArrayIndex(i, uniformGenerator.get());
    }
    
    public void clear() {
        for( int i = 0; i < this.array.length; i++ )
        this.setArrayIndex(i, null);
    }
    
    public void setArrayIndex(int index, U value) {
        this.array[index] = value;
    }
    
    public void setElementValue(int index, T value) {
        this.array[index].set(value);
    }
    
    public U getArrayIndex(int index) {
        return this.array[index];
    }


    @Override
    public void setValue(U[] value) {
        this.array = value;
    }
    
    public U[] getArray() {
        return this.array;
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
