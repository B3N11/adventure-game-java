package game.behaviour.entities.enemy;

import exception.general.ArgumentNullException;
import game.behaviour.abstracts.EnemyBehaviourController;
import game.utility.Identity;

/**
 * This class represents an EnemyType in the game.
 * It extends the Identity class and includes an additional property: controller.
 * 
 * The class contains the following fields:
 * - controller: The behaviour controller of the enemy type.
 * 
 * The class provides a constructor that initializes these fields and methods to get the controller and the enemy entity.
 */
public class EnemyType extends Identity{
    
    private EnemyBehaviourController controller;

    /**
     * Constructor for the EnemyType class.
     * Initializes the id and controller of the enemy type.
     * @param id The id of the enemy type. This is the assetID of the enemy type.
     * @param enemyController The behaviour controller of the enemy type.
     * @throws ArgumentNullException if the enemyController is null.
     */
    public EnemyType(String id, EnemyBehaviourController enemyController) throws ArgumentNullException{
        if(enemyController == null)
            throw new ArgumentNullException();
        
        setID(id);
        controller = enemyController;
    }

    public EnemyBehaviourController getController() { return controller; }
    public EnemyEntity getEntity() { return controller.getEnemyEntity(); }
}
