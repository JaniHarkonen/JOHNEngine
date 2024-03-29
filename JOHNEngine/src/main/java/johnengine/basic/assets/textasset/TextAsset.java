package johnengine.basic.assets.textasset;

import java.io.BufferedReader;
import java.io.FileReader;

import johnengine.core.assetmngr.asset.AAsset;
import johnengine.core.assetmngr.asset.ALoadTask;
import johnengine.core.exception.JOHNException;

public class TextAsset extends AAsset<String> {
    
    public static class LoadTask extends ALoadTask {
        
        @SuppressWarnings("serial")
        public static class TextFileException extends JOHNException {
            public TextFileException(String message, TextAsset targetAsset, String path) {
                super(message, "%name", targetAsset.getName(), "%path", path);
            }
        }
        
        protected String text;
        protected TextAsset targetAsset;
        
        public LoadTask(String path, TextAsset targetAsset) {
            super(path);
            this.text = "";
            this.targetAsset = targetAsset;
        }
        
        public LoadTask() {
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
                this.text += line + "\n";
                
                br.close();
                this.targetAsset.setAsset(this.text);
            }
            catch( Exception e )
            {
                e.printStackTrace();
                throw new TextFileException(
                    "Failed to load text asset '%name' from path:\n%path",
                    this.targetAsset,
                    this.path
                );
            }
        }
        
        public boolean poll(TextAsset targetAsset) {
            if( !this.isLoaded() )
            return false;
            
            //targetAsset.asset = this.text;
            targetAsset.setAsset(this.text);
            return true;            
        }
        
        
        public void setTarget(TextAsset targetAsset) {
            this.targetAsset = targetAsset;
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
    
    
    /*@Override
    public String getDefault() {
        return "";
    }*/
    
    @Override
    public String getDefaultAsset() {
        return "";
    }
}
