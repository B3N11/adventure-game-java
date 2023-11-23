package ui.elements;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import exception.general.ArgumentNullException;

public class CharacterFrame extends JFrame{

    private EquipmentPanel equipmentPanel;
    private InventoryPanel inventoryPanel;
    private EquipmentItemPanel itemPanel;
    
    public CharacterFrame(int inventoryCount, ActionListener buttonHandler) throws ArgumentNullException{
        if(buttonHandler == null)
            throw new ArgumentNullException();
            
        initFrame();
        setUpContent(inventoryCount, buttonHandler);
        pack();
    }

    private void initFrame(){
        setTitle("Character");
        setResizable(false);
        setLayout(new GridLayout(1,3));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void setUpContent(int inventoryCount, ActionListener buttonHandler){
        try{
            equipmentPanel = new EquipmentPanel("Armor", "Weapon");
            add(equipmentPanel);
        }catch(ArgumentNullException e){}

        try{
            int rowCount = (inventoryCount / 10) + 1;
            int finalCount = Math.max(rowCount, 30);
            inventoryPanel = new InventoryPanel(finalCount, 6, buttonHandler);
            add(inventoryPanel);
        }catch(Exception e){}

        try{
            itemPanel = new EquipmentItemPanel(300, 500, 200, 200, "Selected Item");
            add(itemPanel);
        }catch(ArgumentNullException e){}
    }

    public void refresh(){
        revalidate();
        repaint();
    }
    
    public EquipmentPanel getEquipmentPanel() { return equipmentPanel; }
    public InventoryPanel getInventoryPanel() { return inventoryPanel; }
    public EquipmentItemPanel getItemPanel() { return itemPanel; }
}