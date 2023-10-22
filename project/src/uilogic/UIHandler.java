package uilogic;

import exception.ui.UIHandlerAlreadyStartedException;
import ui.elements.PlayFrame;

public class UIHandler {

    //Button Handlers
    private GridButtonHandler gridButtonHandler;
    private PlayFrameMenuBarHandler playFrameMenuBarHandler;

    private PlayFrame playFrame;

    //Logic
    private boolean started = false;

    public UIHandler(){
        initUIHandlers();
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
    }

    private void createPlayFrame() throws Exception{
        playFrame = new PlayFrame(playFrameMenuBarHandler);
    }
}