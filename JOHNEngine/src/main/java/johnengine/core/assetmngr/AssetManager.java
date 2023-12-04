package johnengine.core.assetmngr;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import johnengine.core.IEngineComponent;
import johnengine.core.assetmngr.asset.AAssetLoader;
import johnengine.core.assetmngr.asset.AssetGroup;
import johnengine.core.assetmngr.asset.IAsset;
import johnengine.core.threadable.AThreadable;
import johnengine.testing.DebugUtils;

public final class AssetManager implements IEngineComponent {
    
    private static class LoaderProcess extends AThreadable {
        public static final long DEFAULT_SLEEP_TIME = 500;
        
        private Queue<AAssetLoader> queuedLoaders;
        private boolean isRunning;
        
        LoaderProcess() {
            this.queuedLoaders = new ConcurrentLinkedQueue<>();
            this.isRunning = false;
        }


        @Override
        public void loop() {
            this.isRunning = true;
            
            while( this.isRunning )
            {
                AAssetLoader loader = this.queuedLoaders.poll();
                
                if( loader != null )
                loader.load();
                else
                {
                    try 
                    {
                        Thread.sleep(DEFAULT_SLEEP_TIME);
                    }
                    catch (InterruptedException e) 
                    {
                        e.printStackTrace();
                    }
                }
            }
        }


        @Override
        public void stop() {
            this.isRunning = false;
        }
        
        public void addTask(AAssetLoader loader) {
            this.queuedLoaders.add(loader);
        }
    }
    
    
    /************************* AssetManager-class *************************/

    public static final int DEFAULT_NUMBER_OF_THREADS = 4;
    
    public static AssetManager setup(int numberOfThreads) {
        AssetManager assetManager = new AssetManager(numberOfThreads);
        
            // Start up threads that will be used to process the incoming load requests
        for( int i = 0; i < numberOfThreads; i++ )
        {
            LoaderProcess process = new LoaderProcess();
            assetManager.loaderProcesses[i] = process;
            process.start();
        }
        
        return assetManager;
    }
    
    public static AssetManager setup() {
        return setup(DEFAULT_NUMBER_OF_THREADS);
    }
    
    private Map<String, IAsset> assets;
    private LoaderProcess[] loaderProcesses;
    private int nextThreadIndex;
    private int numberOfThreads;
    
    public AssetManager(int numberOfThreads) {
        this.assets = new HashMap<>();
        this.loaderProcesses = new LoaderProcess[numberOfThreads];
        this.nextThreadIndex = 0;
        this.numberOfThreads = numberOfThreads;
    }
    
    
    public AssetManager declareAsset(IAsset asset) {
        String assetName = asset.getName();
        
        if( asset != null )
        this.assets.put(assetName, asset);
        
        return this;
    }
    
    public AssetManager delistAsset(String assetName) {
        this.assets.remove(assetName);
        return this;
    }
    
    public AssetGroup createAssetGroup(String groupName) {
        return new AssetGroup(groupName, this);
    }
    
    public AssetManager load(AAssetLoader loader) {
        this.getNextLoaderProcess().addTask(loader);
        return this;
    }
    
    public AssetManager loadFrom(String path, AAssetLoader loader) {
        if( loader == null )
        DebugUtils.log(this, "FAIL: null loader");
        else
        loader.setPath(path);
        
        return this.load(loader);
    }
    
    public AssetManager deloadAsset(String assetName) {
        IAsset asset = this.assets.get(assetName);
        
        if( asset != null )
        asset.deload();
        
        return this;
    }
    
    public AssetManager deloadAndDelist(String assetName) {
        this.deloadAsset(assetName);
        this.delistAsset(assetName);
        return this;
    }
    
    @Override
    public void beforeTick(float deltaTime) {
        
    }

    @Override
    public void afterTick(float deltaTime) {
        
    }
    
    public void stop() {
        for( LoaderProcess process : this.loaderProcesses )
        process.stop();
        
            // IMPORTANT!!!: Add the deloading of assets
        
        this.loaderProcesses = null;
    }

    private LoaderProcess getNextLoaderProcess() {
        if( this.nextThreadIndex >= numberOfThreads )
        this.nextThreadIndex = 0;
        
        return this.loaderProcesses[this.nextThreadIndex++];
    }
    
    public IAsset getAsset(String assetName) {
        return this.assets.get(assetName);
    }
}
