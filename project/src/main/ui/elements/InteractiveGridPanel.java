package main.ui.elements;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import main.exception.general.ArgumentNullException;
import main.exception.general.ElementNotFoundException;
import main.exception.general.InvalidArgumentException;
import main.exception.ui.ComponentAlreadyAtPositionException;
import main.exception.ui.PlayfieldNotEmptyException;
import main.file.elements.MapLayoutData;
import main.ui.data.GridDimension;
import main.uilogic.GridEntityComponentHandler;
import main.uilogic.GridPosition;

/**
 * This class represents an interactive grid panel in the game. It has 3 layers: background, entity, and button. Background is the lowest layer displaying the background, entity is the middle layer displaying entities, and button is the top layer with GridButtons.
 * It extends the JPanel class and is used to display the game area.
 * 
 * The class contains the following fields:
 * - layeredPane: The inner layout of the interactive grid panel.
 * - background: The background of the interactive grid panel.
 * - entityPanel: The entity panel of the interactive grid panel.
 * - buttonPanel: The button panel of the interactive grid panel.
 * - entityHandler: The entity handler of the interactive grid panel.
 * - preferredSize: The preferred size of the interactive grid panel.
 * - componentSize: The component size of the interactive grid panel.
 */
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

    /**
     * Constructor for the InteractiveGridPanel class.
     * Initializes the interactive grid panel with the specified width and height.
     * @param width The width of the interactive grid panel.
     * @param height The height of the interactive grid panel.
     * @throws Exception if the initialization of the interactive grid panel throws an Exception.
     */
    public InteractiveGridPanel(int width, int height) throws Exception {
        initInteractiveGrid(width, height);
    }

    public GridDimension getPreferredSize(){ return preferredSize; }
    public GridDimension getComponentSize(){ return componentSize; }

    /**
     * Initializes the interactive grid panel.
     * Sets the panel, calculates the component size based on the default map layout, initializes the background, entity panel, and button panel, sets the layers, and initializes the entity handler.
     * @param width The width of the interactive grid panel.
     * @param height The height of the interactive grid panel.
     * @throws Exception if the initialization of the interactive grid panel throws an Exception.
     */
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

    /**
     * Initializes the panel of the interactive grid panel.
     * Creates a new layered pane, sets its preferred size, sets the preferred size and bounds of the panel, and adds the layered pane to the panel.
     * @param width The width of the panel.
     * @param height The height of the panel.
     */
    private void initPanel(int width, int height){
        preferredSize = new GridDimension(width, height);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(preferredSize);
        
        setPreferredSize(preferredSize);
        setBounds(0, 0, preferredSize.getHorizontal(), preferredSize.getVertical());
        
        add(layeredPane);
    }

    /**
     * Initializes the background of the interactive grid panel.
     * If the file is not defined, it creates an empty background; otherwise, it sets the image of the background.
     * @param width The width of the background.
     * @param height The height of the background.
     * @param file The file path of the background image.
     * @throws Exception if the initialization of the background throws an Exception.
     */
    private void initBackground(int width, int height, String file) throws Exception{

        //If file is not defined, we create an empty background
        if(file == null)
            background = new ImageComponent(width, height);
        else
            background = new ImageComponent(width, height, file);
    }

    /**
     * Initializes the entity panel of the interactive grid panel.
     * Sets the preferred size and bounds of the entity panel, and initializes the entity handler.
     * @param width The width of the entity panel.
     * @param height The height of the entity panel.
     * @throws Exception if the initialization of the entity panel throws an Exception.
     */
    private void initEntityPanel(int width, int height) throws Exception{
        entityPanel = new GridPanel(preferredSize.getHorizontal(), preferredSize.getVertical(), width, height);

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                var position = new GridPosition(j, i);
                entityPanel.add(new DummyComponent(componentSize.getHorizontal(), componentSize.getVertical(), position), position, false, false);
            }
        }
    }

    /**
     * Initializes the button panel of the interactive grid panel.
     * Sets the preferred size and bounds of the button panel, and initializes the buttons.
     * @param width The width of the button panel.
     * @param height The height of the button panel.
     * @param handler The action listener of the buttons.
     * @throws Exception if the initialization of the button panel throws an Exception.
     */
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

    /**
     * Sets the layers of the interactive grid panel.
     * Removes all components from the layered pane, and adds the background, entity panel, and button panel to it.
     */
    private void setLayers(){
        layeredPane.removeAll();

        layeredPane.add(background, Integer.valueOf(0));
        layeredPane.add(entityPanel.getJPanel(), Integer.valueOf(1));
        layeredPane.add(buttonPanel.getJPanel(), Integer.valueOf(2));
    }

    /**
     * Calculates the component size based on the preferred size and the specified x and y.
     * @param x The horizontal component count.
     * @param y The vertical component count.
     * @return The calculated component size.
     */
    private GridDimension calculateAutoComponentSize(int x, int y){        
        int xComponentSize = preferredSize.getHorizontal() / x;
        int yComponentSize = preferredSize.getVertical() / y;

        return new GridDimension(xComponentSize, yComponentSize);
    }

    /**
     * Sets the component size of the interactive grid panel.
     * @param size The new component size.
     * @throws ArgumentNullException if the size is null.
     */
    private void setComponentSize(GridDimension size) throws ArgumentNullException{
        if(size == null)
            throw new ArgumentNullException();
        componentSize = size;
    }
    
    /**
     * Sets the map layout of the interactive grid panel with auto component size.
     * If the entity handler is not empty and force is false, it throws a PlayfieldNotEmptyException; otherwise, it calculates the component size and calls the overloaded setMapLayout method.
     * @param data The new map layout data.
     * @param buttonHandler The new button handler.
     * @param force A boolean that determines whether to force the setting of the map layout.
     * @return The interactive grid panel itself, for chaining.
     * @throws Exception if the setting of the map layout throws an Exception.
     */
    public InteractiveGridPanel setMapLayout(MapLayoutData data, ActionListener buttonHandler, boolean force) throws Exception{
        if(data == null || buttonHandler == null)
            throw new ArgumentNullException();

        if(!entityHandler.isEmpty() && !force)
            throw new PlayfieldNotEmptyException();

        var size = calculateAutoComponentSize(data.getHorizontal(), data.getVertical());
        
        return setMapLayout(data, buttonHandler, force, size);
    }

    /**
     * Sets the map layout of the interactive grid panel.
     * If the entity handler is not empty and force is false, it throws a PlayfieldNotEmptyException; otherwise, it sets the component size, clears the entity handler, and initializes the entity panel and button panel.
     * @param data The new map layout data.
     * @param buttonHandler The new button handler.
     * @param force A boolean that determines whether to force the setting of the map layout.
     * @param componentSize The new component size.
     * @return The interactive grid panel itself, for chaining.
     * @throws Exception if the setting of the map layout throws an Exception.
     */
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

    /**
     * Adds an entity to the interactive grid panel.
     * @param entity The entity to add.
     * @throws ArgumentNullException if the entity is null.
     * @throws ComponentAlreadyAtPositionException if there is already a component at the position of the entity.
     */
    public GridEntityComponent addEntity(GridEntityComponent entity) throws ArgumentNullException, InvalidArgumentException, ComponentAlreadyAtPositionException{
        if(entity == null)
            throw new ArgumentNullException();

        entityPanel.add(entity, entity.getGridPosition(), true, false);
        entityPanel.refresh();

        return entityHandler.add(entity);
    }

    /**
     * Gets an entity from the interactive grid panel by its ID.
     * @param id The ID of the entity.
     * @return The entity with the specified ID.
     * @throws ArgumentNullException if the ID is null.
     * @throws ElementNotFoundException if there is no entity with the specified ID.
     */
    public GridEntityComponent getEntity(String id) throws ArgumentNullException, ElementNotFoundException{
        return entityHandler.getByID(id);
    }

    /**
     * Removes an entity from the interactive grid panel.
     * @param id The ID of the entity to remove.
     * @param removeFromList A boolean that determines whether to remove the entity from the entity handler.
     * @return The removed entity.
     * @throws ArgumentNullException if the entity is null.
     * @throws ElementNotFoundException if there is no entity with the specified ID.
     * @throws InvalidArgumentException if the entity is invalid.
     */
    public GridEntityComponent removeEntity(String id, boolean removeFromList) throws ArgumentNullException, ElementNotFoundException, InvalidArgumentException{
        if(id == null)
            throw new ArgumentNullException();

        var entity = entityHandler.getByID(id);

        return removeEntity(entity, removeFromList);
    }

    /**
     * Removes an entity from the interactive grid panel.
     * @param entity The entity to remove.
     * @param removeFromList A boolean that determines whether to remove the entity from the entity handler.
     * @throws ArgumentNullException if the entity is null.
     */
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

    /**
     * Replaces an entity in the interactive grid panel with a new position.
     * @param id The ID of the entity to replace.
     * @param newPosition The new position of the entity.
     * @return The replaced entity.
     * @throws ArgumentNullException if the ID or the new position is null.
     * @throws ElementNotFoundException if there is no entity with the specified ID.
     * @throws InvalidArgumentException if the new position is invalid.
     * @throws ComponentAlreadyAtPositionException if there is already a component at the new position.
     */
    public GridEntityComponent replaceEntity(String id, GridPosition newPosition) throws ArgumentNullException, ElementNotFoundException, InvalidArgumentException, ComponentAlreadyAtPositionException{
        if(id == null || newPosition == null)
            throw new ArgumentNullException();

        var entity = entityHandler.getByID(id);
        return replaceEntity(entity, newPosition);
    }

    /**
     * Replaces an entity in the interactive grid panel with a new position.
     * @param entity The entity to replace.
     * @param newPosition The new position of the entity.
     * @return The replaced entity.
     * @throws ArgumentNullException if the entity or the new position is null.
     * @throws ElementNotFoundException if there is no entity with the specified ID.
     * @throws InvalidArgumentException if the new position is invalid.
     * @throws ComponentAlreadyAtPositionException if there is already a component at the new position.
     */
    public GridEntityComponent replaceEntity(GridEntityComponent entity, GridPosition newPosition) throws ArgumentNullException, ElementNotFoundException, InvalidArgumentException, ComponentAlreadyAtPositionException{
        if(entity == null || newPosition == null)
            throw new ArgumentNullException();

        removeEntity(entity, false);
        entity.setGridPosition(newPosition);
        entityPanel.add(entity, entity.getGridPosition(), true, false);

        return entity;
    }

    /**
     * Gets the ID of an entity from the interactive grid panel by its position.
     * @param position The position of the entity.
     * @return The ID of the entity at the specified position.
     * @throws ArgumentNullException if the position is null.
     * @throws ElementNotFoundException if there is no entity at the specified position.
     */
    public String getEntityByPosition(GridPosition position) throws ArgumentNullException, ElementNotFoundException{
        if(position == null)
            throw new ArgumentNullException();

        var entity = entityHandler.getByPosition(position);
        return entity.getID();
    }
}