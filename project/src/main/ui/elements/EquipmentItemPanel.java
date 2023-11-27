package main.ui.elements;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import main.exception.general.ArgumentNullException;
import main.game.utility.IDisplayable;

/**
 * This class represents an equipment item panel in the game. It is used to display an item in the character frame.
 * It extends the JPanel class and contains methods to initialize the panel and set up its content.
 * 
 * The class contains the following fields:
 * - panelWidth: The width of the panel.
 * - panelHeight: The height of the panel.
 * - imgWidth: The width of the image.
 * - imgHeight: The height of the image.
 */
public class EquipmentItemPanel extends JPanel{

    public int panelWidth = 280;
    public int panelHeight = 280;
    public int imgWidth = 100;
    public int imgHeight = 100;
    
    /**
     * Constructor for the EquipmentItemPanel class.
     * Initializes the panel with the specified panel width, panel height, image width, image height, and title.
     * @param panelWidth The width of the panel.
     * @param panelHeight The height of the panel.
     * @param imgWidth The width of the image.
     * @param imgHeight The height of the image.
     * @param title The title of the panel.
     * @throws ArgumentNullException if the title is null.
     */
    public EquipmentItemPanel(int panelWidth, int panelHeight, int imgWidth, int imgHeight, String title) throws ArgumentNullException{
        if(title == null)
            throw new ArgumentNullException();
            
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        initPanel(title);
    }

    /**
     * Initializes the panel.
     * Sets the border, preferred size, and layout of the panel.
     * @param title The title of the panel.
     */
    private void initPanel(String title){
        setBorder(BorderFactory.createTitledBorder(title));
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setLayout(new GridBagLayout());
    }

    /**
     * Sets up the content of the panel.
     * Initializes and adds the icon and name label with the specified item, icon path, bearer level, extended status, listener, and equipable status.
     * @param item The item of the panel.
     * @param iconPath The path of the icon.
     * @param bearerLevel The level of the bearer.
     * @param extended The extended status of the panel. If true, the description, text field, and button will be added.
     * @param listener The listener of the panel.
     * @param equipable The equipable status of the panel.
     * @return The panel itself, for chaining.
     * @throws ArgumentNullException if the item, icon path, or listener is null.
     * @throws IOException if the setup of the icon or name label throws an IOException.
     */
    public EquipmentItemPanel setUpContent(IDisplayable item, String iconPath, int bearerLevel, boolean extended, ActionListener listener, boolean equipable) throws ArgumentNullException, IOException{
        removeAll();

        var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridheight = 1;

        gbc.gridy = 0;
        var icon = new ImageComponent(imgWidth, imgHeight).setImage(iconPath);
        add(icon, gbc);
        
        gbc.gridy = 1;
        var nameLabel = new LabelPanel(false).setLabelText(item.getName(), new Font("Arial", Font.BOLD, 20));
        add(nameLabel, gbc);

        gbc.gridy = 2;
        var descLabel = new LabelPanel(false).setLabelText(item.getStatistics(bearerLevel));
        add(descLabel, gbc);

        if(!extended){
            repaint();
            revalidate();
            return this;
        }

        gbc.gridy = 3;
        var textField = new ScrollableTextPanel(panelWidth - 5, 150);
        textField.addToText(item.getDisplayInfo());
        add(textField, gbc);

        gbc.gridy = 4;
        var button = new CustomButton(panelWidth - 5, 50, "Equip");
        button.addActionListener(listener);
        button.setEnabled(equipable);
        add(button, gbc);

        repaint();
        revalidate();
        return this;
    }
}