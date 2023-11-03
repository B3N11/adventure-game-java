package game.behaviour.entities.enemy.controller;

import exception.general.ArgumentNullException;
import game.behaviour.abstracts.EnemyBehaviourController;
import game.behaviour.entities.enemy.EnemyEntity;
import game.enums.EnemyBehaviourControllerType;

public class RangerEnemyController extends EnemyBehaviourController{

    public RangerEnemyController(EnemyEntity enemy) throws ArgumentNullException {
        super(enemy);
        
        controllerType = EnemyBehaviourControllerType.RANGER;
    }
}
