package game.behaviour.abstracts;

import exception.general.ArgumentNullException;
import game.behaviour.entities.enemy.EnemyEntity;
import game.enums.EnemyBehaviourControllerType;

public abstract class EnemyBehaviourController {
    
    protected EnemyEntity enemyEntity;
    protected EnemyBehaviourControllerType controllerType;

    public EnemyBehaviourController(EnemyEntity enemy) throws ArgumentNullException{
        if(enemy == null)
            throw new ArgumentNullException();

        this.enemyEntity = enemy;
    }

    public EnemyBehaviourControllerType getControllerType(){ return controllerType; }
    public EnemyEntity getEnemyEntity(){ return enemyEntity; }
}
