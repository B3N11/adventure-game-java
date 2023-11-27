package main.game.global.storage;

import main.game.behaviour.entities.enemy.Enemy;

/**
 * This class represents an ActiveEnemyStorage in the game. It stores all the active enemies on the current map. The key is the ID of the enemy.
 * It extends the Storage class with Enemy type.
 * 
 * The class contains the following fields:
 * - instance: The singleton instance of the ActiveEnemyStorage class.
 * 
 * The class provides a private constructor and a method to get the singleton instance.
 */
public class ActiveEnemyStorage extends Storage<Enemy>{
    
    private static ActiveEnemyStorage instance;

    private ActiveEnemyStorage(){}

    public static ActiveEnemyStorage getInstance(){
        if(instance == null)
            instance = new ActiveEnemyStorage();
        return instance;
    }
}