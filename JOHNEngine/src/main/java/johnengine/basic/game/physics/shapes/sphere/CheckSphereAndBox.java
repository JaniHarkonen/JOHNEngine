package johnengine.basic.game.physics.shapes.sphere;

import org.joml.Intersectionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.CollisionData;
import johnengine.basic.game.physics.Physics;
import johnengine.basic.game.physics.shapes.ICollisionCheck;

public class CheckSphereAndBox implements ICollisionCheck {

    @Override
    public CollisionData checkCollision(
        CTransform t1, 
        float velocityX, 
        float velocityY, 
        float velocityZ, 
        CTransform t2
    ) {
            // Update the transform components so that unsafe getters
            // can be used for better performance
        t1.update();
        t2.update();
        
        Vector3f origin = t1.getPosition().getUnsafe();
        Vector3f otherOrigin = t2.getPosition().getUnsafe();
        Vector3f otherScale = t2.getScale().getUnsafe();
        
        float otherX = otherOrigin.x;
        float otherY = otherOrigin.y;
        float otherZ = otherOrigin.z;
        float otherHalfScaleX = otherScale.x / 2;
        float otherHalfScaleY = otherScale.y / 2;
        float otherHalfScaleZ = otherScale.z / 2;
        float normalizationScalar = Physics.calculateNormalizationScalar(
            velocityX, 
            velocityY, 
            velocityZ
        );
        
        Vector2f nearFar = new Vector2f();
        if( 
            !Intersectionf.intersectRayAab(
                origin.x, 
                origin.y, 
                origin.z, 
                velocityX * normalizationScalar, 
                velocityY * normalizationScalar, 
                velocityZ * normalizationScalar, 
                otherX - otherHalfScaleX, 
                otherY - otherHalfScaleY, 
                otherZ - otherHalfScaleZ, 
                otherX + otherHalfScaleX, 
                otherY + otherHalfScaleY, 
                otherZ + otherHalfScaleZ, 
                nearFar
            )
        )
        return null;
        
        CollisionData result = new CollisionData();
        result.collisionDistanceNear = nearFar.x;
        result.collisionDistanceFar = nearFar.y;
        return result;
    }
}
