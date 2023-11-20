package ui.elements;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class EquipmentPanel extends JPanel{
    
    public static int PANEL_WIDTH = 400;
    public static int PANEL_HEIGHT = 800;

    public EquipmentPanel(){
        initPanel();
        setUpContent();
    }

    private void initPanel(){
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        setBorder(BorderFactory.createTitledBorder("Equipment"));
        setLayout(new GridLayout(2,1));
    }

    private void setUpContent(){
        
    }
}