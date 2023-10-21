package johnengine.core.renderer.unimngr;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL20;

public class UniformManager {

    private final Map<String, AUniform<?>> uniformMap;
    
    public UniformManager() {
        this.uniformMap = new HashMap<String, AUniform<?>>();
    }
    
    
    public UniformManager declareUniform(AUniform<?> uniform) {
        String uniformName = uniform.getName();
        uniform.setLocation(GL20.glGetUniformLocation(5, uniformName));
        this.uniformMap.put(uniformName, uniform);
        
        return this;
    }
    
    public AUniform<?> getUniform(String name) {
        return this.uniformMap.get(name);
    }
}
