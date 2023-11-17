package uilogic;

import exception.general.ArgumentNullException;
import ui.elements.PlayFrame;

public class CombatLogger {
    
    private PlayFrame frame;

    public CombatLogger(PlayFrame frame){
        this.frame = frame;
    }

    public void addSystemLog(String message) throws ArgumentNullException{
        if(message == null)
            throw new ArgumentNullException();

        String result = "[SYSTEM]: " + message + "\n";
        frame.addToCombatLog(result);
    }
}
