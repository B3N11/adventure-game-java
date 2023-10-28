package game.behaviour.abstracts;

import java.util.ArrayList;

import exception.general.InvalidArgumentException;

public abstract class Entity {
    
    private int health;
    private double speed;

    private Armor armor;
    private ArrayList<Weapon> weapons;

    public Entity(int health, int speed) throws InvalidArgumentException{
        setHealth(health);
        setSpeed(speed);
    }

    public Entity setHealth(int health) throws InvalidArgumentException{
        if(health < 0)
            throw new InvalidArgumentException();
        
        this.health = health;
        return this;
    }

    public Entity setSpeed(int speed) throws InvalidArgumentException{
        if(speed < 0)
            throw new InvalidArgumentException();

        this.speed = speed;
        return this;
    }
}
