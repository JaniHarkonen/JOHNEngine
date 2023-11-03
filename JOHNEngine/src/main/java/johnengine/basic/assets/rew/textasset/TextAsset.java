package johnengine.basic.assets.rew.textasset;

import java.io.BufferedReader;
import java.io.FileReader;

import johnengine.core.assetmngr.asset.rew.asset.AAsset;
import johnengine.core.assetmngr.asset.rew.asset.AAssetLoader;
import johnengine.testing.DebugUtils;

public class TextAsset extends AAsset<String, TextAsset.Loader> {

    public static class Loader extends AAssetLoader {
        protected String text;
        protected TextAsset targetAsset;
        
        public Loader(String path, TextAsset targetAsset) {
            super(path);
            this.text = "";
            this.targetAsset = targetAsset;
        }
        
        public Loader() {
            this(null, null);
        }
        
        
        @Override
        protected void loadImpl() {
            try
            {
                BufferedReader br = new BufferedReader(new FileReader(this.getPath()));
                this.text = "";
                
                String line = null;
                while( (line = br.readLine()) != null )
                this.text += line;
                
                br.close();
                this.targetAsset.asset = this.text;
            }
            catch( Exception e )
            {
                DebugUtils.log(this, "Error loading text file!");
            }
        }
        
        public boolean poll(TextAsset targetAsset) {
            if( !this.isLoaded() )
            return false;
            
            targetAsset.asset = this.text;
            return true;            
        }
    }
    
    /************************ TextAsset-class ************************/
    
    public TextAsset(String name, boolean isPersistent, String preloadedAsset) {
        super(name, isPersistent, preloadedAsset);
    }
    
    public TextAsset(String name) {
        this(name, false, null);
    }

    @Override
    protected void deloadImpl() {
        
    }
    
    
    @Override
    public String getDefault() {
        return "";
    }
}
