package game.behaviour.entities.player;

import exception.entity.ItemNotInInventoryException;
import exception.entity.NoWeaponEquippedException;
import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.abstracts.Weapon;
import game.behaviour.entities.IInteractiveEntity;
import game.behaviour.entities.items.Item;
import game.behaviour.entities.items.equipment.Armor;
import game.enums.EntityCondition;
import game.enums.ModifierType;
import game.logic.event.Event;
import game.logic.event.EventArgument;
import game.logic.event.IEventListener;
import game.utility.Identity;

/**
 * This class represents a Player in the game.
 * It extends the Identity class and implements the IInteractiveEntity interface.
 * 
 * The class contains the following fields:
 * - entity: The player entity.
 * - xp: The experience points of the player.
 * - requiredXP: The experience points required for the player to level up.
 * - currentHealth: The current health of the player.
 * - condition: The condition of the player.
 * - currentMovement: The current movement speed of the player.
 * - onPlayerDied: Event that is triggered when the player dies.
 * - onPlayerLeveledUp: Event that is triggered when the player levels up.
 * 
 * The class provides a constructor that initializes the id and entity of the player.
 */
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

    /**
     * Constructor for the Player class.
     * Initializes the id and entity of the player.
     * @param id The id of the player.
     * @param entity The player entity.
     * @throws ArgumentNullException if the id or entity is null.
     */
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

    /**
     * Constructor for the Player class.
     * Initializes the id and entity of the player.
     * @param id The id of the player.
     * @param entity The player entity.
     * @throws ArgumentNullException if the id or entity is null.
     */
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

    /**
     * Adds experience points to the player. If the player has enough experience points to level up, it levels up the player.
     * @param xp The experience points to add.
     * @throws InvalidArgumentException if the xp is less than 0.
     */
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

    /**
     * Levels up the player.
     * @throws Exception if an error occurs during leveling up.
     */
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

    /**
     * Adds an item to the player's inventory.
     * @param item The item to add.
     * @throws ArgumentNullException if the item is null.
     */
    public void addToInventory(Item item) throws ArgumentNullException{
        if(entity.getInventory() == null)
            entity.createInventory(false);
        entity.getInventory().add(item);
    }

    /**
     * Makes the player attack a target. If the player has no weapon equipped, it throws a NoWeaponEquippedException. Calculates the final target armor class by subtracting the player's attack modifier from the target's armor class.
     * @param targetAC The armor class of the target.
     * @param distance The distance to the target.
     * @return True if the attack is successful, false otherwise.
     * @throws Exception if an error occurs during the attack.
     */
    public boolean attack(int targetAC, double distance) throws Exception{
        if(entity.getWeapon() == null)
            throw new NoWeaponEquippedException();

        int finalTargetAC = targetAC - (int)entity.getInventory().calculateModifiers(ModifierType.ATTACK);
        return entity.attack(finalTargetAC, distance);
    }

    /**
     * Calculates the damage the player deals to a target. Adds the player's damage modifier to the damage dealt.
     * @param distance The distance to the target.
     * @return The damage dealt to the target.
     * @throws Exception if an error occurs during the damage calculation.
     */
    public int damage(double distance) throws Exception{
        if(entity.getWeapon() == null)
            throw new NoWeaponEquippedException();

        return entity.damage(distance) + (int)entity.getInventory().calculateModifiers(ModifierType.DAMAGE);
    }
    
    /**
     * Calculates the armor class of the player. 
     * The armor class is calculated by adding the player's base armor class and any armor class modifiers from the player's inventory.
     * @return The armor class of the player.
     * @throws Exception if an error occurs during the calculation.
     */
    public int getArmorClass() throws Exception{
        return entity.getArmorClass() + (int)entity.getInventory().calculateModifiers(ModifierType.ARMOR_CLASS);
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