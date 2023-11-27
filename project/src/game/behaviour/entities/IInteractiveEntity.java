package game.behaviour.entities;

import exception.general.InvalidArgumentException;
import game.behaviour.abstracts.Entity;

/**
 * This interface represents an interactive entity in the game.
 * It provides methods to get and set the current health, get the current movement, move the entity, take damage, heal, and check if the entity is dead.
 */
public interface IInteractiveEntity {

    /**
     * Sets current stat values to the max.
     * @return This IInteractiveEntity, to allow for chaining.
     */
    public IInteractiveEntity applyStats();

    /**
     * Gets the instance ID of the entity.
     * @return The instance ID of the entity.
     */
    public String getInstanceID();

    
    /**
     * Gets the asset ID of the entity.
     * @return The asset ID of the entity.
     */
    public String getAssetID();

    public int getCurrentHealth();
    
    public double getCurrentMovement();

    public Entity getEntity();

    /**
     * Sets the current health of the entity.
     * @param amount The amount to set the current health to.
     * @return This IInteractiveEntity, to allow for chaining.
     * @throws InvalidArgumentException if the amount is less than 0.
     */
    public IInteractiveEntity setCurrentHealth(int amount) throws InvalidArgumentException;

    /**
     * Moves the entity a certain distance.
     * @param distance The distance to move the entity.
     * @return True if the entity was able to move the specified distance, false otherwise.
     */
    public boolean move(double distance);

    /**
     * Resets the movement of the entity.
     * @throws Exception if an error occurs during the reset.
     */
    public void resetMovement() throws Exception;

    /**
     * Makes the entity take a certain amount of damage.
     * @param damage The amount of damage for the entity to take.
     * @return True if the entity was able to take the damage, false otherwise.
     * @throws InvalidArgumentException if the damage is less than 0.
     */
    public boolean takeDamage(int damage) throws InvalidArgumentException;

    /**
     * Heals the entity by a certain amount.
     * @param amount The amount to heal the entity.
     * @return True if the entity was able to be healed, false otherwise.
     * @throws InvalidArgumentException if the amount is less than 0.
     */
    public boolean heal(int amount) throws InvalidArgumentException;

    /**
     * Checks if the entity is dead.
     * @return True if the entity is dead, false otherwise.
     */
    public boolean isDead();

    /**
     * Makes the entity die.
     */
    public void die();

    /**
     * Resurrects the entity.
     */
    public void resurrect();
}
