package ui.elements;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This class represents an interact button panel in the game. Buttons are used to move, attack, pick up, and end turn.
 * It extends the JPanel class and is used to display a set of interaction buttons.
 * 
 * The class contains the following field:
 * - buttons: The buttons of the interact button panel.
 */
public class InteractButtonPanel extends JPanel{

    private ArrayList<JButton> buttons;
    
    /**
     * Constructor for the InteractButtonPanel class.
     * Initializes the interact button panel with the specified width, height, and action listener.
     * @param width The width of the interact button panel.
     * @param height The height of the interact button panel.
     * @param listener The action listener of the interact button panel.
     */
    public InteractButtonPanel(int width, int height, ActionListener listener){
        initPanel(width, height);
        initButtons(listener);
    }

    /**
     * Initializes the panel of the interact button panel.
     * Sets the layout, border, preferred size, and bounds of the panel.
     * @param width The width of the panel.
     * @param height The height of the panel.
     */
    private void initPanel(int width, int height){
        setLayout(new GridLayout(1,4));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);
    }

    /**
     * Initializes the buttons of the interact button panel.
     * Creates the move, attack, pick up, and end turn buttons, sets their action listeners and action commands, and adds them to the panel and the buttons list.
     * @param listener The action listener of the buttons.
     */
    private void initButtons(ActionListener listener){
        buttons = new ArrayList<>();
        var moveButton = new JButton("Move");
        moveButton.addActionListener(listener);
        moveButton.setActionCommand("INTERACT_MOVE");

        var attackButton = new JButton("Attack");
        attackButton.addActionListener(listener);
        attackButton.setActionCommand("INTERACT_ATTACK");

        var pickupButton = new JButton("Pick Up");
        pickupButton.addActionListener(listener);
        pickupButton.setActionCommand("INTERACT_PICKUP");

        var endTurnButton = new JButton("END TURN");
        endTurnButton.addActionListener(listener);
        endTurnButton.setActionCommand("INTERACT_ENDTURN");

        buttons.add(moveButton);
        buttons.add(attackButton);
        buttons.add(pickupButton);
        buttons.add(endTurnButton);

        add(moveButton);
        add(attackButton);
        add(pickupButton);
        add(endTurnButton);
    }

    /**
     * Toggles the enabled state of the buttons in the interact button panel.
     * If enabled is true, it enables the buttons; otherwise, it disables them.
     * After toggling, it revalidates and repaints the panel.
     * @param enabled A boolean that determines whether to enable the buttons.
     */
    public void toggleButtons(boolean enabled){
        for(var button : buttons)
            button.setEnabled(enabled);
        revalidate();
        repaint();
    }
}