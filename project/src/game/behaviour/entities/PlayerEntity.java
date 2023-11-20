package game.behaviour.entities;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.enums.EntityType;

public class PlayerEntity extends InventoryEntity{
    
    public PlayerEntity(int health, int movement, int level) throws InvalidArgumentException, ArgumentNullException{
        super(health, movement, level, false);

        entityType = EntityType.PLAYER;
    }
}