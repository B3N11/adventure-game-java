package main.game.global.storage;

import main.game.behaviour.entities.enemy.EnemyType;

/**
 * This class represents an EnemyTypeStorage in the game. It stores all the enemy types in the game so that they can be reused. The key is the ID of the enemy type.
 * It extends the Storage class with EnemyType.
 * 
 * The class contains the following fields:
 * - instance: The singleton instance of the EnemyTypeStorage class.
 * 
 * The class provides a private constructor and a method to get the singleton instance.
 */
public class EnemyTypeStorage extends Storage<EnemyType>{
    
    private static EnemyTypeStorage instance;

    private EnemyTypeStorage(){}

    public static EnemyTypeStorage getInstance(){
        if(instance == null)
            instance = new EnemyTypeStorage();
        return instance;
    }
}