package game.global;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import exception.general.ArgumentNullException;
import exception.general.ConfigNotLoadedException;
import exception.ui.UIHandlerAlreadyStartedException;
import ui.elements.PlayFrame;
import ui.elements.PlayerDeathFrame;
import uilogic.CharacterFrameHandler;
import uilogic.CombatLogger;
import uilogic.FileChooserType;
import uilogic.InteractButtonHandler;
import uilogic.PlayFieldHandler;
import uilogic.PlayFrameMenuBarHandler;
import uilogic.PlayerDeathFrameHandler;
import uilogic.TravelFrameHandler;
import uilogic.UtilityButtonHandler;

/**
 * This class handles the user interface of the game.
 * It contains handlers for the play field, combat logger, and various buttons.
 * 
 * The class contains the following fields:
 * - instance: The singleton instance of the UIHandler class.
 * - playFieldHandler: The handler for the play field.
 * - logger: The combat logger.
 * - playFrameMenuBarHandler: The handler for the play frame menu bar.
 * - utilityButtonHandler: The handler for the utility buttons.
 * - interactButtonHandler: The handler for the interact buttons.
 * - playerDeathFrameHandler: The handler for the player death frame.
 * - characterFrameHandler: The handler for the character frame.
 * - travelHandler: The handler for travel.
 */
public class UIHandler {

    private static UIHandler instance;

    private PlayFieldHandler playFieldHandler;
    private CombatLogger logger;

    //Button Handler
    private PlayFrameMenuBarHandler playFrameMenuBarHandler;
    private UtilityButtonHandler utilityButtonHandler;
    private InteractButtonHandler interactButtonHandler;
    private PlayerDeathFrameHandler playerDeathFrameHandler;
    private CharacterFrameHandler characterFrameHandler;
    private TravelFrameHandler travelHandler;

    private PlayFrame playFrame;

    //Logic
    private boolean started;

    private UIHandler(){
        started = false;
        initUIHandlers();
    }

    public static UIHandler getInstance(){
        if(instance == null)
            instance = new UIHandler();
        return instance;
    }

    public PlayFieldHandler getPlayFieldHandler() { return playFieldHandler; }
    public CombatLogger getCombatLogger() { return logger; }

    /**
     * Starts the UIHandler. Sets up the main UI frame.
     * Initializes the UI and displays the main menu.
     */
    public void start() throws Exception{
        if(started)
            throw new UIHandlerAlreadyStartedException();

        createPlayFrame();
        playFrame.setVisible(true);
        logger = new CombatLogger(playFrame);
        started = true;
    }

    private void initUIHandlers(){
        playFrameMenuBarHandler = new PlayFrameMenuBarHandler();
        utilityButtonHandler = new UtilityButtonHandler();
        interactButtonHandler = new InteractButtonHandler();
        playerDeathFrameHandler = new PlayerDeathFrameHandler();
        characterFrameHandler = new CharacterFrameHandler();
        travelHandler = new TravelFrameHandler();
        playFieldHandler = new PlayFieldHandler(null);
    }

    /**
     * Refreshes the UI.
     * Updates the UI to reflect the current game state.
     */
    public void refreshUI(){
        playFrame.refresh();
    }

    /**
     * Opens a file dialog for text files.
     * Allows the user to choose a file to load.
     */
    public void openFileDialog(FileChooserType type){
        var fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a file");
        fileChooser.setFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt", "text"));
        fileChooser.setCurrentDirectory(new File("G:\\uni\\sub\\3\\prog\\hf\\adventure-game-java\\project\\resources\\gamedata"));

        int response = fileChooser.showOpenDialog(null);

        if(response == JFileChooser.APPROVE_OPTION){
            var result = fileChooser.getSelectedFile().getAbsolutePath();
            
            try{GameHandler.getInstance().handleChosenFile(result, type);}
            catch(Exception e){
                showMessage("Please select a valid file!", JOptionPane.ERROR_MESSAGE);
            };
        }
    }

    /**
     * Shows a message.
     * Displays a message to the user.
     * @param message The message to display.
     * @param messageType The type of the message. Use the JOptionPane constants.
     */
    public void showMessage(String message, int messageType){
        JOptionPane.showMessageDialog(null, message, "Message", messageType);
    }

    private void createPlayFrame() throws Exception{
        playFrame = new PlayFrame(playFrameMenuBarHandler, utilityButtonHandler, interactButtonHandler, new WindowAdapter() {
            public void windowClosing(WindowEvent e){ GameHandler.getInstance().quitGame(false); }
        });
        playFieldHandler.setPanel(playFrame.getPlayField());
    }

    /**
     * Toggles the player controls.
     * Enables or disables the player controls.
     * @param on Whether to enable the player controls.
     */
    public void togglePlayerControlls(boolean on){
        playFrame.togglePlayerControlls(on);
    }

    /**
     * Displays the dice roll result. Acts as a callback for the dice roller.
     * Logs the result of a dice roll.
     * @param roll The result of the dice roll.
     */
    public void displayDiceRollResult(Integer roll){
        String message = "Rolled: " + roll;

        try{ logger.addPlainText(message); }
        catch(ArgumentNullException e){ /*Wont happen*/}
    }

    /**
     * Displays the player death screen. Prompts the player to restart or quit the game.
     * Shows a screen indicating the player has died.
     */
    public void displayPlayerDeath(){
        try{
            var deathFrame = new PlayerDeathFrame(playFrame, playerDeathFrameHandler);
            deathFrame.setVisible(true);
        }catch(ArgumentNullException e){}
    }

    /*
     * Displays the character frame. Allows the player to view their character. And modify their equipment.
     */
    public void displayCharacterFrame(){
        try{ characterFrameHandler.start(); }
        catch(ConfigNotLoadedException e){
            showMessage("Please load a game configuration file first!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays the travel frame. Allows the player to travel to a different map.
     * Shows a frame allowing the player to travel.
     */
    public void displayTravelFrame(){
        travelHandler.start();
    }
}