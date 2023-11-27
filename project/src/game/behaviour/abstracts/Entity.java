package game.behaviour.abstracts;

import java.io.Serializable;

import exception.entity.NoWeaponEquippedException;
import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.entities.items.equipment.Armor;
import game.enums.EntityType;

/**
 * This abstract class represents an Entity in the game.
 * It includes properties such as name, entityType, health, movement, level, armor, and weapon.
 * 
 * The class contains the following fields:
 * - name: The name of the entity.
 * - entityType: The type of the entity.
 * - health: The health of the entity.
 * - movement: The movement speed of the entity.
 * - level: The level of the entity.
 * - armor: The armor the entity is wearing.
 * - weapon: The weapon the entity is wielding.
 * 
 * The class provides getter methods for these fields and a setter for the name.
 */
public abstract class Entity implements Serializable{
        
    protected String name;
    protected EntityType entityType;
    protected int health;
    protected double movement;    
    protected int level;

    protected transient Armor armor;
    protected transient Weapon weapon;

    protected Entity(int health, int movement, int level) throws InvalidArgumentException, ArgumentNullException{
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
    
    /**
     * Gets the movement speed of the entity.
     * @return The movement speed of the entity. Applies armor bonus if armor is equipped.
     */
    public double getMovement() {
        double result = armor == null ? movement : movement + armor.getMovementBonus();
        return result;
    }

    /**
     * Calculates and returns the armor class of the entity.
     * If the entity has no armor equipped, it returns the sum of the entity's level and 5.
     * If the entity has armor equipped, it returns the sum of the armor's armor class and the entity's level.
     * @return The armor class of the entity.
     */
    public int getArmorClass(){
        int result = armor == null ? (level + 5) : armor.getArmorClass() + level;
        return result;
    }
    
    /**
     * Equips the entity with a weapon.
     * @param weapon The weapon to equip.
     * @throws ArgumentNullException if the weapon is null.
     */
    public void equip(Weapon weapon) throws ArgumentNullException{
        if(weapon == null)
            throw new ArgumentNullException();
        
        this.weapon = weapon;
    }

    /**
     * Equips the entity with armor.
     * @param armor The armor to equip.
     * @throws ArgumentNullException if the armor is null.
     */
    public void equip(Armor armor) throws ArgumentNullException{
        if(armor == null)
            throw new ArgumentNullException();
        
        this.armor = armor;
    }

    /**
     * Performs an attack roll with the equipped weapon against a target's armor class.
     * @param targetAC The armor class of the target.
     * @param distance The distance to the target.
     * @return true if the attack hits, false otherwise.
     * @throws Exception if the entity has no weapon equipped.
     */
    public boolean attack(int targetAC, double distance) throws Exception{
        if(weapon == null)
            throw new NoWeaponEquippedException();

        return weapon.attack(targetAC, distance);
    }

    /**
     * Calculates the damage dealt by the entity's weapon.
     * @param distance The distance to the target.
     * @return The calculated damage.
     * @throws Exception if the entity has no weapon equipped.
     */
    public int damage(double distance) throws Exception{
        if(weapon == null)
            throw new NoWeaponEquippedException();

        return weapon.damage(distance) + level;
    }
}