package johnengine.basic.assets;

import johnengine.basic.renderer.asset.Mesh;

public interface IMesh<T> extends IGraphicsAsset<T> {
    public Mesh getMesh();
}
