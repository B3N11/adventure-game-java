import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

import game.utility.dice.Dice;
import game.utility.dice.DiceRoller;
import ui.*;
import uilogic.GridButtonHandler;

import java.awt.GridBagLayout;

public class Main {

    public static void main(String[] args){
        var dice = new Dice(20);
        var result = dice.roll();
        System.out.println(result);
    }

    public static void test(String[] args){
        var frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);

        var panel = new JPanel(new GridBagLayout());
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

        var img = new ImageIcon("project/resources/img/1.png");
        var label = new JLabel(img);
        panel.add(label);

        frame.add(panel);

        frame.pack();
        frame.setVisible(true);
    }
}