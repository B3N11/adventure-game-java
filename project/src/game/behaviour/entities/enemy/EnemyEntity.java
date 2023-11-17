package game.behaviour.entities.enemy;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.Inventory;
import game.behaviour.abstracts.Consumable;
import game.behaviour.abstracts.Entity;
import game.behaviour.abstracts.Equipment;
import game.enums.EntityType;

public class EnemyEntity extends Entity{
    
    private int rewardXP;
    transient private Inventory inventory;

    public EnemyEntity(int health, int movement, int level) throws InvalidArgumentException, ArgumentNullException{
        super(health, movement, level);

        inventory = new Inventory(true);
        entityType = EntityType.ENEMY;
    }

    public void addToInventory(Equipment equipment) throws ArgumentNullException{
        if(inventory == null)
            inventory = new Inventory(false);
        inventory.add(equipment);
    }

    public void addToInventory(Consumable consumable) throws ArgumentNullException{
        if(inventory == null)
            inventory = new Inventory(false);
        inventory.add(consumable);
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