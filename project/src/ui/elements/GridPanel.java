package ui.elements;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

import exception.general.ArgumentNullException;
import exception.general.ElementNotFoundException;
import exception.general.InvalidArgumentException;
import exception.ui.ComponentAlreadyAtPositionException;
import ui.data.GridDimension;
import uilogic.GridPosition;
import uilogic.IGridPositionable;

/**
 * This class represents a grid panel in the game.GridPanel stores IGridPositionable objects in a GridBagLayout. Its purpose is to handle the UI components in the layout and manage them by position
 * It stores IGridPositionable objects in a GridBagLayout. Its purpose is to handle the UI components in the layout and manage them by position.
 * 
 * The class contains the following fields:
 * - components: The components of the grid panel.
 * - preferredComponentDimension: The preferred component dimension of the grid panel.
 * - panel: The panel of the grid panel.
 */
public class GridPanel{
    
    private ArrayList<IGridPositionable> components;
    private GridDimension preferredComponentDimension;
    private JPanel panel;

    /**
     * Constructor for the GridPanel class.
     * Initializes the grid panel with the specified width, height, preferred horizontal component count, and preferred vertical component count.
     * @param width The width of the grid panel.
     * @param height The height of the grid panel.
     * @param preferredHorizontalComponentCount The preferred horizontal component count of the grid panel.
     * @param preferredVerticalComponentCount The preferred vertical component count of the grid panel.
     */
    public GridPanel(int width, int height, int preferredHorizontalComponentCount, int preferredVerticalComponentCount){
        panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBounds(0, 0, width, height);
        panel.setOpaque(false);
        preferredComponentDimension = new GridDimension(preferredHorizontalComponentCount, preferredVerticalComponentCount);
        components = new ArrayList<IGridPositionable>();
    }

    /**
     * Gets the JPanel of the grid panel.
     * @return The JPanel of the grid panel.
     */
    public JPanel getJPanel(){ return panel; }

    public void refresh(){
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Adds a component to the grid panel at the specified grid position.
     * If force is true, it will replace any existing component at the position.
     * If allowOutOfBounds is true, it will allow adding the component outside of the preferred dimension.
     * @param component The component to be added.
     * @param position The grid position where the component will be added.
     * @param force A boolean that determines whether to replace any existing component at the position.
     * @param allowOutOfBounds A boolean that determines whether to allow adding the component outside of the preferred dimension.
     * @throws InvalidArgumentException if the component is not a Component child.
     * @throws ComponentAlreadyAtPositionException if a component already exists at the position and force is false.
     * @throws ArgumentNullException if the component or position is null.
     * @throws IndexOutOfBoundsException if the position is outside of the preferred dimension and allowOutOfBounds is false.
     */
    public void add(IGridPositionable component, GridPosition position, boolean force, boolean allowOutOfBounds) throws InvalidArgumentException, ComponentAlreadyAtPositionException, ArgumentNullException{
        //Check if argument is NULL
        if(component == null || position == null)
            throw new ArgumentNullException();

        //Check if the component parameter is Component child
        if(!(component instanceof Component))
            throw new InvalidArgumentException();

        //If we don't allow placement of new component outside of preferred dimension, we throw error if that is violated
        if(!allowOutOfBounds && (position.getX() >= preferredComponentDimension.getHorizontal() || position.getY() >= preferredComponentDimension.getVertical()))
            throw new IndexOutOfBoundsException();

        try{
            var current = getComponentAt(position);

            if(!force)
                throw new ComponentAlreadyAtPositionException();
            
            remove(current);            
        }catch(ElementNotFoundException e){}   
        
        //Create new GBC based on received position data
        var gbc = new GridBagConstraints();
        gbc.gridx = position.getX();
        gbc.gridy = position.getY();

        //Add to list and panel
        components.add(component);
        panel.add((Component)component, gbc);
    }

    /**
     * Gets the component at the specified grid position.
     * @param position The grid position.
     * @return The component at the specified grid position.
     * @throws ElementNotFoundException if no component is found at the specified grid position.
     */
    public IGridPositionable getComponentAt(GridPosition position) throws ElementNotFoundException{
        for(var component : components)
            if(component.getGridPosition().equals(position))
                return component;
        
        throw new ElementNotFoundException();
    }

    /**
     * Removes a component from the grid panel.
     * @param component The component to be removed.
     * @throws ArgumentNullException if the component is null.
     * @throws InvalidArgumentException if the component is not a Component child.
     * @throws ElementNotFoundException if the component does not exist in the grid.
     */
    public void remove(IGridPositionable component) throws ArgumentNullException, InvalidArgumentException, ElementNotFoundException{
        //Check for argument validity
        if(component == null)
            throw new ArgumentNullException();
        
        //Check if argument is Component child
        if(!(component instanceof Component))
            throw new InvalidArgumentException();

        //Check if component is in grid
        if(!components.contains(component))
            throw new ElementNotFoundException();
        
        components.remove(component);
        panel.remove((Component)component);
    }

    /**
     * Removes the component at the specified grid position.
     * @param position The grid position.
     * @throws ArgumentNullException if the position is null.
     * @throws ElementNotFoundException if no component is found at the specified grid position.
     */
    public void removeAt(GridPosition position) throws ArgumentNullException, ElementNotFoundException{
        //Check if argument null
        if(position == null)
            throw new ArgumentNullException();

        var component = getComponentAt(position);
        try{ remove(component); }
        catch(InvalidArgumentException e){
            //Won't happen, the list only stores Component descendants
        }
    }
}