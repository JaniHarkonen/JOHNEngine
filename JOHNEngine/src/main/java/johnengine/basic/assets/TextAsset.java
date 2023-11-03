/*package johnengine.basic.assets;

import java.io.BufferedReader;
import java.io.FileReader;

import johnengine.core.assetmngr.asset.AAsset;
import johnengine.testing.DebugUtils;

public class TextAsset extends AAsset<String> {
    
    public TextAsset(String name, String relativePath, boolean isPersistent, String preloadedAsset) {
        super(name, relativePath, isPersistent, preloadedAsset);
    }
    
    public TextAsset(String name, String relativePath) {
        this(name, relativePath, false, null);
    }

    
    @Override
    protected void loadImpl() {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(this.getPath()));
            this.asset = "";
            
            String line = null;
            while( (line = br.readLine()) != null )
            this.asset += line;
            
            br.close();
        }
        catch( Exception e )
        {
            DebugUtils.log(this, "Error loading text file!");
        }
    }

    @Override
    protected void deloadImpl() {
        
    }
    
    @Override
    protected String getDefault() {
        return "";
    }
}
*/