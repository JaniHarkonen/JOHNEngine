package johnengine.core.renderer;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Renderer {
    
    public float r = 1.0f;
    public float g = 0.0f;
    public float b = 0.0f;
    public float a = 1.0f;

    public void initialize() {
        GL.createCapabilities();
    }
    
    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(this.r, this.g, this.b, this.a);
        GL11.glViewport(0, 0, 640, 480);
    }
}