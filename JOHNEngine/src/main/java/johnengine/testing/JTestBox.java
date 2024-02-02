package johnengine.testing;

import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.JWorld;
import johnengine.basic.game.components.CModel;
import johnengine.basic.game.physics.CPhysics;
import johnengine.basic.game.physics.IPhysicsObject;
import johnengine.basic.game.physics.PhysicsMaterial;
import johnengine.basic.game.physics.collision.CollisionMesh;
import johnengine.basic.game.physics.collision.shapes.Shape;

public class JTestBox extends AWorldObject implements IPhysicsObject {
    private CPhysics physics;
    private boolean settedup = false;

    public JTestBox(JWorld world, CModel model) {
        super(world);
        this.physics = new CPhysics(this);
        
        PhysicsMaterial physicsMat = new PhysicsMaterial(0.0f, 10.0f, 1.0f);
        CollisionMesh cmesh = new CollisionMesh(physicsMat);
        cmesh.setCollisionShapes(new Shape("box"));
        this.physics.getCollision().setCollisionMeshes(cmesh);
        this.physics.getCollision().setLodShape(new Shape("box"));
        
        //this.getTransform().getPosition().setPosition(0, 0, 10);
        //this.getTransform().getScale().setScale(0.15f, 0.15f, 0.15f);
        //this.getTransform().setScale(new Vector3f(0.05f));
        //this.getTransform().setPosition(new Vector3f(10.5f, 10.5f, 10.5f));
        //this.getTransform().setRotation(new Vector3f(10.5f, 10.5f, 10.5f));
        //this.scale.set(new Vector3f(.25f));
    }


    @Override   
    public void tick(float deltaTime) {
        if( !this.settedup )
        {
            this.transform.getScale().setScale(100, 1, 100);
            this.settedup = true;
        }
        //this.position.shift(0.01f, 0.01f, 0.01f);
        /*Vector3f dir = new Vector3f(0.05f);
        this.getTransform().getRightVector(dir);
        this.getTransform().shift(dir.mul(0.01f));*/
        //this.getTransform().rotate(new Vector3f(0.0f, 0.2f, 0.0f));
        //this.getTransform().getRotation().rotate(0.0f, 0.2f, 0.0f);
        //this.getTransform().scale(new Vector3f(0.005f));
    }


    @Override
    public CPhysics getPhysics() {
        return this.physics;
    }
}
