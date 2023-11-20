package ui.elements;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridLayout;

import exception.general.ArgumentNullException;
import ui.data.GridPosition;
import uilogic.GridButtonHandler;

public class CharacterFrame extends JFrame{

    private EquipmentPanel equipmentPanel;
    
    public CharacterFrame(){
        initFrame();
        setUpContent();
        pack();
    }

    private void initFrame(){
        setTitle("Character");
        setAlwaysOnTop(true);
        setResizable(false);
        setLayout(new GridLayout(1,3));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void setUpContent(){
        try{
            equipmentPanel = new EquipmentPanel("Armor", "Weapon");
            add(equipmentPanel);
        }catch(ArgumentNullException e){}

        try{
            var panel = new JPanel();
            panel.setBorder(BorderFactory.createTitledBorder("Inventory"));
            var grid = new GridPanel(500, 500, 5, 5);
            for(int i = 0; i < 5; i++)
                for(int j = 0; j < 5; j++)
                    grid.add(new GridButton(90, 90, Color.BLACK, new GridPosition(i,j), null), new GridPosition(i,j), false, false);
            panel.add(grid.getJPanel());
            add(panel);
        }catch(Exception e){}
    }
    
    public EquipmentPanel getEquipmentPanel() { return equipmentPanel; }
}