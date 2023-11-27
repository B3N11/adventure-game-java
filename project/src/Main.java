import game.global.GameHandler;
import game.utility.delegates.GenericDelegate;
import game.utility.dice.DiceRoller;
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