package game.behaviour.abstracts;

import exception.dice.DefaultDiceNotSetException;
import exception.entity.ItemNotInInventoryException;
import exception.entity.NoWeaponEquippedException;
import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.enums.EntityCondition;
import game.utility.dice.DiceRoller;

public abstract class Entity{
        
    protected int health;
    protected double movement;    
    protected int level;

    protected Armor armor;
    protected Weapon weapon;

    public Entity(int health, int movement, int level) throws InvalidArgumentException, ArgumentNullException{
        setHealth(health);
        setMovement(movement);
        setLevel(level);
    }

    public Entity setHealth(int health) throws InvalidArgumentException{
        if(health < 0)
            throw new InvalidArgumentException();
        
        this.health = health;
        return this;
    }

    public Entity setMovement(int movement) throws InvalidArgumentException{
        if(movement < 0)
            throw new InvalidArgumentException();

        this.movement = movement;
        return this;
    }

    public Entity setLevel(int level) throws InvalidArgumentException{
        if(level <= 0)
            throw new InvalidArgumentException();

        this.level = level;
        return this;
    }

    public int getHealth(){ return health; }
    public int getLevel(){ return level; }
    
    public double getMovement() {
        double result = armor == null ? movement : movement + armor.getMovementBonus();
        return result;
    }

    public int getArmorClass() throws Exception{
        int result = armor == null ? (level + 5) : armor.getArmorClass() + level;
        return result;
    }

    public Weapon getWeapon(){ return weapon; }

    public void equip(Weapon weapon) throws ItemNotInInventoryException, ArgumentNullException{
        if(weapon == null)
            throw new ArgumentNullException();
        
        this.weapon = weapon;
    }

    public void equip(Armor armor) throws ItemNotInInventoryException, ArgumentNullException{
        if(armor == null)
            throw new ArgumentNullException();
        
        this.armor = armor;
    }

    public boolean attack(int targetAC, int distance) throws Exception{
        if(weapon == null)
            throw new NoWeaponEquippedException();

        return weapon.attack(targetAC, distance);
    }

    public int damage(int distance) throws Exception{
        if(weapon == null)
            throw new NoWeaponEquippedException();

        return weapon.damage(distance) + level;
    }
}