package johnengine.testing;

import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.JWorld;
import johnengine.basic.game.components.CController;
import johnengine.basic.game.components.CMovement;
import johnengine.basic.game.input.IControllable;
import johnengine.basic.game.physics.CPhysics;
import johnengine.basic.game.physics.IPhysicsObject;
import johnengine.basic.game.physics.PhysicsMaterial;
import johnengine.basic.game.physics.collision.CollisionMesh;
import johnengine.basic.game.physics.collision.shapes.Shape;

public class JTestPlayer extends AWorldObject implements IControllable, IPhysicsObject {

    private CController controller;
    private CPhysics physics;
    private CMovement movement;
    private float movementSpeed;
    private boolean settedup;
    
    public JTestPlayer(JWorld world) {
        super(world);
        this.controller = null;
        this.movementSpeed = 0.05f;
        this.physics = new CPhysics(this);
        this.physics.setStatic(false);
        
        CollisionMesh cmesh = new CollisionMesh(new PhysicsMaterial());
        cmesh.setCollisionShapes(new Shape("sphere"));
        this.physics.getCollision().setCollisionMeshes(cmesh);
        
        this.movement = new CMovement(this);
        this.settedup = false;
    }

    @Override
    public void tick(float deltaTime) {
        if( !this.settedup )
        {
            this.transform.getPosition().setPosition(0, -110, 0);
            this.settedup = true;
        }
        
        this.controller.tick(deltaTime);
        this.movement.tick(deltaTime);
    }

    
    @Override
    public void moveForward(float intensity) {
        this.movement.moveZ(intensity);
    }

    @Override
    public void moveBackward(float intensity) {
        this.movement.moveZ(-intensity);
    }

    @Override
    public void moveLeft(float intensity) {
        this.movement.moveX(-intensity);
    }

    @Override
    public void moveRight(float intensity) {
        this.movement.moveX(intensity);
    }

    @Override
    public void turn(float deltaX, float deltaY) {
        float sens = 1f;
        this.transform.getRotation().rotate(0, deltaX * sens, 0);
        this.transform.getRotation().rotate(deltaY * sens, 0, 0);
    }

    @Override
    public void setController(CController controller) {
        IControllable.super.setController(controller);
        this.controller = controller;
    }

    @Override
    public CPhysics getPhysics() {
        return this.physics;
    }
}
