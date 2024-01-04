package johnengine.core.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimedCache<K, I> {
    
    final class CachedItem {
        private I item;
        private long lastUsedTime;
        
        public CachedItem(I item) {
            this.item = item;
            this.lastUsedTime = System.currentTimeMillis();
        }
        
        
        public long getLastUsedTime() {
            return this.lastUsedTime;
        }
        
        public boolean hasExpired(long currentTime, long expirationTime) {
            return (currentTime - this.lastUsedTime > expirationTime);
        }
        
        public I getItem() {
            this.lastUsedTime = System.currentTimeMillis();
            return this.item;
        }
    }

    private final Map<K, CachedItem> cacheMap;
    private long expirationTime;
    
    public TimedCache(long expirationTime) {
        this.cacheMap = new HashMap<>();
        this.expirationTime = expirationTime;
    }

    
    public void update() {
        long currentTime = System.currentTimeMillis();
        List<K> expiredKeys = new ArrayList<>();
        for( Map.Entry<K, CachedItem> en : this.cacheMap.entrySet() )
        {
            CachedItem cachedItem = en.getValue();
            
            if( cachedItem.hasExpired(currentTime, this.expirationTime) )
            expiredKeys.add(en.getKey());
        }
        
            // Remove cached VAOs marked for deletion
        for( K key : expiredKeys )
        this.cacheMap.remove(key);
    }
    
    
    public void cacheItem(K key, I item) {
        this.cacheMap.put(key, new CachedItem(item));
    }
    
    public I get(K key) {
        CachedItem cachedItem = this.cacheMap.get(key);
        
        if( cachedItem == null )
        return null;
        
        return cachedItem.getItem();
    }
}
