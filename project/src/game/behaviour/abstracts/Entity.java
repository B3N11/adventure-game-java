package game.behaviour.abstracts;

import java.io.Serializable;
import java.util.ArrayList;

import exception.general.InvalidArgumentException;

public abstract class Entity implements Serializable{
    
    protected int health;
    protected double movement;
    
    protected int level;
    protected int xp;
    protected int requiredXP;

    protected Armor armor;
    protected ArrayList<Weapon> weapons;

    public Entity(int health, int movement, int level) throws InvalidArgumentException{
        setHealth(health);
        setMovement(movement);
        setLevel(level);
    }

    public Entity setHealth(int health) throws InvalidArgumentException{
        if(health < 0)
            throw new InvalidArgumentException();
        
        this.health = health;
        return this;
    }

    public Entity setMovement(int movement) throws InvalidArgumentException{
        if(movement < 0)
            throw new InvalidArgumentException();

        this.movement = movement;
        return this;
    }

    public Entity setLevel(int level) throws InvalidArgumentException{
        if(level <= 0)
            throw new InvalidArgumentException();

        this.level = level;
        return this;
    }

    public int getHealth(){ return health; }
    public int getLevel(){ return level; }
    public int getXP(){ return xp; }
    public int getRequiredXP(){ return requiredXP; }
    
    public double getMovement() {
        double result = armor == null ? movement : movement + armor.getMovementBonus();
        return result;
    }

    public int getArmorClass(){
        int result = armor == null ? (level + 5) : armor.getArmorClass() + level;
        return result;
    }

    public int addXP(int newXP){
        int leftoverXP = newXP - (requiredXP - xp);

        if(leftoverXP >= 0)
            levelUp(leftoverXP);
        else
            xp += newXP;

        return level;
    }

    private void levelUp(int leftoverXP){
        levelUp();
        xp = leftoverXP;
    }

    public void levelUp(){
        level++;
        requiredXP = level * 150;
    }
}
