package game.behaviour.entities.enemy;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.entities.InventoryEntity;
import game.enums.EntityType;

public class EnemyEntity extends InventoryEntity{
    
    private int rewardXP;

    public EnemyEntity(int health, int movement, int level) throws InvalidArgumentException, ArgumentNullException{
        super(health, movement, level, true);

        entityType = EntityType.ENEMY;
    }

    public EnemyEntity setRewardXP(int xp) throws InvalidArgumentException{
        if(xp < 0)
            throw new InvalidArgumentException();

        rewardXP = xp;
        return this;
    }

    public int getRewardXP() { return rewardXP; }
}