package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstanceManager {

    private Map<Long, JGameObject> instances;
    private List<JGameObject> additions;
    private List<Long> deletions;
    
    public InstanceManager() {
        this.instances = new HashMap<Long, JGameObject>();
        this.additions = new ArrayList<JGameObject>();
        this.deletions = new ArrayList<Long>();
    }
    
    public void processAdditions() {
        for( JGameObject inst : this.additions )
        this.instances.put(inst.getID(), inst);
        
        this.additions = new ArrayList<JGameObject>();
    }
    
    public void processDeletions() {
        for( Long instanceID : this.deletions )
        this.instances.remove(instanceID);
        
        this.deletions = new ArrayList<Long>();
    }
    
    public void createInstance(JGameObject instance) {
        this.additions.add(instance);
    }
    
    public void destroyInstance(Long instanceID) {
        this.deletions.add(instanceID);
    }
    
    public void destroyInstance(JGameObject instance) {
        this.deletions.add(instance.getID());
    }
}
