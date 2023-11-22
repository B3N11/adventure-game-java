package ui.elements;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import exception.general.ArgumentNullException;
import game.utility.dataclass.MapLayoutData;
import ui.data.GridDimension;
import ui.data.GridPosition;
import uilogic.MultipleButtonHandler;

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

            //SETUP: data columns fixed, rows depend on inventory size. playfield size horizontally: data.columns * griddimension, vertically: data.rows * griddimension
            var data = new MapLayoutData("asd", 10, 30, null, new GridPosition(0,0));
            var panel = new InteractiveGridPanel(480, 1440).setMapLayout(data, new MultipleButtonHandler() {
                public void initActions(){}
            }, false, new GridDimension(48, 48));
            var scrollbar = new JScrollPane(panel);
            scrollbar.setPreferredSize(new Dimension(500,500));
            scrollbar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollbar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            
            add(scrollbar);
        }catch(Exception e){}
    }
    
    public EquipmentPanel getEquipmentPanel() { return equipmentPanel; }
}