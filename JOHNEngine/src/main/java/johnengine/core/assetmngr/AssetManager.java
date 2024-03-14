package johnengine.core.assetmngr;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import johnengine.core.FileUtils;
import johnengine.core.IEngineComponent;
import johnengine.core.assetmngr.asset.ALoadTask;
import johnengine.core.assetmngr.asset.AssetGroup;
import johnengine.core.assetmngr.asset.IAsset;
import johnengine.core.exception.JOHNException;
import johnengine.core.threadable.AThreadable;

public final class AssetManager implements IEngineComponent {
    
    /************************* NullTaskException-class *************************/
    
    @SuppressWarnings("serial")
    public static class NullTaskException extends JOHNException {
        public NullTaskException() {
            super("Trying to schedule a NULL loading task!");
        }
    }
    
    /************************* NullTaskException-class *************************/
    
    @SuppressWarnings("serial")
    public static class NonExistingRootDirectoryException extends JOHNException {
        public NonExistingRootDirectoryException(String directory) {
            super(
                "Trying to set a non-existing root directory!\nDirectory:\n%directory", 
                "%directory", 
                directory
            );
        }
    }
    
    
    /************************* LoaderProcess-class *************************/
    
    private static class LoaderProcess extends AThreadable {
        public static final long DEFAULT_SLEEP_TIME = 500;
        
        private Queue<ALoadTask> queuedTasks;
        private boolean isRunning;
        
        LoaderProcess() {
            this.queuedTasks = new ConcurrentLinkedQueue<>();
            this.isRunning = false;
        }


        @Override
        public void loop() {
            this.isRunning = true;
            
            while( this.isRunning )
            {
                ALoadTask loadTask = this.queuedTasks.poll();
                
                if( loadTask != null )
                loadTask.load();
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
        
        public void addTask(ALoadTask loadTask) {
            this.queuedTasks.add(loadTask);
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
    
    
    /************************* Class body *************************/
    
    private Map<String, IAsset> assets;
    private LoaderProcess[] loaderProcesses;
    private int nextThreadIndex;
    private int numberOfThreads;
    private String rootDirectory;
    
    public AssetManager(int numberOfThreads) {
        this.assets = new HashMap<>();
        this.loaderProcesses = new LoaderProcess[numberOfThreads];
        this.nextThreadIndex = 0;
        this.numberOfThreads = numberOfThreads;
        this.rootDirectory = "";
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
    
    public AssetManager schedule(ALoadTask loadTask) {
        this.getNextLoaderProcess().addTask(loadTask);
        return this;
    }
    
    public AssetManager scheduleFrom(String path, ALoadTask loadTask) {
        if( loadTask == null )
        throw new NullTaskException();
        else
        loadTask.setPath(this.rootDirectory + FileUtils.normalizePathSlashes(path));
        
        return this.schedule(loadTask);
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
        this.nextThreadIndex %= this.numberOfThreads;
        return this.loaderProcesses[this.nextThreadIndex++];
    }
    
    public void setRootDirectory(String rootDirectory) {
        String normalizedRootDirectory = FileUtils.normalizePathSlashes(rootDirectory);
        
        if( Files.exists(Paths.get(normalizedRootDirectory)) )
        this.rootDirectory = normalizedRootDirectory + "/";
        else
        throw new NonExistingRootDirectoryException(normalizedRootDirectory);
    }
    
    public IAsset getAsset(String assetName) {
        return this.assets.get(assetName);
    }
}
