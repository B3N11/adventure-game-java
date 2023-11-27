package game.behaviour.entities;

import exception.entity.ItemNotInInventoryException;
import exception.entity.NoWeaponEquippedException;
import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.Armor;
import game.behaviour.Item;
import game.behaviour.abstracts.Weapon;
import game.behaviour.interfaces.IInteractiveEntity;
import game.enums.EntityCondition;
import game.enums.ModifierType;
import game.logic.event.Event;
import game.logic.event.EventArgument;
import game.logic.event.IEventListener;
import game.utility.general.Identity;

public class Player extends Identity implements IInteractiveEntity{
    
    private PlayerEntity entity;
    
    //Progression
    private int xp;
    private int requiredXP;

    private int currentHealth;
    private EntityCondition condition;
    private double currentMovement;

    transient private Event onPlayerDied;
    transient private Event onPlayerLeveledUp;

    public Player(String id, PlayerEntity entity) throws ArgumentNullException{
        if(id == null || entity == null)
            throw new ArgumentNullException();
        
        setID(id);
        this.entity = entity;
    }

    public int getXP(){ return xp; }
    public int getRequiredXP(){ return requiredXP; }
    public int getCurrentHealth() { return currentHealth; }
    public double getCurrentMovement() { return currentMovement; }

    public void addEventListeners(IEventListener playerDied, IEventListener playerLeveledUp) throws ArgumentNullException{
        if(playerDied == null || playerLeveledUp == null)
            throw new ArgumentNullException();

        onPlayerDied = new Event(new EventArgument<Player>().setArgument(this));
        onPlayerDied.addEventListener(playerDied);
        onPlayerLeveledUp = new Event(new EventArgument<Player>().setArgument(this));
        onPlayerLeveledUp.addEventListener(playerLeveledUp);
    }

    @Override
    public String getInstanceID() { return getID(); }

    @Override
    public String getAssetID() { return getID(); }
    
    @Override
    public PlayerEntity getEntity() { return entity; }
    
    @Override
    public IInteractiveEntity applyStats() {
        currentHealth = entity.getHealth();
        currentMovement = entity.getMovement();
        return this;
    }

    public void setRequiredXP(int amount) throws InvalidArgumentException{
        if(amount < 0)
            throw new InvalidArgumentException();
        requiredXP = amount;
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
        try{ entity.setLevel(entity.getLevel() + 1); }
        catch(Exception e){}

        try{
            setRequiredXP(entity.getLevel() * 150);
            entity.setHealth((int)(entity.getHealth() * 1.75));
        }
        catch(InvalidArgumentException e) {/* Wont happen */}

        try{ onPlayerLeveledUp.triggerEvent(); }
        catch(Exception e){}
    }

    public void addToInventory(Item item) throws ArgumentNullException{
        if(entity.getInventory() == null)
            entity.createInventory(false);
        entity.getInventory().add(item);
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

        entity.equip(weapon);
    }

    public void equip(Armor armor) throws ItemNotInInventoryException, ArgumentNullException{
        if(!entity.getInventory().contains(armor.getID()))
            throw new ItemNotInInventoryException();

        entity.equip(armor);
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
        currentMovement = Double.parseDouble(String.format("%.2f", currentMovement));
        return true;
    }

    @Override
    public boolean takeDamage(int damage) throws InvalidArgumentException {
        if(damage < 0)
            throw new InvalidArgumentException();

        currentHealth -= damage;
        currentHealth = Math.clamp(currentHealth, 0, entity.getHealth());

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
        currentHealth = Math.clamp(currentHealth, 0, entity.getHealth());

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

        try{ onPlayerDied.triggerEvent(); }
        catch(Exception e){}
    }

    @Override
    public void resurrect() {
        condition = EntityCondition.NORMAL;
    }

    @Override
    public IInteractiveEntity setCurrentHealth(int amount) throws InvalidArgumentException {
        if(amount < 0)
            throw new InvalidArgumentException();
        
        currentHealth = amount;
        return this;
    }

    @Override
    public boolean isDead() {
        return currentHealth == 0;
    }
}