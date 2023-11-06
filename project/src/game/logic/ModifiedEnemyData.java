package game.logic;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.utility.general.Identifiable;
import ui.data.GridPosition;

public class ModifiedEnemyData extends Identifiable{
    
    private GridPosition position;
    private int currentHealth;
    private boolean looted;

    public ModifiedEnemyData(String id, GridPosition position, int health, boolean looted) throws ArgumentNullException{
        setID(id);
        this.position = position;
        currentHealth = health;
        this.looted = looted;
    }

    public GridPosition getPosition() { return position; }
    public int getHealth() { return currentHealth; }
    public boolean isLooted() { return looted; }

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
}
