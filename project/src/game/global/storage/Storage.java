package game.global.storage;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import exception.general.ArgumentNullException;

public class Storage<T> {
    
    protected HashMap<String, T> storage;

    public Storage(){
        storage = new HashMap<String, T>();
    }

    public void add(String id, T item) throws ArgumentNullException{
        if(id == null || item == null)
            throw new ArgumentNullException();

        storage.put(id, item);
    }

    public void remove(String id) throws ArgumentNullException{
        if(id == null)
            throw new ArgumentNullException();

        storage.remove(id);
    }

    public T get(String id) throws ArgumentNullException{
        if(id == null)
         throw new ArgumentNullException();

        return storage.get(id);
    }

    public boolean contains(String id) throws ArgumentNullException{
        if(id == null)
            throw new ArgumentNullException();
        
        return storage.containsKey(id);
    }

    public void clear(){
        storage.clear();
    }

    public Set<Entry<String, T>> entrySet(){
        return storage.entrySet();
    }
}