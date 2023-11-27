package game.behaviour.entities.enemy;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.abstracts.Entity;
import game.behaviour.interfaces.IInteractiveEntity;
import game.enums.EntityCondition;
import game.utility.general.Identifiable;

/**
 * This class represents an Enemy in the game. This is an instance of an enemy type.
 * It extends the Identifiable class and implements the IInteractiveEntity interface.
 * 
 * The class contains the following fields:
 * - enemyType: The type of the enemy.
 * - currentHealth: The current health of the enemy.
 * - condition: The condition of the enemy.
 * - currentMovement: The current movement speed of the enemy.
 * 
 * The class provides a constructor that initializes these fields and methods to get the enemy type, current health, current movement, instance ID, and asset ID.
 */
public class Enemy extends Identifiable implements IInteractiveEntity{
    
    private EnemyType enemyType;

    private int currentHealth;
    private EntityCondition condition;
    private double currentMovement;

    /**
     * Constructor for the Enemy class.
     * Initializes the id, enemyType, and condition of the enemy.
     * @param id The id of the enemy.
     * @param enemyType The type of the enemy.
     * @throws ArgumentNullException if the enemyType is null.
     */
    public Enemy(String id, EnemyType enemyType) throws ArgumentNullException{
        if(enemyType == null)
            throw new ArgumentNullException();

        setID(id);
        this.enemyType = enemyType;
        condition = EntityCondition.NORMAL;
    }

    public EnemyType getEnemyType() { return enemyType; }
    public int getCurrentHealth() { return currentHealth; }
    public double getCurrentMovement() { return currentMovement; }
    
    @Override
    public String getInstanceID() { return getID(); }

    @Override
    public String getAssetID() { return enemyType.getID(); }

    @Override
    public Entity getEntity() { return enemyType.getEntity(); }
    
    @Override
    public IInteractiveEntity applyStats() {
        currentHealth = enemyType.getEntity().getHealth();
        currentMovement = enemyType.getEntity().getMovement();
        return this;
    }

    @Override
    public Enemy setCurrentHealth(int health) throws InvalidArgumentException{
        if(health < 0)
            throw new InvalidArgumentException();
        currentHealth = health;
        return this;
    }

    @Override
    public boolean move(double distance){
        if(distance > currentMovement)
            return false;

        currentMovement -= distance;
        currentMovement = Double.parseDouble(String.format("%.2f", currentMovement));
        return true;
    }

    @Override
    public void resetMovement() throws Exception{
        currentMovement = enemyType.getEntity().getMovement();
    }

    @Override
    public boolean takeDamage(int damage) throws InvalidArgumentException{
        if(damage < 0)
            throw new InvalidArgumentException();

        currentHealth -= damage;
        currentHealth = Math.clamp(currentHealth, 0, enemyType.getEntity().getHealth());

        //If health is 0, set condition to DEAD
        if(currentHealth == 0){
            die();
            return true;
        }

        return false;
    }

    @Override
    public boolean heal(int amount) throws InvalidArgumentException{
        if(amount < 0)
            throw new InvalidArgumentException();

        currentHealth += amount;
        currentHealth = Math.clamp(currentHealth, 0, enemyType.getEntity().getHealth());

        //If is dead, then resurrect
        if(condition == EntityCondition.DEAD){
            resurrect();
            return true;
        }

        return false;
    }

    @Override
    public boolean isDead(){
        return currentHealth == 0;
    }

    @Override
    public void die(){
        condition = EntityCondition.DEAD;
    }

    @Override
    public void resurrect(){
        condition = EntityCondition.NORMAL;
    }
}