package johnengine.basic.opengl.renderer.shadows;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class CascadeShadow {
    
    public static void updateShadows(
        CascadeShadow[] shadowArray,
        Matrix4f cameraMatrix,
        Matrix4f projectionMatrix,
        Vector3f lightDirection,
        Vector3f up
    ) {
        float portionLambda = 0.95f;
        float[] portions = new float[shadowArray.length];
        
        float near = projectionMatrix.perspectiveNear();
        float far = projectionMatrix.perspectiveFar();
        float range = far - near;
        float ratio = far / near;
        
        int s = portions.length;
        for( int i = 0; i < s; i++ )
        {
            float p = (i + 1) / ((float) s);
            float log = (float) (near * Math.pow(ratio, p));
            float uniform = near + range * p;
            float d = portionLambda * (log - uniform) + uniform;
            portions[i] = (d - near) / range;
        }
        
        float lastPortionLength = 0.0f;
        for( int i = 0; i < s; i++ )
        {
            float portionLength = portions[i];
            Vector3f[] frustumCorners = new Vector3f[] {
                new Vector3f(-1.0f, 1.0f, -1.0f),
                new Vector3f(1.0f, 1.0f, -1.0f),
                new Vector3f(1.0f, -1.0f, -1.0f),
                new Vector3f(-1.0f, -1.0f, -1.0f),
                new Vector3f(-1.0f, 1.0f, 1.0f),
                new Vector3f(1.0f, 1.0f, 1.0f),
                new Vector3f(1.0f, -1.0f, 1.0f),
                new Vector3f(-1.0f, -1.0f, 1.0f)
            };
            
            Vector3f light = (new Vector3f(lightDirection.x, lightDirection.y, lightDirection.z).mul(-1)).normalize();
            Matrix4f inverseCameraViewMatrix = 
                (new Matrix4f(projectionMatrix).mul(cameraMatrix)).invert();
            
            int j;
            for( j = 0; j < 8; j++ )
            {
                Vector4f corner = 
                    new Vector4f(frustumCorners[j], 1.0f).mul(inverseCameraViewMatrix);
                frustumCorners[j] = new Vector3f(
                    corner.x / corner.w, corner.y / corner.w, corner.z / corner.w
                );
            }
            
            for( j = 0; j < 4; j++ )
            {
                Vector3f length = new Vector3f(frustumCorners[j + 4]).sub(frustumCorners[j]);
                frustumCorners[j + 4] = new Vector3f(frustumCorners[j])
                .add(new Vector3f(length).mul(portionLength));
                frustumCorners[j] = new Vector3f(frustumCorners[j])
                .add(new Vector3f(length).mul(lastPortionLength));
            }
            
            Vector3f frustumCenter = new Vector3f(0.0f);
            for( j = 0; j < 8; j++ )
            frustumCenter.add(frustumCorners[j]);
            
            frustumCenter.div(8.0f);
            
            float radius = 0.0f;
            for( j = 0; j < 8; j++ )
            {
                float length = (new Vector3f(frustumCorners[j]).sub(frustumCenter)).length();
                radius = Math.max(radius, length);
            }
            
            radius = (float) Math.ceil(radius * 16.0f) / 16.0f;
            
            Vector3f eye = new Vector3f(frustumCenter).sub(new Vector3f(light).mul(radius));
            Matrix4f lightViewMatrix = new Matrix4f().lookAt(eye, frustumCenter, up);
            Matrix4f lightOrthoMatrix = new Matrix4f()
            .ortho(-radius, radius, -radius, radius, 0.0f, radius + radius, true);
            
            CascadeShadow shadow = shadowArray[i];
            shadow.portionLength = (near + portionLength * range) * -1.0f;
            shadow.projectionMatrix = lightOrthoMatrix.mul(lightViewMatrix);
            
            lastPortionLength = portions[i];
        }
    }
    
    private Matrix4f projectionMatrix;
    private float portionLength;
    
    public CascadeShadow() {
        this.projectionMatrix = new Matrix4f();
        this.portionLength = 0.0f;
    }
    
    
    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }
    
    public float getLength() {
        return this.portionLength;
    }
}
