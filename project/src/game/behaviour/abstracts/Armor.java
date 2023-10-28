package game.behaviour.abstracts;

public class Armor extends Equipment{
 
    protected int armorClass;
    protected int speedBonus;

    public Armor(int armorClass, int speedBonus){
        setArmorClass(armorClass);
        setSpeedBonus(speedBonus);
    }

    public int getArmorClass(){ return armorClass; }
    public int getSpeedBonus(){ return speedBonus; }

    public void setArmorClass(int armorClass){
        this.armorClass = armorClass;
    }

    public void setSpeedBonus(int speedBonus){
        this.speedBonus = speedBonus;
    }
}