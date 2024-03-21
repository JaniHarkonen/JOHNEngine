package johnengine.basic.assets.mesh;

import johnengine.basic.assets.IGraphicsStrategy;

public interface IMeshGraphics extends IGraphicsStrategy {

    public void setMesh(Mesh mesh);
    
    public Mesh getMesh();
    
    @Override
    public IMeshGraphics duplicateStrategy();
}
