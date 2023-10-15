package ui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import exception.general.ArgumentNullException;
import exception.general.InvalidArgumentException;
import exception.ui.ComponentAlreadyAtPositionException;
import uilogic.GridButtonHandler;
import uilogic.MapLayoutData;
import ui.data.GridDimension;
import ui.handlers.GridEntityComponentHandler;

//Manages UI related to the area where the game is displayed
public class PlayfieldPanel extends JLayeredPane{
    
    private JLabel background;
    private GridPanel entityPanel;
    private GridPanel buttonPanel;

    private GridEntityComponentHandler entityHandler;

    private static GridDimension preferredSize = new GridDimension(1000, 563);
    private static GridDimension componentSize;

    public PlayfieldPanel(MapLayoutData layoutData, GridButtonHandler handler) throws Exception {
        if(layoutData == null || handler == null)
            throw new ArgumentNullException();

        initPlayfield(layoutData, handler);
    }

    public GridDimension getPreferredSize(){ return preferredSize; }
    public GridDimension getComponentSize(){ return componentSize; }

    //#region INITIALIZE
    private void initPlayfield(MapLayoutData layoutData, GridButtonHandler handler) throws Exception{
        //Set size
        setPreferredSize(preferredSize);
        setBounds(0, 0, (int)preferredSize.getWidth(), (int)preferredSize.getHeight());

        //Set componet size
        setComponentSize(layoutData.getHorizontal(), layoutData.getVertical());

        //Create the 3 layers
        initBackground(preferredSize.getHorizontal(), preferredSize.getVertical(), layoutData.getFilePath());
        initEntityPanel(layoutData.getHorizontal(), layoutData.getVertical());
        initButtonPanel(layoutData.getHorizontal(), layoutData.getVertical(), handler);

        //Add layers
        add(background, Integer.valueOf(0));
        add(entityPanel.getJPanel(), Integer.valueOf(1));
        add(buttonPanel.getJPanel(), Integer.valueOf(2));

        entityHandler = new GridEntityComponentHandler();
    }

    private void initBackground(int x, int y, String file) throws Exception{
        background = new GridImageComponent(x, y, file);
    }

    private void initEntityPanel(int x, int y) throws Exception{
        entityPanel = new GridPanel(preferredSize.getHorizontal(), preferredSize.getVertical());
        var gbc = new GridBagConstraints();

        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                gbc.gridx = j;
                gbc.gridy = i;
                entityPanel.add(new DummyComponent(componentSize.getHorizontal(), componentSize.getVertical(), gbc), gbc, false);
            }
        }
    }

    private void initButtonPanel(int x, int y, GridButtonHandler handler) throws Exception{
        buttonPanel = new GridPanel(preferredSize.getHorizontal(), preferredSize.getVertical());
        var gbc = new GridBagConstraints();
        var color = new Color(255, 0, 0, 50);

        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                gbc.gridx = j;
                gbc.gridy = i;
                buttonPanel.add(new GridButton(componentSize.getHorizontal(), componentSize.getVertical(), color, gbc, handler), gbc, false);
            }
        }
    }

    private void setComponentSize(int x, int y){
        if(componentSize != null)
            return;
        
        int xComponentSize = preferredSize.getHorizontal() / x;
        int yComponentSize = preferredSize.getVertical() / y;

        componentSize = new GridDimension(xComponentSize, yComponentSize);
    }
    //#endregion

    public GridEntityComponent addEntity(GridEntityComponent entity) throws ArgumentNullException, InvalidArgumentException, ComponentAlreadyAtPositionException{
        if(entity == null)
            throw new ArgumentNullException();

        entityPanel.add(entity, entity.getGridPositionAsGBC(), true);
        entityPanel.refresh();

        return entityHandler.add(entity);
    }
}