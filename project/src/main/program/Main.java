package main.program;

import main.game.global.DiceRoller;
import main.game.global.GameHandler;

public class Main {

    private static boolean run = true;

    public static void main(String[] args) throws Exception{
        DiceRoller.getInstance().setDefault(20);
        
        if(run)
            GameHandler.getInstance().start();
        else
            DataCreator.createTestData();
    }
}