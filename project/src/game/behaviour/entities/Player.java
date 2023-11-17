package game.behaviour.entities;

import exception.entity.ItemNotInInventoryException;
import exception.entity.NoWeaponEquippedException;
import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.abstracts.Armor;
import game.behaviour.abstracts.Consumable;
import game.behaviour.abstracts.Equipment;
import game.behaviour.abstracts.Weapon;
import game.behaviour.interfaces.IInteractiveEntity;
import game.enums.EntityCondition;
import game.enums.ModifierType;
import game.utility.general.Identity;
import ui.data.GridPosition;

public class Player extends Identity implements IInteractiveEntity{
    
    private PlayerEntity entity;
    
    //Progression
    private int xp;
    private int requiredXP;

    private int currentHealth;
    private EntityCondition condition;
    private double currentMovement;

    private GridPosition position;

    public Player(String id, PlayerEntity entity) throws ArgumentNullException{
        if(id == null || entity == null)
            throw new ArgumentNullException();
        
        setID(id);
        this.entity = entity;
    }

    public String getInstanceID() { return getID(); }
    public int getXP(){ return xp; }
    public int getRequiredXP(){ return requiredXP; }
    public GridPosition getPosition(){ return position; }
    public int getCurrentHealth() { return currentHealth; }
    public double getCurrentMovement() { return currentMovement; }
    
    @Override
    public PlayerEntity getEntity() { return entity; }
    
    @Override
    public IInteractiveEntity applyStats() {
        currentHealth = entity.getHealth();
        currentMovement = entity.getMovement();
        return this;
    }

    public void setPosition(GridPosition position) throws ArgumentNullException{
        if(position == null)
            throw new ArgumentNullException();
        this.position = position;
    }

    public int addXP(int newXP){
        int leftoverXP = newXP - (requiredXP - xp);

        if(leftoverXP >= 0)
            levelUp(leftoverXP);
        else
            xp += newXP;

        return entity.getLevel();
    }

    private void levelUp(int leftoverXP){
        levelUp();
        xp = leftoverXP;
    }

    public void levelUp(){
        try{
            entity.setLevel(entity.getLevel() + 1);
        }
        catch(Exception e){}

        requiredXP = entity.getLevel() * 150;
    }

    public void addToInventory(Equipment equipment) throws ArgumentNullException{
        if(entity.getInventory() == null)
            entity.createInventory(false);
        entity.getInventory().add(equipment);
    }

    public void addToInventory(Consumable consumable) throws ArgumentNullException{
        if(entity.getInventory() == null)
            entity.createInventory(false);
        entity.addToInventory(consumable);
    }

    public boolean attack(int targetAC, double distance) throws Exception{
        if(entity.getWeapon() == null)
            throw new NoWeaponEquippedException();

        int finalTargetAC = targetAC - (int)entity.getInventory().calculateModifiers(ModifierType.ATTACK);
        return entity.attack(finalTargetAC, distance);
    }

    public int damage(double distance) throws Exception{
        if(entity.getWeapon() == null)
            throw new NoWeaponEquippedException();

        return entity.damage(distance) + (int)entity.getInventory().calculateModifiers(ModifierType.DAMAGE);
    }

    public int getArmorClass() throws Exception{
        int result = entity.getArmor() == null ? (entity.getLevel() + 5) : entity.getArmor().getArmorClass() + entity.getLevel();
        result += (int)entity.getInventory().calculateModifiers(ModifierType.ARMOR_CLASS);
        return result;
    }

    public void equip(Weapon weapon) throws ItemNotInInventoryException, ArgumentNullException{
        if(!entity.getInventory().contains(weapon.getID()))
            throw new ItemNotInInventoryException();

        //Unequip current
        if(entity.getWeapon() != null)
            entity.getWeapon().equip(false);

        entity.equip(weapon);
        entity.getWeapon().equip(true);
    }

    public void equip(Armor armor) throws ItemNotInInventoryException, ArgumentNullException{
        if(!entity.getInventory().contains(armor.getID()))
            throw new ItemNotInInventoryException();
        
        if(entity.getArmor() != null)
            entity.getArmor().equip(false);

        entity.equip(armor);
        entity.getArmor().equip(true);
    }

    @Override
    public void resetMovement() throws Exception{
        currentMovement = entity.getMovement() + entity.getArmor().getMovementBonus() + entity.getInventory().calculateModifiers(ModifierType.MOVEMENT);
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
        Math.clamp(currentHealth, 0, entity.getHealth());

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
        Math.clamp(currentHealth, 0, entity.getHealth());

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
