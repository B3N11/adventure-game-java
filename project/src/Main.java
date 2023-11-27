import game.global.GameHandler;
import game.utility.DiceRoller;
import game.utility.GenericDelegate;
import uilogic.UIHandler;

public class Main {

    public static void main(String[] args) throws Exception{
        DiceRoller.getInstance().setDefault(20);
        DiceRoller.getInstance().setDelegate(new GenericDelegate() {
            public void run(Object o){ UIHandler.getInstance().displayDiceRollResult((Integer)o); }
        });
        GameHandler.getInstance().start();
        //DataCreator.createTestData();
    }
}