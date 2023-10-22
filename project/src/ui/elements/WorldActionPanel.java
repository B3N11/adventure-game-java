package ui.elements;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class WorldActionPanel extends JPanel{
    
    public static int WIDTH = 300;
    public static int HEIGHT = PlayFrame.HEIGHT;
    public static int BUTTONCOUNT = 4;

    public WorldActionPanel(){
        initPanel();
        setupButtons();
    }

    private void initPanel(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBounds(0, 0, WIDTH, HEIGHT);
        setLayout(new GridLayout(BUTTONCOUNT, 1));

        setBorder(BorderFactory.createLineBorder(Color.red));
    }

    private void setupButtons(){
        //for(int i = 0; i < BUTTONCOUNT; i++)
            //add(new Button("asd"));
    }
}
