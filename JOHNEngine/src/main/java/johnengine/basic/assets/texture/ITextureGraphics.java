package johnengine.basic.assets.texture;

import johnengine.basic.assets.IGraphicsStrategy;

public interface ITextureGraphics extends IGraphicsStrategy {

    public void setTexture(Texture texture);
    
    public Texture getTexture();
    
    @Override
    public ITextureGraphics duplicateStrategy();
}
