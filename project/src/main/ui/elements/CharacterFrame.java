package main.ui.elements;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import main.exception.general.ArgumentNullException;

/**
 * This class represents a character frame in the game. It displays the character's equipment and inventory.
 * It extends the JFrame class and contains an equipment panel, an inventory panel, and an equipment item panel.
 * 
 * The class contains the following fields:
 * - equipmentPanel: The equipment panel of the character.
 * - inventoryPanel: The inventory panel of the character.
 * - itemPanel: The equipment item panel of the character.
 */
public class CharacterFrame extends JFrame{

    private EquipmentPanel equipmentPanel;
    private InventoryPanel inventoryPanel;
    private EquipmentItemPanel itemPanel;
    
    /**
     * Constructor for the CharacterFrame class.
     * Initializes the frame with the specified inventory count and button handler.
     * @param inventoryCount The count of the inventory.
     * @param buttonHandler The handler of the button.
     * @throws ArgumentNullException if the button handler is null.
     */
    public CharacterFrame(int inventoryCount, ActionListener buttonHandler) throws ArgumentNullException{
        if(buttonHandler == null)
            throw new ArgumentNullException();
            
        initFrame();
        setUpContent(inventoryCount, buttonHandler);
        pack();
    }

    /**
     * Initializes the frame.
     * Sets the title, resizability, layout, and default close operation of the frame.
     */
    private void initFrame(){
        setTitle("Character");
        setResizable(false);
        setLayout(new GridLayout(1,3));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Sets up the content of the frame.
     * Initializes and adds the equipment panel and inventory panel with the specified inventory count and button handler.
     * @param inventoryCount The count of the inventory.
     * @param buttonHandler The handler of the button.
     */
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