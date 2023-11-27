package main.game.global.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import main.exception.general.ArgumentNullException;

/**
 * This class represents a Storage in the game.
 * It is a generic class that can store items of type T.
 * 
 * The class contains the following fields:
 * - storage: A HashMap that stores the items.
 * 
 * The class provides a constructor that initializes the storage and methods to add, remove, and get items.
 */
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

    /**
     * Gets all items from the storage.
     * @return An ArrayList containing all items in the storage.
     */
    public ArrayList<T> getAllItems(){
        var result = new ArrayList<T>();

        for(var entry : storage.entrySet())
            result.add(entry.getValue());

        return result;
    }

    /**
     * Gets all keys from the storage.
     * @return An ArrayList containing all keys in the storage.
     */
    public ArrayList<String> getAllKeys(){
        var result = new ArrayList<String>();

        for(var entry : storage.entrySet())
            result.add(entry.getKey());

        return result;
    }
}