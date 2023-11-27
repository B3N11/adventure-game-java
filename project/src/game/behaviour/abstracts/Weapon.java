package game.behaviour.abstracts;

import exception.dice.DefaultDiceNotSetException;
import exception.dice.InvalidDiceSideCountException;
import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import game.enums.EquipmentType;
import game.enums.WeaponType;
import game.utility.dice.DiceRoller;

/**
 * This abstract class represents a Weapon in the game.
 * It extends the Equipment class and includes additional properties such as attack modifier, damage dice, dice count, damage modifier, range, weapon type, and attacks in round.
 * 
 * The class contains the following fields:
 * - attackModifier: The bonus value that is added to the default attack roll.
 * - damageDice: Represents the sideCount of the damage dice it rolls with.
 * - diceCount: The number of dice that is rolled for damage.
 * - damageModifier: The bonus value that is added to the rolled damage.
 * - range: The maximum distance the weapon can deal damage in.
 * - weaponType: The type of the weapon, used for type casting.
 * - attacksInRound: The number of attacks in a round.
 * 
 * The class provides getter and setter methods for these fields.
 */
public abstract class Weapon extends Equipment{
    
    protected int attackModifier;              //The bonus value that is added to the default attack roll
    protected int damageDice;                      //Represents the sideCount of the damage dice it rolls with
    protected int diceCount;                       //The number of dice that is rolled for damage
    protected int damageModifier;                  //The bonus value that is added to the rolled damage

    protected double range;                        //The maximum distance the weapon can deal damage in
    protected WeaponType weaponType;               //The type of the weapon, used for type casting

    protected int attacksInRound;

    protected Weapon(String id, String weaponName) throws ArgumentNullException{
        setID(id);
        setName(weaponName);
        setDescription("");
        try{ setAttacksInRound(1); }
        catch(InvalidArgumentException e){}
        
        equipmentType = EquipmentType.WEAPON;
    }

    //#region GET/SET methods

    public Weapon setAttackModifier(int modifier){
        attackModifier = modifier;

        return this;
    }

    public Weapon setDamageDice(int sides) throws InvalidDiceSideCountException{
        //Check for invalid side
        if(sides < 1)
            throw new InvalidDiceSideCountException();
        
        damageDice = sides;
        return this;
    }

    public Weapon setDiceCount(int count) throws InvalidDiceSideCountException{
        //Check for invalid side
        if(count < 0)
            throw new InvalidDiceSideCountException();
        
        diceCount = count;
        return this;
    }

    public Weapon setDamageModifier(int modifier){
        damageModifier = modifier;
        return this;
    }

    public Weapon setRange(int newRange) throws InvalidArgumentException{
        //Check for invalid range
        if(newRange < 0)
            throw new  InvalidArgumentException();

        this.range = newRange;
        return this;
    }

    public Weapon setAttacksInRound(int amount) throws InvalidArgumentException{
        if(amount < 0)
            throw new InvalidArgumentException();
        attacksInRound = amount;
        return this;
    }

    @Override
    public Weapon setName(String newName) throws ArgumentNullException{
        super.setName(newName);
        return this;
    }

    @Override
    public Weapon setDescription(String newDescription) throws ArgumentNullException{
        super.setDescription(newDescription);
        return this;
    }

    public int getDamageDice() { return damageDice; }
    public int getAttackModifier() { return attackModifier; }
    public int getDamageDiceCount() { return diceCount; }
    public int getDamageModifier() { return damageModifier; }
    public int getAttacksInRound() { return attacksInRound; }
    public double getRange() { return range; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public WeaponType getWeaponType() { return weaponType; }
    //#endregion

    @Override
    public String getDisplayInfo(){
        return getDescription();
    }

    @Override
    public String getStatistics(int bearerLevel){
        String result = "Hit DC: 1d" + DiceRoller.getInstance().getDefault() + " + " + attackModifier +
                        "\nAttack DC: " + diceCount + "d" + damageDice + " + " + (damageModifier + bearerLevel) +
                        "\nRange: " + range;
        return result;
    }

    /**
     * Checks if the weapon can attack to the specified distance.
     * @param distance The distance to check.
     * @return true if the weapon can attack to the distance, false otherwise.
     */
    public boolean checkRange(double distance){
        return range >= distance;
    }

    /**
     * Performs an attack roll with default dice and checks if it succeeds against the target's armor class.
     * Also checks if the target is within the weapon's range.
     * @param targetAC The armor class of the target.
     * @param distance The distance to the target.
     * @return true if the attack hits and the target is within range, false otherwise.
     * @throws DefaultDiceNotSetException if the default dice is not set.
     */
    public boolean attack(int targetAC, double distance) throws DefaultDiceNotSetException{
        var roller = DiceRoller.getInstance();

        //Check if the rolled value succeeds armorClass
        boolean hit = roller.rollDefault(attackModifier) >= targetAC;
        boolean inRange = checkRange(distance);
        return inRange && hit;
    }

    /**
     * Calculates the damage dealt by the weapon.
     * Rolls the weapon's damage dice and adds the damage modifier.
     * @param distance The distance to the target.
     * @return The calculated damage.
     * @throws InvalidDiceSideCountException if the side count of the damage dice is invalid.
     */
    public int damage(double distance) throws InvalidDiceSideCountException{
        var roller = DiceRoller.getInstance();
        
        //Only throws exception if damageDice is not set
        return roller.rollDice(damageDice, diceCount, 0) + damageModifier;
    }
}