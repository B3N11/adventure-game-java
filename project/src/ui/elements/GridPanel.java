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
import ui.data.GridPosition;
import uilogic.IGridPositionable;

//GridPanel stores IGridPositionable objects in a GridBagLayout. Its purpose is to handle the UI components in the layout and manage them by position
public class GridPanel{
    
    private ArrayList<IGridPositionable> components;
    private GridDimension preferredComponentDimension;
    private JPanel panel;

    public GridPanel(int width, int height, int preferredHorizontalComponentCount, int preferredVerticalComponentCount){
        panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBounds(0, 0, width, height);
        panel.setOpaque(false);
        preferredComponentDimension = new GridDimension(preferredHorizontalComponentCount, preferredVerticalComponentCount);
        components = new ArrayList<IGridPositionable>();
    }

    public JPanel getJPanel(){ return panel; }

    public void refresh(){
        panel.revalidate();
        panel.repaint();
    }

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

    public IGridPositionable getComponentAt(GridPosition position) throws ElementNotFoundException{
        for(var component : components)
            if(component.getGridPosition().equals(position))
                return component;
        
        throw new ElementNotFoundException();
    }

    //Remove a component
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

    //Remove a component at given position
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