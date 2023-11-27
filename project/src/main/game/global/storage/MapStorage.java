package main.game.global.storage;

/**
 * This class represents a MapStorage in the game. It stores map name-ID pairs. The key is the name of the map. Only used to load new maps based on user input.
 * It extends the Storage class with String type.
 * 
 * The class contains the following fields:
 * - instance: The singleton instance of the MapStorage class.
 * 
 * The class provides a private constructor and a method to get the singleton instance.
 */
public class MapStorage extends Storage<String>{
    
    private static MapStorage instance;

    private MapStorage(){}

    public static MapStorage getInstance(){
        if(instance == null)
            instance = new MapStorage();
        return instance;
    }
}