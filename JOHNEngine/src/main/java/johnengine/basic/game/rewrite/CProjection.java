package johnengine.basic.game.rewrite;

import org.joml.Matrix4f;

public class CProjection {

    public static final float DEFAULT_FOV_Y = (float) Math.toRadians(90.0f);
    public static final int DEFAULT_VIEW_WIDTH = 640;
    public static final int DEFAULT_VIEW_HEIGHT = 480;
    public static final float DEFAULT_Z_NEAR = 0.001f;
    public static final float DEFAULT_Z_FAR = 10000.0f;
    
    private Matrix4f projectionMatrix;
    private float fovY;
    private int viewWidth;
    private int viewHeight;
    private float zNear;
    private float zFar;
    
    public CProjection(float fovY, int viewWidth, int viewHeight, float zNear, float zFar) {
        this.projectionMatrix = new Matrix4f();
        this.fovY = fovY;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.zNear = zNear;
        this.zFar = zFar;
        
        this.calculate();
    }
    
    public CProjection(CProjection projection) {
        this(
            projection.fovY, 
            projection.viewWidth, 
            projection.viewHeight, 
            projection.zNear, 
            projection.zFar
        );
    }
    
    public CProjection() {
        this(
            DEFAULT_FOV_Y, 
            DEFAULT_VIEW_WIDTH,
            DEFAULT_VIEW_HEIGHT, 
            DEFAULT_Z_NEAR, 
            DEFAULT_Z_FAR
        );
    }
    
    
    public Matrix4f getMatrix() {
        return this.projectionMatrix;
    }
    
    public Matrix4f getCopy() {
        return new Matrix4f(this.projectionMatrix);
    }
    
    public CProjection copyInstance() {
        return new CProjection(this);
    }
    
    public void calculate() {
        this.projectionMatrix.setPerspective(
            this.fovY, 
            this.viewWidth / this.viewHeight, 
            this.zNear, 
            this.zFar
        );
    }
    
    
    public void setFovY(float fovY) {
        this.fovY = fovY;
    }

    public void setViewDimensions(int viewWidth, int viewHeight) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
    }
    
    public void setZCulling(float zNear, float zFar) {
        this.zNear = zNear;
        this.zFar = zFar;
    }
    
    
    public float getFovY() {
        return this.fovY;
    }
    
    public int getViewWidth() {
        return this.viewWidth;
    }
    
    public int getViewHeight() {
        return this.viewHeight;
    }
    
    public float getZNear() {
        return this.zNear;
    }
    
    public float getZFar() {
        return this.zFar;
    }
}
