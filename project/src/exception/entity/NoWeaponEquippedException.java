package exception.entity;

public class NoWeaponEquippedException extends Exception{
    public NoWeaponEquippedException(){
        super("Entity doesn't have a weapon equipped");
    }
}