package game.global.storage;

import game.utility.ModifiedEnemyData;

/**
 * This class represents a ModifiedEnemyStorage in the game. It stores the currently acitve enemies that have been modified by the player. The key is the ID of the enemy.
 * It extends the Storage class with ModifiedEnemyData type.
 * 
 * The class contains the following fields:
 * - instance: The singleton instance of the ModifiedEnemyStorage class.
 * 
 * The class provides a private constructor and a method to get the singleton instance.
 */
public class ModifiedEnemyStorage extends Storage<ModifiedEnemyData>{
    
    private static ModifiedEnemyStorage instance;

    private ModifiedEnemyStorage(){}

    public static ModifiedEnemyStorage getInstance(){
        if(instance == null)
            instance = new ModifiedEnemyStorage();
        return instance;
    }
}
