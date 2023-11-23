package ui.elements;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class UtilityButtonPanel extends JPanel{
    
    public UtilityButtonPanel(int width, int height, ActionListener listener){
        initPanel(width, height);
        initButtons(listener);
    }

    private void initPanel(int width, int height){
        setLayout(new GridLayout(2,1));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        setPreferredSize(new Dimension(width, height));
        setBounds(0, 0, width, height);
    }

    private void initButtons(ActionListener listener){
        var travelButton = new JButton("Travel");
        travelButton.addActionListener(listener);

        var equipmentButton = new JButton("Equipment");
        equipmentButton.addActionListener(listener);
        equipmentButton.setActionCommand("UTILITY_EQUIPMENT");

        add(travelButton);
        add(equipmentButton);
    }
}