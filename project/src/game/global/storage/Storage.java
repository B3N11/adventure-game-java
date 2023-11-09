package game.global.storage;

import java.util.HashMap;

import exception.general.ArgumentNullException;
import exception.general.ElementAlreadyInCollectionException;

public class Storage<T> {
    
    protected HashMap<String, T> storage;

    public Storage(){
        storage = new HashMap<String, T>();
    }

    public void add(String id, T item) throws ElementAlreadyInCollectionException, ArgumentNullException{
        if(id == null || item == null)
            throw new ArgumentNullException();

        if(storage.containsKey(id) || storage.containsValue(item))
            throw new ElementAlreadyInCollectionException();

        storage.put(id, item);
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

    public boolean contains(T value) throws ArgumentNullException{
        if(value == null)
            throw new ArgumentNullException();

        return storage.containsValue(value);
    }
}