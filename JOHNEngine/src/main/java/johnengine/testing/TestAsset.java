package johnengine.testing;

import java.io.BufferedReader;
import java.io.FileReader;

import johnengine.core.assetmngr.asset.AAsset;

public class TestAsset extends AAsset {
    
    protected TestAsset(String name, String path) {
        super(name, path);
        this.asset = this.getDefault();
    }

    @Override
    protected void loadImpl() {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(this.path));
            String line;
            while( (line = br.readLine()) != null )
            this.asset += line;
            
            br.close();
        }
        catch( Exception e )
        {
            DebugUtils.log(this, "lol failure xdDDDD");
        }
    }

    @Override
    protected void deloadImpl() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    protected Object getDefault() {
        return "";
    }
}
