package ui.handlers;

import ui.elements.PlayFrame;
import uilogic.GridButtonHandler;
import uilogic.PlayFrameMenuBarHandler;

public class UIHandler {

    //Button Handlers
    private GridButtonHandler gridButtonHandler;
    private PlayFrameMenuBarHandler playFrameMenuBarHandler;

    private PlayFrame playFrame;

    public UIHandler(){
        initUIHandlers();
    }

    private void initUIHandlers(){
        gridButtonHandler = new GridButtonHandler();
        playFrameMenuBarHandler = new PlayFrameMenuBarHandler();
    }

    private void createPlayFrame() throws Exception{
        playFrame = new PlayFrame(playFrameMenuBarHandler);
    }

    public void start() throws Exception{
        createPlayFrame();
        playFrame.displayFrame();
    }
}
