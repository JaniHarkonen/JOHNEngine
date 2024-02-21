package johnengine.basic.game;

import java.util.ArrayList;
import java.util.List;

import johnengine.basic.game.gui.CText;
import johnengine.core.AGame;
import johnengine.core.IRenderable;
import johnengine.core.renderer.IRenderContext;

public class JGUI extends AGameObject implements IRenderContext {

    private int iteratorIndex;
    private List<CText> DEBUGinstances;
    
    public JGUI(AGame game) {
        super(game);
        this.iteratorIndex = 0;
        this.DEBUGinstances = new ArrayList<>();
    }

    
    @Override
    public void startRenderBuffer() {
        this.iteratorIndex = 0;
    }

    @Override
    public IRenderable nextInstance() {
        if( this.iteratorIndex >= this.DEBUGinstances.size() )
        return null;
        
        return this.DEBUGinstances.get(this.iteratorIndex++);
    }

    @Override
    public void tick(float deltaTime) {
        // TODO Auto-generated method stub
        
    }
    
    public void addElement(CText e) {
        this.DEBUGinstances.add(e);
    }
}
