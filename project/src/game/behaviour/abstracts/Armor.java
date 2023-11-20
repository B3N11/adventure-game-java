package game.behaviour.abstracts;

public class Armor extends Equipment{
 
    protected int armorClass;
    protected int movementBonus;

    public Armor(int armorClass, int movementBonus){
        setArmorClass(armorClass);
        setMovementBonus(movementBonus);
    }

    public int getArmorClass(){ return armorClass; }
    public int getMovementBonus(){ return movementBonus; }

    public void setArmorClass(int armorClass){
        this.armorClass = armorClass;
    }

    public void setMovementBonus(int movementBonus){
        this.movementBonus = movementBonus;
    }

    @Override
    public String getDisplayInfo() {
        return getDescription();
    }

    @Override
    public String getStatistics(int bearerLevel) {
        String result = "Armor Class: " + armorClass + " + " + bearerLevel + "\n" + "Movement Bonus: " + movementBonus;
        return result;
    }
}