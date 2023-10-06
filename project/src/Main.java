import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import ui.*;
import java.awt.GridBagLayout;

public class Main {

    static void test(){
        var frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);

        var panel = new JPanel(new GridBagLayout());
        var gbc = new GridBagConstraints();

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 6; j++){
                if(i % 2 == 0 || j % 3 == 0)
                    continue;

                var button = new MapGridButton("ASD", i, j);
                gbc.gridx = i;
                gbc.gridy = j;
                //panel.add(button, gbc);
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