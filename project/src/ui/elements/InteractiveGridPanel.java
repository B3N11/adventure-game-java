package ui.elements;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import exception.general.ArgumentNullException;
import exception.general.ElementNotFoundException;
import exception.general.InvalidArgumentException;
import exception.ui.ComponentAlreadyAtPositionException;
import exception.ui.PlayfieldNotEmptyException;
import file.elements.MapLayoutData;
import uilogic.GridEntityComponentHandler;
import ui.data.GridDimension;
import ui.data.GridPosition;

//Manages UI related to the area where the game is displayed
public class InteractiveGridPanel extends JPanel{
    
    //Inner layout
    private JLayeredPane layeredPane;

    //Layers
    private JLabel background;
    private GridPanel entityPanel;
    private GridPanel buttonPanel;

    //Entity Handler
    private GridEntityComponentHandler entityHandler;

    private GridDimension preferredSize;
    private GridDimension componentSize;

    public InteractiveGridPanel(int width, int height) throws Exception {
        initInteractiveGrid(width, height);
    }

    public GridDimension getPreferredSize(){ return preferredSize; }
    public GridDimension getComponentSize(){ return componentSize; }

    //#region INITIALIZE
    private void initInteractiveGrid(int width, int height) throws Exception{        
        //Set panel
        initPanel(width, height);

        var defaultMapLayout = new MapLayoutData("default-layout-000", 20, 11, null, new GridPosition(0,0));

        //Set componet size
        setComponentSize(calculateAutoComponentSize(defaultMapLayout.getHorizontal(), defaultMapLayout.getVertical()));

        //Create the 3 layers
        initBackground(preferredSize.getHorizontal(), preferredSize.getVertical(), defaultMapLayout.getBackgroundFilePath());
        initEntityPanel(defaultMapLayout.getHorizontal(), defaultMapLayout.getVertical());
        initButtonPanel(defaultMapLayout.getHorizontal(), defaultMapLayout.getVertical(), null);

        //Add layers
        setLayers();
        entityHandler = new GridEntityComponentHandler();
    }

    private void initPanel(int width, int height){
        preferredSize = new GridDimension(width, height);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(preferredSize);
        
        setPreferredSize(preferredSize);
        setBounds(0, 0, preferredSize.getHorizontal(), preferredSize.getVertical());
        
        add(layeredPane);
    }

    private void initBackground(int width, int height, String file) throws Exception{

        //If file is not defined, we create an empty background
        if(file == null)
            background = new ImageComponent(width, height);
        else
            background = new ImageComponent(width, height, file);
    }

    private void initEntityPanel(int width, int height) throws Exception{
        entityPanel = new GridPanel(preferredSize.getHorizontal(), preferredSize.getVertical(), width, height);

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                var position = new GridPosition(j, i);
                entityPanel.add(new DummyComponent(componentSize.getHorizontal(), componentSize.getVertical(), position), position, false, false);
            }
        }
    }

    private void initButtonPanel(int width, int height, ActionListener handler) throws Exception{
        buttonPanel = new GridPanel(preferredSize.getHorizontal(), preferredSize.getVertical(), width, height);
        var color = new Color(255, 0, 0, 50);

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                var position = new GridPosition(j, i);
                buttonPanel.add(new GridButton(componentSize.getHorizontal(), componentSize.getVertical(), color, position, handler), position, false, false);
            }
        }
    }

    private void setLayers(){
        layeredPane.removeAll();

        layeredPane.add(background, Integer.valueOf(0));
        layeredPane.add(entityPanel.getJPanel(), Integer.valueOf(1));
        layeredPane.add(buttonPanel.getJPanel(), Integer.valueOf(2));
    }

    private GridDimension calculateAutoComponentSize(int x, int y){        
        int xComponentSize = preferredSize.getHorizontal() / x;
        int yComponentSize = preferredSize.getVertical() / y;

        return new GridDimension(xComponentSize, yComponentSize);
    }

    private void setComponentSize(GridDimension size) throws ArgumentNullException{
        if(size == null)
            throw new ArgumentNullException();
        componentSize = size;
    }
    //#endregion

    public InteractiveGridPanel setMapLayout(MapLayoutData data, ActionListener buttonHandler, boolean force) throws Exception{
        if(data == null || buttonHandler == null)
            throw new ArgumentNullException();

        if(!entityHandler.isEmpty() && !force)
            throw new PlayfieldNotEmptyException();

        var size = calculateAutoComponentSize(data.getHorizontal(), data.getVertical());
        
        return setMapLayout(data, buttonHandler, force, size);
    }

    public InteractiveGridPanel setMapLayout(MapLayoutData data, ActionListener buttonHandler, boolean force, GridDimension componentSize) throws Exception{
        if(data == null || buttonHandler == null)
            throw new ArgumentNullException();

        if(!entityHandler.isEmpty() && !force)
            throw new PlayfieldNotEmptyException();

        entityHandler.clear();

        setComponentSize(componentSize);
        
        initEntityPanel(data.getHorizontal(), data.getVertical());
        initButtonPanel(data.getHorizontal(), data.getVertical(), buttonHandler);
        initBackground(preferredSize.getHorizontal(), preferredSize.getVertical(), data.getBackgroundFilePath());
        
        setLayers();
        return this;
    }

    public GridEntityComponent addEntity(GridEntityComponent entity) throws ArgumentNullException, InvalidArgumentException, ComponentAlreadyAtPositionException{
        if(entity == null)
            throw new ArgumentNullException();

        entityPanel.add(entity, entity.getGridPosition(), true, false);
        entityPanel.refresh();

        return entityHandler.add(entity);
    }

    public GridEntityComponent getEntity(String id) throws ArgumentNullException, ElementNotFoundException{
        return entityHandler.getByID(id);
    }

    public GridEntityComponent removeEntity(String id, boolean removeFromList) throws ArgumentNullException, ElementNotFoundException, InvalidArgumentException{
        if(id == null)
            throw new ArgumentNullException();

        var entity = entityHandler.getByID(id);

        return removeEntity(entity, removeFromList);
    }

    public GridEntityComponent removeEntity(GridEntityComponent entity, boolean removeFromList) throws ArgumentNullException, ElementNotFoundException, InvalidArgumentException{
        if(entity == null)
            throw new ArgumentNullException();

        //Remove if requested
        if(removeFromList)
            entityHandler.remove(entity);

        var newComponent = new DummyComponent(componentSize.getHorizontal(), componentSize.getVertical(), entity.getGridPosition());
        try{ entityPanel.add(newComponent, entity.getGridPosition(), true, false); }
        catch(ComponentAlreadyAtPositionException e){}

        return entity;
    }

    public GridEntityComponent replaceEntity(String id, GridPosition newPosition) throws ArgumentNullException, ElementNotFoundException, InvalidArgumentException, ComponentAlreadyAtPositionException{
        if(id == null || newPosition == null)
            throw new ArgumentNullException();

        var entity = entityHandler.getByID(id);
        return replaceEntity(entity, newPosition);
    }

    public GridEntityComponent replaceEntity(GridEntityComponent entity, GridPosition newPosition) throws ArgumentNullException, ElementNotFoundException, InvalidArgumentException, ComponentAlreadyAtPositionException{
        if(entity == null || newPosition == null)
            throw new ArgumentNullException();

        removeEntity(entity, false);
        entity.setGridPosition(newPosition);
        entityPanel.add(entity, entity.getGridPosition(), true, false);

        return entity;
    }

    public String getEntityByPosition(GridPosition position) throws ArgumentNullException, ElementNotFoundException{
        if(position == null)
            throw new ArgumentNullException();

        var entity = entityHandler.getByPosition(position);
        return entity.getID();
    }
}