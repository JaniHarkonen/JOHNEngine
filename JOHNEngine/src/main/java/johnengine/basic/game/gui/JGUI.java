package johnengine.basic.game.gui;

import java.util.ArrayList;
import java.util.List;

import johnengine.basic.game.AGameObject;
import johnengine.basic.game.IWorld;
import johnengine.core.AGame;
import johnengine.core.IRenderable;
import johnengine.core.input.IInput;
import johnengine.core.renderer.IRenderContext;

public class JGUI extends AGameObject implements IWorld, IRenderContext {

    private int iteratorIndex;
    private List<JFrame> frames;
    private IInput inputSource;
    
    public JGUI(AGame game, IInput inputSource) {
        super(game);
        this.iteratorIndex = 0;
        this.frames = new ArrayList<>();
        this.inputSource = inputSource;
    }

    
    @Override
    public void startRenderBuffer() {
        this.iteratorIndex = 0;
    }

    @Override
    public IRenderable nextInstance() {
        if( this.iteratorIndex >= this.frames.size() )
        return null;
        
        return this.frames.get(this.iteratorIndex++);
    }

    @Override
    public void tick(float deltaTime) {
        for( JFrame frame : this.frames )
        frame.tick(deltaTime);
    }
    
    public void addFrame(JFrame frame) {
        this.frames.add(frame);
    }
    
    public List<JFrame> getFrames() {
        return this.frames;
    }
    
    public IInput getInputSource() {
        return this.inputSource;
    }
}
