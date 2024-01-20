package johnengine.basic.game.physics.collision.shapes.sphere;

import org.joml.Intersectionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.Physics;
import johnengine.basic.game.physics.collision.CollisionData;
import johnengine.basic.game.physics.collision.shapes.ICollisionCheck;

public class CheckSphereAndBox implements ICollisionCheck {

    @Override
    public boolean checkCollision(
        CTransform t1, 
        float velocityX, 
        float velocityY, 
        float velocityZ, 
        CTransform t2,
        CollisionData result
    ) {
            // Update the transform components so that unsafe getters
            // can be used for better performance
        t1.update();
        t2.update();
        
        Vector3f originT1 = t1.getPosition().getUnsafe();
        Vector3f originT2 = t2.getPosition().getUnsafe();
        Vector3f scaleT2 = t2.getScale().getUnsafe();
        
        float xT2 = originT2.x;
        float yT2 = originT2.y;
        float zT2 = originT2.z;
        float halfScaleXT2 = scaleT2.x / 2;
        float halfScaleYT2 = scaleT2.y / 2;
        float halfScaleZT2 = scaleT2.z / 2;
        float normalizationScalar = Physics.calculateNormalizationScalar(
            velocityX, 
            velocityY, 
            velocityZ
        );
        
            // Collision check
        Vector2f nearFar = new Vector2f();
        if( 
            !Intersectionf.intersectRayAab(
                originT1.x, 
                originT1.y, 
                originT1.z, 
                velocityX * normalizationScalar, 
                velocityY * normalizationScalar, 
                velocityZ * normalizationScalar, 
                xT2 - halfScaleXT2, 
                yT2 - halfScaleYT2, 
                zT2 - halfScaleZT2, 
                xT2 + halfScaleXT2, 
                yT2 + halfScaleYT2, 
                zT2 + halfScaleZT2, 
                nearFar
            )
        )
        return false;
        
        result.collisionDistanceNear = nearFar.x;
        result.collisionDistanceFar = nearFar.y;
        return true;
    }
}
