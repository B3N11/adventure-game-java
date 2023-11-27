package game.utility;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import uilogic.GridPosition;

/**
 * This class represents the modified data of an enemy in the game. Used to store and save the modified enemies.
 * It extends the Identifiable class and contains methods to set and get the position, health, looted status, and death status of the enemy.
 * 
 * The class contains the following fields:
 * - position: The position of the enemy on the grid.
 * - currentHealth: The current health of the enemy.
 * - looted: Whether the enemy has been looted.
 * - dead: Whether the enemy is dead.
 */
public class ModifiedEnemyData extends Identifiable{
    
    private GridPosition position;
    private int currentHealth;
    private boolean looted;
    private boolean dead;

    public ModifiedEnemyData(String id, GridPosition position, int health, boolean looted, boolean dead) throws ArgumentNullException, InvalidArgumentException{
        setID(id);
        setPosition(position);
        setHealth(health);
        setLooted(looted);
        setDead(dead);
    }

    public GridPosition getPosition() { return position; }
    public int getHealth() { return currentHealth; }
    public boolean isLooted() { return looted; }
    public boolean isDead() { return dead; }

    public void setPosition(GridPosition position) throws ArgumentNullException{
        if(position == null)
            throw new ArgumentNullException();
        this.position = position;
    }

    public void setHealth(int health) throws InvalidArgumentException{
        if(health < 0)
            throw new InvalidArgumentException();
        currentHealth = health;
    }

    public void setLooted(boolean looted){
        this.looted = looted;
    }

    public void setDead(boolean dead){
        this.dead = dead;
    }
}
