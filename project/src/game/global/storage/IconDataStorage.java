package game.global.storage;

import file.elements.IconData;

/**
 * This class represents an IconDataStorage in the game. It stores all the icons for the game elements. The key is the ID of the element.
 * It extends the Storage class with IconData type.
 * 
 * The class contains the following fields:
 * - instance: The singleton instance of the IconDataStorage class.
 * 
 * The class provides a private constructor and a method to get the singleton instance.
 */
public class IconDataStorage extends Storage<IconData>{
    
    private static IconDataStorage instance;

    private IconDataStorage(){}

    public static IconDataStorage getInstance(){
        if(instance == null)
            instance = new IconDataStorage();
        return instance;
    }
}
