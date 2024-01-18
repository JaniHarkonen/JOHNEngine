package johnengine.basic.game.physics.shapes;

import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.CollisionData;

public class Shape {

    private Vector3f offset;
    private int precedence;
    
    public Shape(String shapeName) {
        this.offset = null;
        this.setShape(shapeName);
    }
    
    public Shape() {
        this(null);
    }
    
    
    public CollisionData checkCollision(CTransform myTransform, Vector3f myVelocity, CTransform otherTransform, Shape other) {
        ICollisionCheck checker = CollisionShapes.getChecker(other, this);
        
        if( this.precedence > other.precedence )
        return checker.checkCollision(otherTransform, myVelocity, myTransform);
        
        return checker.checkCollision(myTransform, myVelocity, otherTransform);
    }
    
    
    public void setShape(String shapeName) {
        this.precedence = CollisionShapes.getShapePrecedence(shapeName);
    }
    
    public void setOffset(Vector3f offset) {
        this.offset = offset;
    }
    
    
    public int getPrecedence() {
        return this.precedence;
    }
    
    public Vector3f getOffset() {
        return this.offset;
    }
}
