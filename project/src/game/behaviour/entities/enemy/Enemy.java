package game.behaviour.entities.enemy;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.abstracts.Entity;
import game.behaviour.interfaces.IInteractiveEntity;
import game.enums.EntityCondition;
import game.utility.general.Identifiable;
import ui.data.GridPosition;

public class Enemy extends Identifiable implements IInteractiveEntity{
    
    private EnemyType enemyType;

    private int currentHealth;
    private EntityCondition condition;
    private double currentMovement;

    private GridPosition position;

    public Enemy(String id, EnemyType enemyType) throws ArgumentNullException{
        if(enemyType == null)
            throw new ArgumentNullException();

        setID(id);
        this.enemyType = enemyType;
        condition = EntityCondition.NORMAL;
    }

    public String getInstanceID() { return getID(); }
    public EnemyType getEnemyType() { return enemyType; }
    public GridPosition getPosition() { return position; }
    public Entity getEntity() { return enemyType.getEntity(); }
    public int getCurrentHealth() { return currentHealth; }
    public double getCurrentMovement() { return currentMovement; }

    @Override
    public IInteractiveEntity applyStats() {
        currentHealth = enemyType.getEntity().getHealth();
        currentMovement = enemyType.getEntity().getMovement();
        return this;
    }

    public Enemy setCurrentHealth(int health) throws InvalidArgumentException{
        if(health < 0)
            throw new InvalidArgumentException();
        int damage = currentHealth - health;
        takeDamage(damage);
        return this;
    }

    public Enemy setPosition(GridPosition position) throws ArgumentNullException{
        if(position == null)
            throw new ArgumentNullException();

        this.position = position;
        return this;
    }

    public boolean move(double distance){
        if(distance > currentMovement)
            return false;

        currentMovement -= distance;
        return true;
    }

    public void resetMovement() throws Exception{
        currentMovement = enemyType.getEntity().getMovement();
    }

    public boolean takeDamage(int damage) throws InvalidArgumentException{
        if(damage < 0)
            throw new InvalidArgumentException();

        currentHealth -= damage;
        Math.clamp(currentHealth, 0, enemyType.getEntity().getHealth());

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
        Math.clamp(currentHealth, 0, enemyType.getEntity().getHealth());

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
