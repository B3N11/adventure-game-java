package main.game.behaviour.abstracts;

import main.exception.general.ArgumentNullException;
import main.game.behaviour.entities.IInteractiveEntity;
import main.game.behaviour.entities.enemy.EnemyEntity;
import main.game.behaviour.entities.enemy.controller.BerserkerEnemyController;
import main.game.enums.EnemyBehaviourControllerType;
import main.game.utility.event.Event;
import main.game.utility.event.EventArgument;
import main.game.utility.event.IEventListener;

/**
 * This abstract class represents an EnemyBehaviourController in the game.
 * It includes properties such as enemyEntity, controllerType, attemptedToHitEvent, and damageEvent.
 * 
 * The class contains the following fields:
 * - enemyEntity: The enemy entity this controller is controlling.
 * - controllerType: The type of the controller.
 * - attemptedToHitEvent: Event that is triggered when the enemy attempts to hit.
 * - damageEvent: Event that is triggered when the enemy takes damage.
 * 
 * The class provides a constructor that initializes these fields and a setEvents method that sets up the events.
 */
public abstract class EnemyBehaviourController {
    
    protected EnemyEntity enemyEntity;
    protected EnemyBehaviourControllerType controllerType;

    protected Event attemptedToHitEvent;
    protected Event damageEvent;

    protected EnemyBehaviourController(EnemyEntity enemy) throws ArgumentNullException{
        if(enemy == null)
            throw new ArgumentNullException();

        this.enemyEntity = enemy;
        setEvents();
    }

    /**
     * Sets up the events for the controller.
     * Initializes the attemptedToHitEvent and damageEvent and sets their removeOnRun property to false.
     */
    protected void setEvents(){
        attemptedToHitEvent = new Event(new EventArgument<String>());
        attemptedToHitEvent.setRemoveOnRun(false);
        
        damageEvent = new Event(new EventArgument<String>());
        damageEvent.setRemoveOnRun(false);
    }

    public EnemyBehaviourControllerType getControllerType(){ return controllerType; }
    public EnemyEntity getEnemyEntity(){ return enemyEntity; }

    /**
     * Returns an instance of an EnemyBehaviourController of the specified type for the specified enemy entity.
     * @param type The type of the EnemyBehaviourController.
     * @param entity The enemy entity the controller will control.
     * @return An instance of an EnemyBehaviourController of the specified type.
     * @throws ArgumentNullException if the entity is null.
     */
    public static EnemyBehaviourController getTypeInstance(EnemyBehaviourControllerType type, EnemyEntity entity) throws ArgumentNullException{
        switch (type) {
            default:
                return new BerserkerEnemyController(entity);
        }
    }

    /**
     * Executes the enemy's behaviour towards a target.
     * @param target The target of the enemy's behaviour.
     * @param distance The distance to the target.
     * @throws Exception if an error occurs during the execution of the behaviour.
     */
    public abstract void runEnemy(IInteractiveEntity target, double distance) throws Exception;

    /**
     * Adds event listeners to the attemptedToHitEvent and damageEvent.
     * @param attemptedToHitListener The listener for the attemptedToHitEvent.
     * @param damageEventListener The listener for the damageEvent.
     */
    public void addEventListeners(IEventListener attemptedToHitListener, IEventListener damageEventListener){
        try{ attemptedToHitEvent.addEventListener(attemptedToHitListener); }
        catch(Exception e){}
        try{ damageEvent.addEventListener(damageEventListener); }
        catch(Exception e){}
    }
}