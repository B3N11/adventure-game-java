import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

import game.utility.dice.Dice;
import game.utility.dice.DiceRoller;
import ui.*;
import ui.elements.DummyComponent;
import ui.elements.GridPanel;
import ui.elements.MapGridButton;
import uilogic.GridButtonHandler;
import uilogic.GridPosition;

import java.awt.GridBagLayout;

public class Main {

    public static void main(String[] args) throws Exception{
        test2();
    }

    static void test2() throws Exception{
         var frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);

        var panel1 = new JPanel();
        var img = new ImageIcon("project/resources/img/1.png");
        var label = new JLabel(img);
        panel1.add(label);

        var panel = new GridPanel();
        var gbc = new GridBagConstraints();
        var handler = new GridButtonHandler();

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 6; j++){
                gbc.gridx = i;
                gbc.gridy = j;
                //var button = new DummyComponent(1000, 1000, gbc);
                var button = new MapGridButton("HELLO", i, j, handler);
                panel.add(button, gbc, false);
            }
        }

        frame.add(panel1);


        frame.pack();
        frame.setVisible(true);

        Thread.sleep(5000);

        frame.remove(panel1);
        frame.add(panel.getJPanel());
        frame.repaint();
        frame.pack();
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
                var button = new DummyComponent(1000, 1000, gbc);
                //var button = new MapGridButton("HELLO", i, j, handler);
                panel.add(button, gbc, false);
            }
        }

        gbc.gridx = 3;
        gbc.gridy = 3;
        panel.add(new MapGridButton("ASD", 3, 3, handler), gbc, true);
        panel.refresh();

        var img = new ImageIcon("project/resources/img/1.png");
        var label = new JLabel(img);
        //panel.getJPanel().add(label);

        frame.add(panel.getJPanel());

        frame.pack();
        frame.setVisible(true);
    }
}