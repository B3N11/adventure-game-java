package game.behaviour.abstracts;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;

public abstract class Enemy extends Entity{
    
    protected Entity target;

    public Enemy(String id, int health, int movement, int level) throws InvalidArgumentException, ArgumentNullException{
        super(id, health, movement, level);
    }

    public Entity getTarget(){return target;}

    public void setTarget(Entity target){
        this.target = target;
    }

    public abstract void engage(int distanceFromTarget);
}