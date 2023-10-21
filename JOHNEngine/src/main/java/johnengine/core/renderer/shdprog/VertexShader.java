package johnengine.core.renderer.shdprog;

import org.lwjgl.opengl.GL30;

public class VertexShader extends AShader {

    public VertexShader(String name, String relativePath) {
        super(name, relativePath);
    }

    @Override
    public int getType() {
        return GL30.GL_VERTEX_SHADER;
    }
}
