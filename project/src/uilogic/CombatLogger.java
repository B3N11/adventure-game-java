package uilogic;

import exception.general.ArgumentNullException;
import ui.elements.PlayFrame;

/**
 * This class handles the logging of combat in the UI.
 * It contains a PlayFrame for displaying the combat log.
 * 
 * The class contains the following field:
 * - frame: The PlayFrame that this class uses to display the combat log.
 */
public class CombatLogger {
    
    private PlayFrame frame;

    /**
     * Constructor for the CombatLogger class.
     * Initializes the CombatLogger with a PlayFrame.
     * @param frame The PlayFrame that this class uses to display the combat log.
     */
    public CombatLogger(PlayFrame frame){
        this.frame = frame;
    }

    /**
     * Adds a system log to the combat log.
     * Prepends "[SYSTEM]: " to the message and adds it to the combat log.
     * @param message The message to add to the combat log.
     * @throws ArgumentNullException if the message is null.
     */
    public void addSystemLog(String message) throws ArgumentNullException{
        if(message == null)
            throw new ArgumentNullException();

        String result = "[SYSTEM]: " + message + "\n";
        frame.addToCombatLog(result);
    }

    /**
     * Adds an entity log to the combat log.
     * Prepends the entity name in brackets and a colon to the message and adds it to the combat log.
     * @param entityName The name of the entity.
     * @param message The message to add to the combat log.
     * @throws ArgumentNullException if the entity name or the message is null.
     */
    public void addEntityLog(String entityName, String message) throws ArgumentNullException{
        if(entityName == null || message == null)
            throw new ArgumentNullException();

        String result = "[" + entityName + "]: " + message + "\n";
        frame.addToCombatLog(result);
    }

    /**
     * Adds plain text to the combat log.
     * @param message The text to add to the combat log.
     * @throws ArgumentNullException if the message is null.
     */
    public void addPlainText(String message) throws ArgumentNullException{
        if(message == null)
            throw new ArgumentNullException();
        frame.addToCombatLog(message);
    }

    /**
     * Adds a map description to the combat log.
     * @param mapName The name of the map.
     * @param description The description of the map.
     * @throws ArgumentNullException if the map name or the description is null.
     */
    public void addMapDescription(String mapName, String description) throws ArgumentNullException{
        if(mapName == null || description == null)
            throw new ArgumentNullException();

        String result = "## " + mapName + " ##\n" + description + "\n";
        frame.clearCombatLog();
        frame.addToCombatLog(result);
    }
}