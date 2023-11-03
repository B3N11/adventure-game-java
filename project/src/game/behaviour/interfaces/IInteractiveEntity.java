package game.behaviour.interfaces;

import exception.general.InvalidArgumentException;

public interface IInteractiveEntity {

    public boolean move(double distance);

    public void resetMovement() throws Exception;

    public boolean takeDamage(int damage) throws InvalidArgumentException;

    public boolean heal(int amount) throws InvalidArgumentException;

    public void die();

    public void resurrect();
}
