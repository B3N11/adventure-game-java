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
        frame.setSize(1500, 1000);
        frame.setLocationRelativeTo(null);

        var panel = new JPanel(new GridLayout(2,2));      
        var panel2 = new JPanel(new GridLayout(2, 2));

        //!!!!!!!!!!
        //Every map needs its own object that describes the rows, coloumns and the name of its image

        for(int i = 0; i < 4; i++){
            var button = new JButton("Button " + i);
            var button2 = new JButton("Button2 " + i);
            button.setOpaque(true);
            button.setContentAreaFilled(false);
            button.setBorderPainted(true);

            panel.add(button);
            panel2.add(button2);
        }

        //frame.add(panel, BorderLayout.SOUTH);
        //frame.add(panel2, BorderLayout.NORTH);

        var image = new ImageIcon("1.png");
        var label = new JLabel(image);
        var panel3 = new JPanel(new GridLayout(1, 1, 0, 0));
        panel3.add(label);
        frame.add(panel3, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }
}