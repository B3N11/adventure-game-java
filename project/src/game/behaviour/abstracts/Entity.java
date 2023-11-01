package game.behaviour.abstracts;

import exception.dice.DefaultDiceNotSetException;
import exception.entity.ItemNotInInventoryException;
import exception.entity.NoWeaponEquippedException;
import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.enums.EntityCondition;
import game.utility.dice.DiceRoller;
import game.utility.general.Identifiable;

public abstract class Entity extends Identifiable{
        
    protected int health;
    protected int currentHealth;            //REMOVE!!!
    protected EntityCondition condition;    //REMOVE!!!

    protected double movement;
    protected double currentMovement;       ///REMOVE!!!
    
    protected int level;

    protected int initiativeBonus;
    protected int rolledInitiative;         //REMOVE!!!

    protected Armor armor;
    protected Weapon weapon;

    public Entity(String id, int health, int movement, int level) throws InvalidArgumentException, ArgumentNullException{
        setID(id);
        setHealth(health);
        setMovement(movement);
        setLevel(level);

        condition = EntityCondition.NORMAL;
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

    public Entity setInitiativeBonus(int bonus){
        initiativeBonus = bonus;
        return this;
    }

    public int getHealth(){ return health; }
    public int getLevel(){ return level; }
    public int getRolledInitiative() { return rolledInitiative; }
    public String setID(){ return id; }

    public int rollInitiative() throws DefaultDiceNotSetException{
        var roller = DiceRoller.getInstance();
        int baseRoll = roller.rollDefault();
        rolledInitiative = baseRoll + initiativeBonus;

        return rolledInitiative;
    }
    
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

    public boolean move(double distance){
        if(distance > currentMovement)
            return false;

        currentMovement -= distance;
        return true;
    }

    public void resetMovement() throws Exception{
        currentMovement = movement + armor.getMovementBonus();
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