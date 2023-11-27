package uilogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import exception.general.ArgumentNullException;
import exception.general.ConfigNotLoadedException;
import game.behaviour.abstracts.Equipment;
import game.behaviour.abstracts.Weapon;
import game.behaviour.entities.items.Item;
import game.behaviour.entities.items.equipment.Armor;
import game.enums.EquipmentType;
import game.enums.ItemType;
import game.global.GameHandler;
import game.global.storage.IconDataStorage;
import game.global.storage.ItemStorage;
import game.utility.GenericDelegate;
import ui.elements.CharacterFrame;
import ui.elements.GridEntityComponent;

public class CharacterFrameHandler {
    
    private CharacterFrame frame;
    private GridButtonHandler gridButtonHandler;
    private Item item;

    public CharacterFrameHandler(){
        try{
            gridButtonHandler = new GridButtonHandler(new GenericDelegate() {
                public void run(Object o){ selectItem(o);}
            }, false);            
        }catch(ArgumentNullException e){}
    }

    public GridButtonHandler getGridButtonHandler() { return gridButtonHandler; }

    public void setCharacterFrame(CharacterFrame frame) throws ArgumentNullException{
        if(frame == null)
            throw new ArgumentNullException();
        this.frame = frame;
    }

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