package game.behaviour.entities.player;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.entities.InventoryEntity;
import game.enums.EntityType;

/**
 * This class represents a PlayerEntity in the game.
 * It extends the InventoryEntity class and includes an additional property: entityType.
 * 
 * The class contains the following fields:
 * - entityType: The type of the entity, which is PLAYER for this class.
 * 
 * The class provides a constructor that initializes the health, movement, level, and entityType of the player entity.
 */
public class PlayerEntity extends InventoryEntity{
    
    /**
     * Constructor for the PlayerEntity class.
     * Initializes the health, movement, level, and entityType of the player entity.
     * @param health The health of the player entity.
     * @param movement The movement speed of the player entity.
     * @param level The level of the player entity.
     * @throws InvalidArgumentException if the health, movement, or level is invalid.
     * @throws ArgumentNullException if the inventory is null.
     */
    public PlayerEntity(int health, int movement, int level) throws InvalidArgumentException, ArgumentNullException{
        super(health, movement, level, false);

        entityType = EntityType.PLAYER;
    }
}