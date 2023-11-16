package uilogic;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import exception.general.ArgumentNullException;
import exception.ui.UIHandlerAlreadyStartedException;
import game.global.GameHandler;
import ui.elements.PlayFrame;

public class UIHandler {

    private static UIHandler instance;

    private PlayFieldHandler playFieldHandler;

    //Button Handler
    private PlayFrameMenuBarHandler playFrameMenuBarHandler;
    private UtilityButtonHandler utilityButtonHandler;
    private InteractButtonHandler interactButtonHandler;

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

    public void start() throws Exception{
        if(started)
            throw new UIHandlerAlreadyStartedException();

        createPlayFrame();
        playFrame.displayFrame();
        started = true;
    }

    private void initUIHandlers(){
        playFrameMenuBarHandler = new PlayFrameMenuBarHandler();
        utilityButtonHandler = new UtilityButtonHandler();
        playFieldHandler = new PlayFieldHandler(null);
    }

    public void openFileDialog(FileChooserType type){
        var fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a file");
        fileChooser.setFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt", "text"));

        int response = fileChooser.showOpenDialog(null);

        if(response == JFileChooser.APPROVE_OPTION){
            var result = fileChooser.getSelectedFile().getAbsolutePath();
            
            try{GameHandler.getInstance().handleChosenFile(result, type);}
            catch(Exception e){
                showErrorMessage("Please select a valid file!");
            };
        }
    }

    public void addToCombatLog(String text){
        playFrame.addToCombatLog(text);
    }

    public void showErrorMessage(String message){
        JOptionPane.showMessageDialog(null, message,"Error!", JOptionPane.ERROR_MESSAGE);
    }

    private void createPlayFrame() throws Exception{
        playFrame = new PlayFrame(playFrameMenuBarHandler, utilityButtonHandler, interactButtonHandler);
        playFieldHandler.setPlayField(playFrame.getPlayField());
    }

    public void togglePlayerControlls(boolean on){
        //Turn on or off the player controlls while enemies are doing their turns
    }
}