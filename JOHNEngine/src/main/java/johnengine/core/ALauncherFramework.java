package johnengine.core;

import java.util.ArrayList;
import java.util.List;

import johnengine.core.engine.Engine;

public abstract class ALauncherFramework {

    private List<IEngineComponent> engineConfiguration;

    private AGame game;

    public ALauncherFramework() {
        this.engineConfiguration = new ArrayList<IEngineComponent>();
        this.configureEngine();
        Engine.run(
            this.game,
            this.engineConfiguration.toArray(new IEngineComponent[this.engineConfiguration.size()])
        );
    }

    protected abstract void configureEngine();

    protected void setupComponent(IEngineComponent component) {
        this.engineConfiguration.add(component);
    }

    protected void setupGame(AGame game) {
        this.game = game;
    }
}
