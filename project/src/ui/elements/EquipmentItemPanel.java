package ui.elements;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import exception.general.ArgumentNullException;
import game.utility.IDisplayable;

public class EquipmentItemPanel extends JPanel{

    public int panelWidth = 280;
    public int panelHeight = 280;
    public int imgWidth = 100;
    public int imgHeight = 100;
    
    public EquipmentItemPanel(int panelWidth, int panelHeight, int imgWidth, int imgHeight, String title) throws ArgumentNullException{
        if(title == null)
            throw new ArgumentNullException();
            
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        initPanel(title);
    }

    private void initPanel(String title){
        setBorder(BorderFactory.createTitledBorder(title));
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setLayout(new GridBagLayout());
    }

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