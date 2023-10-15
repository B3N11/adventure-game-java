import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

import game.utility.dice.Dice;
import game.utility.dice.DiceRoller;
import ui.*;
import ui.elements.DummyComponent;
import ui.elements.GridPanel;
import ui.elements.MapGridButton;
import ui.elements.PlayfieldPanel;
import uilogic.GridButtonHandler;
import uilogic.GridPosition;
import uilogic.MapLayoutData;

import java.awt.GridBagLayout;

public class Main {

    public static void main(String[] args) throws Exception{
        //test(args);
        test2();
    }

    static void test2() throws Exception{
        var frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1500, 1000));

        var handler = new GridButtonHandler();
        var layout = new MapLayoutData(20, 5, "project/resources/img/2.jpg");
        var field = new PlayfieldPanel(layout, handler);

        frame.add(field);
        frame.revalidate();
        frame.pack();
        frame.setVisible(true);
    }

    public static void test(String[] args) throws Exception{
        var frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);

        var panel = new GridPanel();
        var gbc = new GridBagConstraints();
        var handler = new GridButtonHandler();

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 6; j++){
                gbc.gridx = i;
                gbc.gridy = j;
                var button = new DummyComponent(50, 50, gbc);
                //var button = new MapGridButton("HELLO", i, j, handler);
                panel.add(button, gbc, false);
            }
        }

        gbc.gridx = 3;
        gbc.gridy = 3;
        //panel.add(new MapGridButton("ASD", 3, 3, handler), gbc, true);
        panel.refresh();

        var img = new ImageIcon("project/resources/img/1.png");
        var label = new JLabel(img);
        //panel.getJPanel().add(label);

        frame.add(panel.getJPanel());

        frame.pack();
        frame.setVisible(true);
    }
}