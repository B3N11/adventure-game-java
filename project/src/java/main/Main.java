package main;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class Main {
    public static void main(String[] args) throws Exception {
        
        var frame = new JFrame();
        frame.setTitle("HELLO HELLO");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);

        var panel = new JPanel(new GridLayout(2,2, 10, 10));      

        //!!!!!!!!!!
        //Every map needs its own object that describes the rows, coloumns and the name of its image

        for(int i = 0; i < 4; i++){
            var button = new JButton("Button " + i);
            button.setOpaque(true);
            button.setContentAreaFilled(false);
            button.setBorderPainted(true);

            panel.add(button);
        }

        frame.add(panel, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }
}