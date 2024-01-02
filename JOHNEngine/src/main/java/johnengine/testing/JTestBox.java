package johnengine.testing;

import johnengine.basic.game.AWorldObject;
import johnengine.basic.game.JWorld;
import johnengine.basic.game.components.CModel;

public class JTestBox extends AWorldObject {

    public JTestBox(JWorld world, CModel model) {
        super(world);
        //this.getTransform().getScale().setScale(0.05f, 0.05f, 0.05f);
        //this.getTransform().setScale(new Vector3f(0.05f));
        //this.getTransform().setPosition(new Vector3f(10.5f, 10.5f, 10.5f));
        //this.getTransform().setRotation(new Vector3f(10.5f, 10.5f, 10.5f));
        //this.scale.set(new Vector3f(.25f));
    }


    @Override
    public void tick(float deltaTime) {
        //this.position.shift(0.01f, 0.01f, 0.01f);
        /*Vector3f dir = new Vector3f(0.05f);
        this.getTransform().getRightVector(dir);
        this.getTransform().shift(dir.mul(0.01f));*/
        //this.getTransform().rotate(new Vector3f(0.0f, 0.2f, 0.0f));
        //this.getTransform().getRotation().rotate(0.0f, 0.2f, 0.0f);
        //this.getTransform().scale(new Vector3f(0.005f));
    }
}
