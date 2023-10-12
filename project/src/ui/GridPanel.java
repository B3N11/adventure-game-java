package ui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

import exception.general.ArgumentNullException;
import exception.general.ElementNotFoundException;
import uilogic.GridPosition;

public class GridPanel extends JPanel{
    
    private ArrayList<MapGridButton> buttons;

    public GridPanel(){
        super(new GridBagLayout());
        buttons = new ArrayList<MapGridButton>();
    }

    public void add(MapGridButton button, GridBagConstraints gbc){
        buttons.add(button);
        super.add(button, gbc);
    }

    public MapGridButton getButtonAt(GridPosition position) throws ElementNotFoundException{
        for(var button : buttons)
            if(button.getGridPosition().equals(position))
                return button;
        
        throw new ElementNotFoundException();
    }

    public void removeAt(GridPosition position) throws ArgumentNullException, ElementNotFoundException{
        if(position == null)
            throw new ArgumentNullException();

        var button = getButtonAt(position);
        remove(button);
    }
}