package game.utility;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import ui.data.GridPosition;

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
