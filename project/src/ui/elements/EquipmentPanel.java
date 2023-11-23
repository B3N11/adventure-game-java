package ui.elements;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import exception.general.ArgumentNullException;

public class EquipmentPanel extends JPanel{
    
    public static int PANEL_WIDTH = 300;
    public static int PANEL_HEIGHT = 500;

    private EquipmentItemPanel topPanel;
    private EquipmentItemPanel botPanel;

    public EquipmentPanel(String topText, String botText) throws ArgumentNullException{
        if(topText == null || botText == null)
            throw new ArgumentNullException();

        initPanel();
        setUpContent(topText, botText);
    }

    private void initPanel(){
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        setBorder(BorderFactory.createTitledBorder("Equipment"));
        setLayout(new GridLayout(2,1));
    }

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