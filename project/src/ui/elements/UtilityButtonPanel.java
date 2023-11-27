package ui.elements;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class UtilityButtonPanel extends JPanel{

    private ArrayList<JButton> buttons;
    
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

    public void toggleButtons(boolean enabled){
        for(var button : buttons)
            button.setEnabled(enabled);
        revalidate();
        repaint();
    }
}