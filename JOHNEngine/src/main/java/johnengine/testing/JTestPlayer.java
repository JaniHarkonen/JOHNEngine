package johnengine.testing;

import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.JWorld;
import johnengine.basic.game.components.CController;
import johnengine.basic.game.components.CMovement;
import johnengine.basic.game.input.AControllerAction;
import johnengine.basic.game.input.IControllable;
import johnengine.basic.game.input.actions.ACTMoveBackward;
import johnengine.basic.game.input.actions.ACTMoveForward;
import johnengine.basic.game.input.actions.ACTMoveLeft;
import johnengine.basic.game.input.actions.ACTMoveRight;
import johnengine.basic.game.input.actions.ACTTurn;
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
            this.transform.getPosition().setPosition(0, 0, 0);
            //this.transform.getRotation().setRotation(70, 47, 0);
            this.settedup = true;
        }
        
        this.controller.tick(deltaTime);
        this.movement.tick(deltaTime);
    }

    @Override
    public void control(AControllerAction action) {
        switch( action.action )
        {
            case MOVE_FORWARD: {
                this.movement.moveZ(((ACTMoveForward) action).intensity);
            } break;
            
            case MOVE_BACKWARD: {
                this.movement.moveZ(-((ACTMoveBackward) action).intensity);
            } break;
            
            case MOVE_LEFT: {
                this.movement.moveX(-((ACTMoveLeft) action).intensity);
            } break;
            
            case MOVE_RIGHT: {
                this.movement.moveX(((ACTMoveRight) action).intensity);
            } break;
            
            case TURN: {
                ACTTurn actionTurn = (ACTTurn) action;
                float sens = 0.5f;
                this.transform.getRotation().rotate(0, actionTurn.deltaX * sens, 0);
                this.transform.getRotation().rotate(actionTurn.deltaY * sens, 0, 0);
            } break;
        }
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
