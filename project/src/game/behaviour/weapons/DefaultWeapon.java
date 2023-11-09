package game.behaviour.weapons;

import exception.dice.InvalidDiceSideCountException;
import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.behaviour.abstracts.Weapon;

public class DefaultWeapon extends Weapon{

    protected DefaultWeapon() throws ArgumentNullException, InvalidDiceSideCountException, InvalidArgumentException {
        super("default-weapon-000", "Unarmed");

        setAttackModifier(1);
        setDiceCount(1);
        setDamageDice(4);
        setDamageModifier(0);
        setRange(1);

        setDescription("If no weapon is at hand, your best option is...your hand.");
    }    
}