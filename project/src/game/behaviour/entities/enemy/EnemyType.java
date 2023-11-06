package game.behaviour.entities.enemy;

import exception.general.ArgumentNullException;
import game.behaviour.abstracts.EnemyBehaviourController;
import game.utility.general.Identifiable;

public class EnemyType extends Identifiable{
    
    private EnemyBehaviourController controller;
    private String iconFilePath;

    public EnemyType(String id, EnemyBehaviourController enemyController, String iconFilePath) throws ArgumentNullException{
        if(enemyController == null || iconFilePath == null)
            throw new ArgumentNullException();
        
        setID(id);
        controller = enemyController;
        this.iconFilePath = iconFilePath;
    }

    public EnemyBehaviourController getController() { return controller; }
    public EnemyEntity getEntity() { return controller.getEnemyEntity(); }
    public String getIconFilePath() { return iconFilePath; }
}
