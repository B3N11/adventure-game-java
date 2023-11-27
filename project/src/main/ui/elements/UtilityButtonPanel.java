package main.ui.elements;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This class represents a utility button panel in the UI. It allows the player to travel and manage their equipment.
 * It extends the JPanel class and contains a list of JButtons for utility functions.
 * 
 * The class contains the following field:
 * - buttons: The list of JButtons in the panel.
 */
public class UtilityButtonPanel extends JPanel{

    private ArrayList<JButton> buttons;
    
    /**
     * Constructor for the UtilityButtonPanel class.
     * Initializes the panel and the buttons with the specified width, height, and action listener.
     * @param width The width of the panel.
     * @param height The height of the panel.
     * @param listener The action listener of the buttons.
     */
    public UtilityButtonPanel(int width, int height, ActionListener listener){
        initPanel(width, height);
        initButtons(listener);
    }

    /**
     * Initializes the panel.
     * Sets the layout, border, preferred size, and bounds of the panel.
     * @param width The width of the panel.
     * @param height The height of the panel.
     */
    private void initPanel(int width, int height){
        setLayout(new GridLayout(2,1));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);
    }

    /**
     * Initializes the buttons in the panel.
     * Initializes the "Travel" and "Equipment" buttons, sets their action listeners and action commands, adds them to the list of buttons, and adds them to the panel.
     * @param listener The action listener of the buttons.
     */
    private void initButtons(ActionListener listener){
        buttons = new ArrayList<>();
        var travelButton = new JButton("Travel");
        travelButton.addActionListener(listener);
        travelButton.setActionCommand("UTILITY_TRAVEL");

        var equipmentButton = new JButton("Equipment");
        equipmentButton.addActionListener(listener);
        equipmentButton.setActionCommand("UTILITY_EQUIPMENT");

        buttons.add(travelButton);
        buttons.add(equipmentButton);

        add(travelButton);
        add(equipmentButton);
    }

    /**
     * Toggles the buttons.
     * Enables or disables the buttons.
     * @param enabled A boolean that determines whether to enable or disable the buttons.
     */
    public void toggleButtons(boolean enabled){
        for(var button : buttons)
            button.setEnabled(enabled);
        revalidate();
        repaint();
    }
}