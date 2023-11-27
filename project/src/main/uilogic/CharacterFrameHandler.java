package main.uilogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.exception.general.ArgumentNullException;
import main.exception.general.ConfigNotLoadedException;
import main.game.behaviour.abstracts.Equipment;
import main.game.behaviour.abstracts.Weapon;
import main.game.behaviour.items.Item;
import main.game.behaviour.items.equipment.Armor;
import main.game.enums.EquipmentType;
import main.game.enums.ItemType;
import main.game.global.GameHandler;
import main.game.global.storage.IconDataStorage;
import main.game.global.storage.ItemStorage;
import main.game.utility.GenericDelegate;
import main.ui.elements.CharacterFrame;
import main.ui.elements.GridEntityComponent;

/**
 * This class handles the character frame in the UI.
 * It contains a CharacterFrame, a GridButtonHandler for handling grid button actions, and an Item for the selected item.
 * 
 * The class contains the following fields:
 * - frame: The CharacterFrame that this class handles.
 * - gridButtonHandler: The GridButtonHandler for handling grid button actions.
 * - item: The Item for the selected item.
 */
public class CharacterFrameHandler {
    
    private CharacterFrame frame;
    private GridButtonHandler gridButtonHandler;
    private Item item;

    /**
     * Constructor for the CharacterFrameHandler class.
     * Initializes the GridButtonHandler with a GenericDelegate that runs the selectItem method when a grid button is clicked.
     */
    public CharacterFrameHandler(){
        try{
            gridButtonHandler = new GridButtonHandler(new GenericDelegate() {
                public void run(Object o){ selectItem(o);}
            }, false);            
        }catch(ArgumentNullException e){}
    }

    /**
     * Gets the GridButtonHandler.
     * @return The GridButtonHandler.
     */
    public GridButtonHandler getGridButtonHandler() { return gridButtonHandler; }

    /**
     * Sets the CharacterFrame.
     * @param frame The CharacterFrame to set.
     * @throws ArgumentNullException if the frame is null.
     */
    public void setCharacterFrame(CharacterFrame frame) throws ArgumentNullException{
        if(frame == null)
            throw new ArgumentNullException();
        this.frame = frame;
    }

    /**
     * Selects an item.
     * Sets the selected item to the item associated with the clicked grid button, and shows the selected item in the item panel.
     * @param o The object associated with the clicked grid button.
     */
    public void selectItem(Object o){
        var tilePosition = (GridPosition)o;
        
        try{
            String id = frame.getInventoryPanel().getGrid().getEntityByPosition(tilePosition);
            var iconPath = IconDataStorage.getInstance().get(id);
            item = ItemStorage.getInstance().get(id);
            boolean equipable = item.getItemType() == ItemType.EQUIPMENT ? true : false;

            frame.getItemPanel().setUpContent(item, iconPath.getAbsolutPath(), GameHandler.getInstance().getPlayer().getEntity().getLevel(), true, new ActionListener() {
                public void actionPerformed(ActionEvent e){ equipItem(); }
            }, equipable);
            frame.refresh();
        }
        catch(Exception e){}
    }

    /**
     * Equips an item.
     * If the selected item is not null and is an equipment, equips the selected item to the player, and shows the equipped item in the equipment panel.
     */
    public void equipItem(){
        if(item.getItemType() == ItemType.CONSUMABLE)
            return;
        
        var player = GameHandler.getInstance().getPlayer().getEntity();
        var newEquipment = (Equipment)item;
        
        if(newEquipment.getEquipmentType() == EquipmentType.ARMOR){
            try{ player.equip((Armor)newEquipment); }
            catch(Exception e){}
        }
        else if(newEquipment.getEquipmentType() == EquipmentType.WEAPON){
            try{ player.equip((Weapon)newEquipment); }
            catch(Exception e){}
        }

        showEquippedItem(newEquipment.getEquipmentType());
    }

    
    /**
     * Starts the character frame handler.
     * Gets the player, checks if the player is null, gets the inventory count, initializes the character frame with the inventory count and the grid button handler, fills the inventory, sets the equipment panel, sets the item display panel, and sets the visibility of the character frame to true.
     * @throws ConfigNotLoadedException if the player is null.
     */
    public void start() throws ConfigNotLoadedException{
        var player = GameHandler.getInstance().getPlayer();

        if(player == null)
            throw new ConfigNotLoadedException();

        int inventoryCount = player.getEntity().getInventory().size();

        try{ frame = new CharacterFrame(inventoryCount, gridButtonHandler); }
        catch(ArgumentNullException e){}

        fillInventory();
        setEquipmentPanel();
        setItemDisplayPanel();

        frame.setVisible(true);
    }

    /**
     * Fills the inventory.
     * Gets the grid of the inventory panel, the inventory of the player, all items in the inventory, and the column count of the inventory panel, and adds the items to the grid.
     */
    private void fillInventory(){
        var grid = frame.getInventoryPanel().getGrid();
        var inventory = GameHandler.getInstance().getPlayer().getEntity().getInventory();
        var allItems = inventory.getAllItems();
        int columnCount = frame.getInventoryPanel().getColumnCount();

        int horizontal = 0;
        int vertical = 0;

        try{
            for(var item : allItems){
                var imagePath = IconDataStorage.getInstance().get(item.getID());
                var component = new GridEntityComponent(item.getID(), grid.getComponentSize().getHorizontal(), grid.getComponentSize().getVertical(), new GridPosition(horizontal, vertical));
                component.setImage(imagePath.getAbsolutPath());
                grid.addEntity(component);

                horizontal++;
                if(horizontal != columnCount)
                    continue;

                horizontal = 0;
                vertical++;
            }
        }catch(Exception e){}
    }

    /**
     * Sets the equipment panel.
     * Gets the player, the player's armor and weapon, the icon paths of the armor and weapon, and sets up the top panel and the bottom panel of the equipment panel with the armor and weapon, their icon paths, and the player's level.
     */
    private void setEquipmentPanel(){
        var player = GameHandler.getInstance().getPlayer().getEntity();
        var armor = player.getArmor();
        var weapon = player.getWeapon();

        try{
            var armorIcon = IconDataStorage.getInstance().get(armor.getID());
            var weaponIcon = IconDataStorage.getInstance().get(weapon.getID());
    
            frame.getEquipmentPanel().getTopPanel().setUpContent(armor, armorIcon.getAbsolutPath(), player.getLevel(), false, null, false);
            frame.getEquipmentPanel().getBotPanel().setUpContent(weapon, weaponIcon.getAbsolutPath(), player.getLevel(), false, null, false);
        }catch(Exception e){}
    }

    /**
     * Sets the item display panel.
     * Gets the player, the first item in the player's inventory, the icon path of the item, and sets up the item panel with the item, its icon path, the player's level, and an action listener for equipping the item, and refreshes the frame.
     */
    private void setItemDisplayPanel(){
        var player = GameHandler.getInstance().getPlayer().getEntity();
        var item = player.getInventory().getEquipments().getFirst();

        try{
            var iconPath = IconDataStorage.getInstance().get(item.getID());
            frame.getItemPanel().setUpContent(item, iconPath.getAbsolutPath(), player.getLevel(), true, new ActionListener() {
                public void actionPerformed(ActionEvent e) { equipItem(); }
            },true);
            frame.refresh();
        }
        catch(Exception e){}
    }

    /**
     * Shows an equipped item in the equipment panel.
     * Sets up the content of the top panel or the bottom panel of the equipment panel with the item, its icon path, and the player's level, depending on the equipment type of the item.
     * @param type The equipment type of the item.
     */
    private void showEquippedItem(EquipmentType type){
        try{
            var iconPath = IconDataStorage.getInstance().get(item.getID());
            var player = GameHandler.getInstance().getPlayer().getEntity();
    
            if(type == EquipmentType.ARMOR)
                frame.getEquipmentPanel().getTopPanel().setUpContent(item, iconPath.getAbsolutPath(), player.getLevel(), false, null, false);
            else if(type == EquipmentType.WEAPON)
                frame.getEquipmentPanel().getBotPanel().setUpContent(item, iconPath.getAbsolutPath(), player.getLevel(), false, null, false);

        }catch(Exception e){}
    }
}