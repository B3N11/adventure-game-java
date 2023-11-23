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

    public void addEntityLog(String entityName, String message) throws ArgumentNullException{
        if(entityName == null || message == null)
            throw new ArgumentNullException();

        String result = "[" + entityName + "]: " + message + "\n";
        frame.addToCombatLog(result);
    }

    public void addPlainText(String message) throws ArgumentNullException{
        if(message == null)
            throw new ArgumentNullException();
        frame.addToCombatLog(message);
    }

    public void addMapDescription(String mapName, String description) throws ArgumentNullException{
        if(mapName == null || description == null)
            throw new ArgumentNullException();

        String result = "## " + mapName + " ##\n" + description + "\n";
        frame.clearCombatLog();
        frame.addToCombatLog(result);
    }
}
