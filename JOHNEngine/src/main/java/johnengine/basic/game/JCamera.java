package johnengine.basic.game;

import org.joml.Matrix4f;

import johnengine.basic.window.Window;
import johnengine.core.renderer.ARenderer;
import johnengine.core.winframe.AWindowFramework;

public class JCamera extends AWorldObject {

    public static final float DEFAULT_NEAR = 0.001f;
    public static final float DEFAULT_FAR = 1000.0f;
    
    private static final float FOV_Y = (float) Math.toRadians(90.0f);
    
    private int viewWidth;
    private int viewHeight;
    private float near;
    private float far;
    private Matrix4f projectionMatrix;
    private Matrix4f orientationMatrix;
    
    public JCamera(JWorld world) {
        super(world);
        this.near = DEFAULT_NEAR;
        this.far = DEFAULT_FAR;
        this.viewWidth = Window.WindowProperties.DEFAULT_WIDTH;
        this.viewHeight = Window.WindowProperties.DEFAULT_HEIGHT;
        this.projectionMatrix = new Matrix4f();
        this.orientationMatrix = new Matrix4f();
    }

    
    @Override
    public void tick(float deltaTime) {
        
    }
    
    @Override
    public void render(ARenderer renderer) {
        AWindowFramework window = renderer.getWindow();
        int width = window.getWidth();
        int height = window.getHeight();
        
        if( this.viewWidth != width || this.viewHeight != height )
        this.setViewDimensions(width, height);
    }
    
    private void recalculateProjectionMatrix() {
        this.projectionMatrix = (new Matrix4f())
        .setPerspective(FOV_Y, this.viewWidth / this.viewHeight, this.near, this.far);
    }
    
    private void recalculateOrientationMatrix() {
        
    }
    
    
    public void setViewDimensions(int viewWidth, int viewHeight) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.recalculateProjectionMatrix();
    }
    
    public void setNear(float near) {
        this.near = near;
        this.recalculateProjectionMatrix();
    }
    
    public void setFar(float far) {
        this.far = far;
        this.recalculateProjectionMatrix();
    }
    
    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }
    
    public Matrix4f getOrientationMatrix() {
        return this.orientationMatrix;
    }
}
