package johnengine.core;

import java.util.ArrayList;
import java.util.List;

import johnengine.core.engine.Engine;

public abstract class ALauncherFramework {

    protected final List<IEngineComponent> engineConfiguration;
    protected AGame game;

    public ALauncherFramework() {
        this.engineConfiguration = new ArrayList<>();
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
