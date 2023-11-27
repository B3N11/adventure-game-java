package main.game.behaviour.entities.enemy;

import main.exception.general.ArgumentNullException;
import main.exception.general.InvalidArgumentException;
import main.game.behaviour.entities.InventoryEntity;
import main.game.enums.EntityType;

/**
 * This class represents an EnemyEntity in the game.
 * It extends the InventoryEntity class and includes an additional property: rewardXP.
 * 
 * The class contains the following fields:
 * - rewardXP: The experience points rewarded when the enemy is defeated.
 * 
 * The class provides a constructor that initializes these fields and methods to set and get the rewardXP.
 */
public class EnemyEntity extends InventoryEntity {
    
    private int rewardXP;

    /**
     * Constructor for the EnemyEntity class.
     * Initializes the health, movement, level, and rewardXP of the entity.
     * @param health The health of the entity.
     * @param movement The movement speed of the entity.
     * @param level The level of the entity.
     * @throws InvalidArgumentException if the health, movement, or level is invalid.
     * @throws ArgumentNullException if the inventory is null.
     */
    public EnemyEntity(int health, int movement, int level) throws InvalidArgumentException, ArgumentNullException{
        super(health, movement, level, true);

        entityType = EntityType.ENEMY;
    }

    /**
     * Sets the experience points rewarded when the enemy is defeated.
     * @param xp The experience points to set.
     * @return This EnemyEntity, to allow for chaining.
     * @throws InvalidArgumentException if the xp is less than 0.
     */
    public EnemyEntity setRewardXP(int xp) throws InvalidArgumentException{
        if(xp < 0)
            throw new InvalidArgumentException();

        rewardXP = xp;
        return this;
    }

    public int getRewardXP() { return rewardXP; }
}