package johnengine.basic.assets;

import johnengine.basic.renderer.asset.rewrite.Texture;

public interface ITexture<T> extends IGraphicsAsset<T> {
    public Texture getTexture();
}
