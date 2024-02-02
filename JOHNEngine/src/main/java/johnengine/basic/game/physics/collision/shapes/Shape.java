package johnengine.basic.game.physics.collision.shapes;

import org.joml.Vector3f;

import johnengine.basic.game.components.geometry.CTransform;
import johnengine.basic.game.physics.collision.CollisionData;

public class Shape {

    private Vector3f offset;
    private int precedence;
    private String shapeName;
    
    public Shape(String shapeName) {
        this.offset = null;
        this.setShape(shapeName);
    }
    
    public Shape() {
        this(null);
    }
    
    
    public boolean checkCollision(
        CTransform myTransform, 
        Vector3f myVelocity, 
        CTransform otherTransform, 
        Shape other,
        CollisionData result
    ) {
        ICollisionCheck checker = CollisionShapes.getChecker(other, this);
        
        if( this.precedence > other.precedence )
        return checker.checkCollision(otherTransform, myVelocity, myTransform, result);
        
        return checker.checkCollision(myTransform, myVelocity, otherTransform, result);
    }
    
    
    public void setShape(String shapeName) {
        this.precedence = CollisionShapes.getShapePrecedence(shapeName);
        this.shapeName = shapeName;
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
    
    public String getShapeName() {
        return this.shapeName;
    }
}
