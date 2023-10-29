package game.behaviour.abstracts;

import java.io.Serializable;

import exception.dice.DefaultDiceNotSetException;
import exception.dice.InvalidDiceSideCountException;
import exception.entity.ItemNotInInventoryException;
import exception.entity.NoWeaponEquippedException;
import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.Inventory;
import game.enums.EntityCondition;
import game.enums.ModifierType;
import game.utility.general.Identifiable;

public abstract class Entity extends Identifiable implements Serializable{
        
    protected int health;
    protected int currentHealth;
    protected EntityCondition condition;

    protected double movement;
    protected double currentMovement;
    
    protected int level;
    protected int xp;
    protected int requiredXP;

    protected Armor armor;
    protected Weapon weapon;
    protected Inventory inventory;

    public Entity(String id, int health, int movement, int level) throws InvalidArgumentException, ArgumentNullException{
        setID(id);
        setHealth(health);
        setMovement(movement);
        setLevel(level);

        condition = EntityCondition.NORMAL;
        inventory = new Inventory(false);
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
    public int getXP(){ return xp; }
    public int getRequiredXP(){ return requiredXP; }
    public String setID(){ return id; }
    
    public double getMovement() {
        double result = armor == null ? movement : movement + armor.getMovementBonus();
        return result;
    }

    public int getArmorClass() throws Exception{
        int result = armor == null ? (level + 5) : armor.getArmorClass() + level;
        result += (int)inventory.calculateModifiers(ModifierType.ARMOR_CLASS);
        return result;
    }

    public int addXP(int newXP){
        int leftoverXP = newXP - (requiredXP - xp);

        if(leftoverXP >= 0)
            levelUp(leftoverXP);
        else
            xp += newXP;

        return level;
    }

    private void levelUp(int leftoverXP){
        levelUp();
        xp = leftoverXP;
    }

    public void levelUp(){
        level++;
        requiredXP = level * 150;
    }

    public Weapon getWeapon(){ return weapon; }

    public void addToInventory(Equipment equipment) throws ArgumentNullException{
        inventory.add(equipment);
    }

    public void addToInventory(Consumable consumable) throws ArgumentNullException{
        inventory.add(consumable);
    }

    public void equip(Weapon weapon) throws ItemNotInInventoryException, ArgumentNullException{
        if(!inventory.contains(weapon.getID()))
            throw new ItemNotInInventoryException();
        
        this.weapon = weapon;
    }

    public void equip(Armor armor) throws ItemNotInInventoryException, ArgumentNullException{
        if(!inventory.contains(armor.getID()))
            throw new ItemNotInInventoryException();
        
        this.armor = armor;
    }

    public boolean attack(int targetAC, int distance) throws Exception{
        if(weapon == null)
            throw new NoWeaponEquippedException();

        int finalTargetAC = targetAC - (int)inventory.calculateModifiers(ModifierType.ATTACK);
        return weapon.attack(finalTargetAC, distance);
    }

    public int damage(int distance) throws Exception{
        if(weapon == null)
            throw new NoWeaponEquippedException();

        return weapon.damage(distance) + level + (int)inventory.calculateModifiers(ModifierType.DAMAGE);
    }

    public boolean move(double distance){
        if(distance > currentMovement)
            return false;

        currentMovement -= distance;
        return true;
    }

    public void resetMovement() throws Exception{
        currentMovement = movement + inventory.calculateModifiers(ModifierType.MOVEMENT);
    }

    public boolean takeDamage(int damage) throws InvalidArgumentException{
        if(damage < 0)
            throw new InvalidArgumentException();

        currentHealth -= damage;
        Math.clamp(currentHealth, 0, health);

        //If health is 0, set condition to DEAD
        if(currentHealth == 0){
            die();
            return true;
        }

        return false;
    }

    public boolean heal(int amount) throws InvalidArgumentException{
        if(amount < 0)
            throw new InvalidArgumentException();

        currentHealth += amount;
        Math.clamp(currentHealth, 0, health);

        //If is dead, then resurrect
        if(condition == EntityCondition.DEAD){
            resurrect();
            return true;
        }

        return false;
    }

    public void die(){
        condition = EntityCondition.DEAD;
    }

    public void resurrect(){
        condition = EntityCondition.NORMAL;
    }
}