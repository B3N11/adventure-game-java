package main.ui.elements;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import main.exception.general.ArgumentNullException;
import main.game.utility.GenericDelegate;

/**
 * This class represents a travel frame in the UI. It allows the player to select a map to travel to in a combobox.
 * It extends the JFrame class and contains a JComboBox for selecting a map and a GenericDelegate for handling map selection.
 * 
 * The class contains the following fields:
 * - comboBox: The JComboBox for selecting a map.
 * - onMapSelect: The GenericDelegate for handling map selection.
 */
public class TravelFrame extends JFrame{

    private JComboBox<String> comboBox;
    private GenericDelegate onMapSelect;

    /**
     * Constructor for the TravelFrame class.
     * Initializes the frame, sets up the content, and sets the GenericDelegate for handling map selection.
     * @param mapNames The names of the maps.
     * @param onMapSelect The GenericDelegate for handling map selection.
     */
    public TravelFrame(String[] mapNames, GenericDelegate onMapSelect){
        initFrame();
        setUpContent(mapNames);
        this.onMapSelect = onMapSelect;
    }
        
    /**
     * Initializes the frame.
     * Sets the default close operation, resizable status, and layout of the frame.
     */
    private void initFrame(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(2,1));
    }

    /**
     * Sets up the content of the frame.
     * Initializes the JComboBox with the map names and adds it to the frame.
     * @param mapNames The names of the maps.
     */
    private void setUpContent(String[] mapNames){
        comboBox = new JComboBox<>(mapNames);   
        add(comboBox);

        try{
            var button = new CustomButton(200, 50, "Travel");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { selectMap(); }
            });
            add(button);
        }
        catch(ArgumentNullException e){}
        
        pack();
    }

    /**
     * Selects a map.
     * Gets the selected item from the JComboBox, and if the GenericDelegate for handling map selection is not null, runs it with the selected item.
     */
    public void selectMap(){
        String item = comboBox.getSelectedItem().toString();
        if(onMapSelect != null)
            onMapSelect.run(item);
    }
}