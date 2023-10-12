package ui.elements;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

import exception.general.ArgumentNullException;
import exception.general.ElementNotFoundException;
import exception.general.InvalidArgumentException;
import exception.ui.ComponentAlreadyAtPositionException;
import ui.interfaces.IGridPositionable;
import uilogic.GridPosition;

public class GridPanel{
    
    private ArrayList<IGridPositionable> components;
    private JPanel panel;

    public GridPanel(){
        panel = new JPanel(new GridBagLayout());
        components = new ArrayList<IGridPositionable>();
    }

    public JPanel getJPanel(){ return panel; }

    public void refresh(){
        panel.revalidate();
        panel.repaint();
    }

    public void add(IGridPositionable component, GridBagConstraints gbc, boolean force) throws InvalidArgumentException, ComponentAlreadyAtPositionException, ArgumentNullException{
        //Check if argument is NULL
        if(component == null || gbc == null)
            throw new ArgumentNullException();
        
        //Check if the component parameter is Component child
        if(!(component instanceof Component))
            throw new InvalidArgumentException();

        try{
            var check = new GridPosition(gbc.gridx, gbc.gridy);
            var current = getComponentAt(check);

            if(!force)
                throw new ComponentAlreadyAtPositionException();
            
            remove(current);            
        }catch(ElementNotFoundException e){}   
        
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