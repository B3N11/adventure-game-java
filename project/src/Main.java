import game.global.DiceRoller;
import game.global.GameHandler;
import game.global.UIHandler;
import game.utility.GenericDelegate;

public class Main {

    public static void main(String[] args) throws Exception{
        DiceRoller.getInstance().setDefault(20);
        
        GameHandler.getInstance().start();
        //DataCreator.createTestData();
    }
}