package ui.elements;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import exception.general.ArgumentNullException;

/**
 * This class represents an equipment panel in the game. It stores two non extended equipment item panels. (top and bottom)
 * It extends the JPanel class and contains methods to initialize the panel and set up its content.
 * 
 * The class contains the following fields:
 * - PANEL_WIDTH: The width of the panel.
 * - PANEL_HEIGHT: The height of the panel.
 * - topPanel: The top panel of the equipment panel.
 * - botPanel: The bottom panel of the equipment panel.
 */
public class EquipmentPanel extends JPanel{
    
    public static int PANEL_WIDTH = 300;
    public static int PANEL_HEIGHT = 500;

    private EquipmentItemPanel topPanel;
    private EquipmentItemPanel botPanel;

    /**
     * Constructor for the EquipmentPanel class.
     * Initializes the panel with the specified top text and bottom text.
     * @param topText The text of the top panel.
     * @param botText The text of the bottom panel.
     * @throws ArgumentNullException if the top text or bottom text is null.
     */
    public EquipmentPanel(String topText, String botText) throws ArgumentNullException{
        if(topText == null || botText == null)
            throw new ArgumentNullException();

        initPanel();
        setUpContent(topText, botText);
    }

    /**
     * Initializes the panel.
     * Sets the preferred size, bounds, border, and layout of the panel.
     */
    private void initPanel(){
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        setBorder(BorderFactory.createTitledBorder("Equipment"));
        setLayout(new GridLayout(2,1));
    }

    /**
     * Sets up the content of the panel.
     * Initializes and adds the top panel and bottom panel with the specified top text and bottom text.
     * @param topText The text of the top panel.
     * @param botText The text of the bottom panel.
     */
    private void setUpContent(String topText, String botText){            
        try{
            topPanel = new EquipmentItemPanel(280, 280, 100, 100, topText);
            botPanel = new EquipmentItemPanel(280, 280, 100, 100, botText);

            add(topPanel);
            add(botPanel);
        }catch(ArgumentNullException e){}
    }

    public EquipmentItemPanel getTopPanel() { return topPanel; }
    public EquipmentItemPanel getBotPanel() { return botPanel; }
}