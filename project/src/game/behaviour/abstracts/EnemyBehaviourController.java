package game.behaviour.abstracts;

import exception.general.ArgumentNullException;
import game.behaviour.entities.enemy.EnemyEntity;
import game.behaviour.entities.enemy.controller.BerserkerEnemyController;
import game.behaviour.interfaces.IInteractiveEntity;
import game.enums.EnemyBehaviourControllerType;
import game.logic.event.Event;
import game.logic.event.EventArgument;
import game.logic.event.IEventListener;

public abstract class EnemyBehaviourController {
    
    protected EnemyEntity enemyEntity;
    protected EnemyBehaviourControllerType controllerType;

    protected Event attemptedToHitEvent;
    protected Event damageEvent;

    public EnemyBehaviourController(EnemyEntity enemy) throws ArgumentNullException{
        if(enemy == null)
            throw new ArgumentNullException();

        this.enemyEntity = enemy;
        setEvents();
    }

    protected void setEvents(){
        attemptedToHitEvent = new Event(new EventArgument<String>());
        attemptedToHitEvent.setRemoveOnRun(false);
        
        damageEvent = new Event(new EventArgument<String>());
        damageEvent.setRemoveOnRun(false);
    }

    public EnemyBehaviourControllerType getControllerType(){ return controllerType; }
    public EnemyEntity getEnemyEntity(){ return enemyEntity; }

    public static EnemyBehaviourController getTypeInstance(EnemyBehaviourControllerType type, EnemyEntity entity) throws ArgumentNullException{
        switch (type) {
            default:
                return new BerserkerEnemyController(entity);
        }
    }

    public abstract void runEnemy(IInteractiveEntity target, double distance) throws Exception;

    public void addEventListeners(IEventListener attemptedToHitListener, IEventListener damageEventListener){
        try{ attemptedToHitEvent.addEventListener(attemptedToHitListener); }
        catch(Exception e){}
        try{ damageEvent.addEventListener(damageEventListener); }
        catch(Exception e){}
    }
}