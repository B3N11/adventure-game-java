package ui.elements;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class InteractButtonPanel extends JPanel{

    private ArrayList<JButton> buttons;
    
    public InteractButtonPanel(int width, int height, ActionListener listener){
        initPanel(width, height);
        initButtons(listener);
    }

    private void initPanel(int width, int height){
        setLayout(new GridLayout(1,4));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);
    }

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

    public void toggleButtons(boolean enabled){
        for(var button : buttons)
            button.setEnabled(enabled);
        revalidate();
        repaint();
    }
}