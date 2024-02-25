package johnengine.basic.assets;

import johnengine.basic.opengl.renderer.asset.Mesh;

public interface IMesh<T> extends IGraphicsAsset<T> {
    public Mesh getMesh();
}
