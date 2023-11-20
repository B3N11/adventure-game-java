package game.behaviour.interfaces;

import exception.general.InvalidArgumentException;
import game.behaviour.abstracts.Entity;

public interface IInteractiveEntity {

    //Sets current stat values to the max
    public IInteractiveEntity applyStats();

    public String getInstanceID();

    public String getAssetID();

    public int getCurrentHealth();
    
    public double getCurrentMovement();

    public Entity getEntity();

    public boolean move(double distance);

    public void resetMovement() throws Exception;

    public boolean takeDamage(int damage) throws InvalidArgumentException;

    public boolean heal(int amount) throws InvalidArgumentException;

    public void die();

    public void resurrect();
}
