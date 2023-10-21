package johnengine.core.renderer.shdprog;

import org.lwjgl.opengl.GL30;

public class FragmentShader extends AShader {

    public FragmentShader(String name, String relativePath) {
        super(name, relativePath);
    }

    @Override
    public int getType() {
        return GL30.GL_FRAGMENT_SHADER;
    }
}
