package game.behaviour.entities.enemy;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.Inventory;
import game.behaviour.abstracts.Entity;

public class EnemyEntity extends Entity{
    
    private int rewardXP;
    private Inventory inventory;

    public EnemyEntity(int health, int movement, int level) throws InvalidArgumentException, ArgumentNullException{
        super(health, movement, level);

        inventory = new Inventory(true);
    }

    public EnemyEntity setRewardXP(int xp) throws InvalidArgumentException{
        if(xp < 0)
            throw new InvalidArgumentException();

        rewardXP = xp;
        return this;
    }

    public int getRewardXP() { return rewardXP; }
    public Inventory getInventory() { return inventory; }
}