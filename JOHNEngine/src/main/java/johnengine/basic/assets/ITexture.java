package johnengine.basic.assets;

import johnengine.basic.opengl.renderer.asset.Texture;

public interface ITexture<T> extends IGraphicsAsset<T> {
    public Texture getTexture();
}
