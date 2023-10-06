package game.behaviour.weapons;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import exception.dice.DefaultDiceNotSetException;
import exception.dice.InvalidDiceSideCountException;
import game.behaviour.abstracts.Weapon;

public class Shotgun extends Weapon{

    private double hitRange;
    
    public Shotgun(String id, String weaponName, int attackModifier, int dice, int diceCount, int dmgModifier, int range, double hitRange) throws ArgumentNullException, InvalidArgumentException, InvalidDiceSideCountException{
        super(id, weaponName, attackModifier, dice, diceCount, dmgModifier, range);
        weaponType = WeaponType.SHOTGUN;

        setHitRange(hitRange);
    }
    
    public double getHitRange(){ return hitRange; }

    public void setHitRange(double newHitRange) throws InvalidArgumentException{
        if(newHitRange < 0 || newHitRange > 1)
            throw new InvalidArgumentException();
        
        hitRange = newHitRange;
    }

    @Override
    public boolean attack(int armorClass, int distance) throws DefaultDiceNotSetException{

        //Shotgun customisation: Garanteed Hit
        if(distance < (range * hitRange))
            return true;

        return super.attack(armorClass, distance);
    }
}