import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

import game.utility.dice.Dice;
import game.utility.dice.DiceRoller;
import ui.*;
import uilogic.GridButtonHandler;
import uilogic.GridPosition;

import java.awt.GridBagLayout;

public class Main {

    public static void main(String[] args) throws Exception{
        test(args);
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
                var button = new MapGridButton("ASD", i, j, handler);
                gbc.gridx = i;
                gbc.gridy = j;
                panel.add(button, gbc);
            }
        }

        panel.removeAt(new GridPosition(3, 3));
        gbc.gridx = 3;
        gbc.gridy = 3;
        panel.add(new MapGridButton("HELLO", 3, 3, handler), gbc);
        panel.revalidate();
        panel.repaint();

        var img = new ImageIcon("project/resources/img/1.png");
        var label = new JLabel(img);
        //panel.add(label);

        frame.add(panel);

        frame.pack();
        frame.setVisible(true);
    }
}