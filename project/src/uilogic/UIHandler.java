package uilogic;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import exception.general.ArgumentNullException;
import exception.general.ConfigNotLoadedException;
import exception.ui.UIHandlerAlreadyStartedException;
import game.global.GameHandler;
import ui.elements.PlayFrame;
import ui.elements.PlayerDeathFrame;

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

    public void start() throws Exception{
        if(started)
            throw new UIHandlerAlreadyStartedException();

        createPlayFrame();
        playFrame.displayFrame();
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

    public void refreshUI(){
        playFrame.refresh();
    }

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

    public void showMessage(String message, int messageType){
        JOptionPane.showMessageDialog(null, message, "Message", messageType);
    }

    private void createPlayFrame() throws Exception{
        playFrame = new PlayFrame(playFrameMenuBarHandler, utilityButtonHandler, interactButtonHandler, new WindowAdapter() {
            public void windowClosing(WindowEvent e){ GameHandler.getInstance().quitGame(false); }
        });
        playFieldHandler.setPanel(playFrame.getPlayField());
    }

    public void togglePlayerControlls(boolean on){
        //Turn on or off the player controlls while enemies are doing their turns
    }

    public void displayDiceRollResult(Integer roll){
        String message = "Rolled: " + roll;

        try{ logger.addPlainText(message); }
        catch(ArgumentNullException e){ /*Wont happen*/}
    }

    public void displayPlayerDeath(){
        try{
            var deathFrame = new PlayerDeathFrame(playFrame, playerDeathFrameHandler);
            deathFrame.setVisible(true);
        }catch(ArgumentNullException e){}
    }

    public void displayCharacterFrame(){
        try{ characterFrameHandler.start(); }
        catch(ConfigNotLoadedException e){
            showMessage("Please load a game configuration file first!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void displayTravelFrame(){
        travelHandler.start();
    }
}