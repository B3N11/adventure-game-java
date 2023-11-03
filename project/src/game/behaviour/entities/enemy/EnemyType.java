package game.behaviour.entities.enemy;

import exception.general.ArgumentNullException;
import game.behaviour.abstracts.EnemyBehaviourController;
import game.utility.general.Identifiable;

public class EnemyType extends Identifiable{
    
    private EnemyBehaviourController controller;

    public EnemyType(String id, EnemyBehaviourController enemyController) throws ArgumentNullException{
        if(enemyController == null)
            throw new ArgumentNullException();
        
        setID(id);
        controller = enemyController;
    }

    public EnemyBehaviourController getController() { return controller; }
    public EnemyEntity getEntity() { return controller.getEnemyEntity(); }
}
