package game.behaviour.abstracts;

import java.io.Serializable;

import exception.entity.ItemNotInInventoryException;
import exception.entity.NoWeaponEquippedException;
import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.enums.EntityType;

public abstract class Entity implements Serializable{
        
    protected String name;
    protected EntityType entityType;
    protected int health;
    protected double movement;    
    protected int level;

    transient protected Armor armor;
    transient protected Weapon weapon;

    public Entity(int health, int movement, int level) throws InvalidArgumentException, ArgumentNullException{
        setHealth(health);
        setMovement(movement);
        setLevel(level);
    }

    public int getHealth(){ return health; }
    public int getLevel(){ return level; }
    public String getName(){ return name; }
    public Weapon getWeapon(){ return weapon; }
    public Armor getArmor(){ return armor; }
    public EntityType getEntityType() { return entityType; }

    public Entity setEntityType(EntityType type){
        this.entityType = type;
        return this;
    }
    
    public Entity setName(String name) throws ArgumentNullException{
        if(name == null)
            throw new ArgumentNullException();
        this.name = name;
        return this;
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
    
    public double getMovement() {
        double result = armor == null ? movement : movement + armor.getMovementBonus();
        return result;
    }

    public int getArmorClass() throws Exception{
        int result = armor == null ? (level + 5) : armor.getArmorClass() + level;
        return result;
    }

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

    public boolean attack(int targetAC, double distance) throws Exception{
        if(weapon == null)
            throw new NoWeaponEquippedException();

        return weapon.attack(targetAC, distance);
    }

    public int damage(double distance) throws Exception{
        if(weapon == null)
            throw new NoWeaponEquippedException();

        return weapon.damage(distance) + level;
    }
}