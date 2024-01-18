package johnengine.basic.game.physics.shapes.plane;

import org.joml.Intersectionf;
import org.joml.Math;
import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.Physics;
import johnengine.basic.game.physics.shapes.ICollisionCheck;

public class CheckPlaneAndSphere implements ICollisionCheck {

    @Override
    public float checkCollision(
        CTransform t1, 
        float velocityX, 
        float velocityY, 
        float velocityZ, 
        CTransform t2
    ) {
            // Update the transform components so that unsafe getters can be used
            // for increased performance
        t1.update();
        t2.update();
        
            // Calculate a normalization scalar to avoid calling JOML's Vector3f.normalize(),
            // this will be used to normalize the velocity vector
        float normalizationScalar = Math.invsqrt(
            Math.fma(
                velocityX, 
                velocityX, 
                Math.fma(velocityY, velocityY, velocityZ * velocityZ)
            )
        );
        
        Vector3f origin = t1.getPosition().getUnsafe();
        float planeNormalX = 0;
        float planeNormalY = 0;
        float planeNormalZ = 0;
        
        return Intersectionf.intersectRayPlane(
            origin.x, 
            origin.y, 
            origin.z, 
            velocityX * normalizationScalar, 
            velocityY * normalizationScalar, 
            velocityZ * normalizationScalar, 
            planeNormalX, 
            planeNormalY, 
            planeNormalZ, 
            d, 
            Physics.INTERSECTION_EPSILON
        );
    }
}
