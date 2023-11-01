package johnengine.basic.renderer.asset;

import org.lwjgl.assimp.AIMaterial;

import johnengine.basic.renderer.ARendererAsset;

public class Material extends ARendererAsset<AIMaterial> {

    public Material(String name) {
        super(name);
    }

    
    @Override
    protected void loadImpl() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void deloadImpl() {
        // TODO Auto-generated method stub
        
    }
}
