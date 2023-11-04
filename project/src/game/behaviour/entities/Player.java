package game.behaviour.entities;

import java.io.Serializable;

import exception.entity.ItemNotInInventoryException;
import exception.entity.NoWeaponEquippedException;
import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.Inventory;
import game.behaviour.abstracts.Armor;
import game.behaviour.abstracts.Consumable;
import game.behaviour.abstracts.Entity;
import game.behaviour.abstracts.Equipment;
import game.behaviour.abstracts.Weapon;
import game.behaviour.interfaces.IInteractiveEntity;
import game.enums.EntityCondition;
import game.enums.ModifierType;

public class Player extends Entity implements IInteractiveEntity{
    
    //Progression
    private int xp;
    private int requiredXP;

    private int currentHealth;
    private EntityCondition condition;
    protected double currentMovement;

    transient private Inventory inventory;

    public Player(int health, int movement, int level) throws InvalidArgumentException, ArgumentNullException{
        super(health, movement, level);

        inventory = new Inventory(false);
    }

    public int getXP(){ return xp; }
    public int getRequiredXP(){ return requiredXP; }

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

    public Inventory getInventory(){ return inventory; }

    @Override
    public boolean attack(int targetAC, int distance) throws Exception{
        if(weapon == null)
            throw new NoWeaponEquippedException();

        int finalTargetAC = targetAC - (int)inventory.calculateModifiers(ModifierType.ATTACK);
        return weapon.attack(finalTargetAC, distance);
    }

    @Override
    public int damage(int distance) throws Exception{
        if(weapon == null)
            throw new NoWeaponEquippedException();

        return weapon.damage(distance) + level + (int)inventory.calculateModifiers(ModifierType.DAMAGE);
    }

    @Override
    public void resetMovement() throws Exception{
        currentMovement = movement + armor.getMovementBonus() + inventory.calculateModifiers(ModifierType.MOVEMENT);
    }

    @Override
    public int getArmorClass() throws Exception{
        int result = armor == null ? (level + 5) : armor.getArmorClass() + level;
        result += (int)inventory.calculateModifiers(ModifierType.ARMOR_CLASS);
        return result;
    }

    @Override
    public void equip(Weapon weapon) throws ItemNotInInventoryException, ArgumentNullException{
        if(!inventory.contains(weapon.getID()))
            throw new ItemNotInInventoryException();

        //Unequip current
        if(this.weapon != null)
            this.weapon.equip(false);

        this.weapon = weapon;
        this.weapon.equip(true);
    }

    @Override
    public void equip(Armor armor) throws ItemNotInInventoryException, ArgumentNullException{
        if(!inventory.contains(armor.getID()))
            throw new ItemNotInInventoryException();
        
        if(this.armor != null)
            this.armor.equip(false);

        this.armor = armor;
        this.armor.equip(true);
    }

    @Override
    public boolean move(double distance) {
        if(distance > currentMovement)
            return false;

        currentMovement -= distance;
        return true;
    }

    @Override
    public boolean takeDamage(int damage) throws InvalidArgumentException {
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

    @Override
    public boolean heal(int amount) throws InvalidArgumentException {
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

    @Override
    public void die() {
        condition = EntityCondition.DEAD;
    }

    @Override
    public void resurrect() {
        condition = EntityCondition.NORMAL;
    }
}
