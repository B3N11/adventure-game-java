package uilogic;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import exception.ui.ComponentAlreadyAtPositionException;
import exception.ui.UIHandlerAlreadyStartedException;
import game.behaviour.abstracts.Entity;
import game.behaviour.interfaces.IInteractiveEntity;
import game.global.GameHandler;
import ui.elements.GridEntityComponent;
import ui.elements.PlayFrame;

public class UIHandler {

    private static UIHandler instance;

    //Button Handlers
    private GridButtonHandler gridButtonHandler;
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

    public void start() throws Exception{
        if(started)
            throw new UIHandlerAlreadyStartedException();

        createPlayFrame();
        playFrame.displayFrame();
        started = true;
    }

    private void initUIHandlers(){
        gridButtonHandler = new GridButtonHandler();
        playFrameMenuBarHandler = new PlayFrameMenuBarHandler();
        utilityButtonHandler = new UtilityButtonHandler();
    }

    public void setCurrentMapLayout(MapLayoutData data) throws Exception{
        playFrame.getPlayField().setMapLayout(data, gridButtonHandler, true);
    }

    public void placeEntity(IInteractiveEntity entity, String imagePath) throws ArgumentNullException, InvalidArgumentException, ComponentAlreadyAtPositionException, IOException{
        var component = new GridEntityComponent(entity.getInstanceID(),
            playFrame.getPlayField().getComponentSize().getHorizontal(),
            playFrame.getPlayField().getComponentSize().getVertical(),
            entity.getPosition());

        component.setImage(imagePath);

        playFrame.getPlayField().addEntity(component);
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
    }
}