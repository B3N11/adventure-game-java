package ui.elements;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class InteractButtonPanel extends JPanel{
    
    public InteractButtonPanel(int width, int height, ActionListener listener){
        initPanel(width, height);
        initButtons(listener);
    }

    private void initPanel(int width, int height){
        setLayout(new GridLayout(1,3));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);
    }

    private void initButtons(ActionListener listener){
        var moveButton = new JButton("Move");
        moveButton.addActionListener(listener);

        var attackButton = new JButton("Attack");
        attackButton.addActionListener(listener);

        var pickupButton = new JButton("Pick Up");
        pickupButton.addActionListener(listener);

        add(moveButton);
        add(attackButton);
        add(pickupButton);
    }
}