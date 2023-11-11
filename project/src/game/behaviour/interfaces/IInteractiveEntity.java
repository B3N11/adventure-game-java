package game.behaviour.interfaces;

import exception.general.InvalidArgumentException;
import game.behaviour.abstracts.Entity;
import ui.data.GridPosition;

public interface IInteractiveEntity {

    public String getInstanceID();

    public int getCurrentHealth();
    
    public double getCurrentMovement();

    public Entity getEntity();

    public GridPosition getPosition();

    public boolean move(double distance);

    public void resetMovement() throws Exception;

    public boolean takeDamage(int damage) throws InvalidArgumentException;

    public boolean heal(int amount) throws InvalidArgumentException;

    public void die();

    public void resurrect();
}
