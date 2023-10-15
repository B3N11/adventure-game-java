package ui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import exception.general.ArgumentNullException;
import uilogic.GridButtonHandler;
import uilogic.MapLayoutData;
import ui.data.UIDataConfig;

//Manages UI related to the area where the game is displayed
public class PlayfieldPanel extends JLayeredPane{
    
    private JLabel background;
    private GridPanel entityPanel;
    private GridPanel buttonPanel;

    public PlayfieldPanel(MapLayoutData layoutData, GridButtonHandler handler) throws Exception {
        if(layoutData == null || handler == null)
            throw new ArgumentNullException();

        initPlayfield(layoutData, handler);
    }

    private void initPlayfield(MapLayoutData layoutData, GridButtonHandler handler) throws Exception{
        setPreferredSize(new Dimension(UIDataConfig.MAPGRID_X, UIDataConfig.MAPGRID_Y));
        setBounds(0, 0, UIDataConfig.MAPGRID_X, UIDataConfig.MAPGRID_Y);

        initBackground(UIDataConfig.MAPGRID_X, UIDataConfig.MAPGRID_Y, layoutData.getFilePath());
        initEntityPanel(layoutData.getHorizontal(), layoutData.getVertical());
        initButtonPanel(layoutData.getHorizontal(), layoutData.getVertical(), handler);

        add(background, Integer.valueOf(0));
        add(entityPanel.getJPanel(), Integer.valueOf(1));
        add(buttonPanel.getJPanel(), Integer.valueOf(2));

        revalidate();
        repaint();
    }

    private void initBackground(int x, int y, String file) throws Exception{
        Image asd = ImageIO.read(new File(file));
        Image image = asd.getScaledInstance(x, y, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        background = new JLabel(icon);
        background.setPreferredSize(new Dimension(x, y));
        background.setBounds(0, 0, x, y);
    }

    private void initEntityPanel(int x, int y) throws Exception{
        entityPanel = new GridPanel();
        var gbc = new GridBagConstraints();

        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                gbc.gridx = j;
                gbc.gridy = i;
                entityPanel.add(new DummyComponent(50, 50, gbc), gbc, false);
            }
        }

        entityPanel.refresh();
    }

    private void initButtonPanel(int x, int y, GridButtonHandler handler) throws Exception{
        buttonPanel = new GridPanel();
        var gbc = new GridBagConstraints();
        var color = new Color(255, 0, 0, 100);

        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                gbc.gridx = j;
                gbc.gridy = i;
                buttonPanel.add(new MapGridButton(50, 50, color, gbc, handler), gbc, false);
            }
        }

        buttonPanel.refresh();
    }
}