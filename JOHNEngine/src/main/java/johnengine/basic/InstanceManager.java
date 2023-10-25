package johnengine.basic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import johnengine.basic.game.AGameObject;

public class InstanceManager<T extends AGameObject> {
    
    public static final int DEFAULT_FIRST_ID = 1;

    private long nextUniqueID;
    private Map<Long, T> instances;
    private final List<T> additionRequests;
    private final List<Long> deletionRequests;
    private Iterator<Entry<Long, T>> iterator;
    
    public InstanceManager(long nextID) {
        this.nextUniqueID = nextID;
        this.instances = new LinkedHashMap<>();
        this.additionRequests = new ArrayList<>();
        this.deletionRequests = new ArrayList<>();
        
        this.resetIterator();
    }
    
    public InstanceManager() {
        this(DEFAULT_FIRST_ID);
    }
    
    public void processRequests() {
            // Process additions
        while( this.additionRequests.size() > 0 )
        {
            T inst = this.additionRequests.remove(0);
            this.instances.put(inst.getID(), inst);
        }

            // Process deletions
        while( this.deletionRequests.size() > 0 )
        {
            long id = this.deletionRequests.remove(0);
            T inst = this.instances.get(id);
            
            if( inst == null )
            continue;
            
            
            this.instances.remove(id);
        }
    }
    
    public void addInstance(T inst) {
        this.additionRequests.add(inst);
    }
    
    public void deleteInstance(long id) {
        this.deletionRequests.add(id);
    }
    
    public long nextUniqueID() {
        return this.nextUniqueID++;
    }
    
    public long peekID() {
        return this.nextUniqueID;
    }
    
    public void resetIterator() {
        this.iterator = this.instances.entrySet().iterator();
    }
    
    public T nextInstance() {
        if( this.iterator.hasNext() )
        return this.nextInstanceImpl();
        
        return null;
    }
    
    public T nextActiveInstance() {
        if( this.iterator.hasNext() )
        {
            T inst = this.nextInstanceImpl();
            
            if( inst.isActive() && !inst.isDestroyed() )
            return inst;
        }
            
        return null;
    }
    
    private T nextInstanceImpl() {
        return this.iterator.next().getValue();
    }
    
    
    public T getInstance(long id) {
        return this.instances.get(id);
    }
    
    public List<T> getAllInstances() {
        Set<Entry<Long, T>> entrySet = this.instances.entrySet();
        List<T> all = new ArrayList<>(entrySet.size());
        
        for( Entry<Long, T> en : entrySet )
        all.add(en.getValue());
        
        return all;
    }
}
