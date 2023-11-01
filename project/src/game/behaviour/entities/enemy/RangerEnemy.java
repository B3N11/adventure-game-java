package game.behaviour.entities.enemy;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.abstracts.Enemy;

public class RangerEnemy extends Enemy{

    public RangerEnemy(String id, int health, int movement, int level) throws InvalidArgumentException, ArgumentNullException {
        super(id, health, movement, level);
    }

    @Override
    public void engage(int distanceFromTarget) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'engage'");
    }    
}
