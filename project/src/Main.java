import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.*;

import ui.elements.GridEntityComponent;
import ui.elements.PlayfieldPanel;
import uilogic.GridButtonHandler;
import uilogic.MapLayoutData;

public class Main {

    public static void main(String[] args) throws Exception{
        test();
    }

    static void test() throws Exception{
        var frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1500, 1000));

        var handler = new GridButtonHandler();
        var layout = new MapLayoutData(20, 11, "project/resources/img/maps/2.jpg");
        var field = new PlayfieldPanel(layout, handler);

        var size = field.getComponentSize();
        var gbc = new GridBagConstraints();
        gbc.gridx = 12;
        gbc.gridy = 6;
        var entity = new GridEntityComponent("punk-01", size.getHorizontal(), size.getVertical(), gbc).setImage("project/resources/img/characters/7.png");
        gbc.gridx = 11;
        gbc.gridy = 7;
        var entity2 = new GridEntityComponent("punk-02", size.getHorizontal(), size.getVertical(), gbc).setImage("project/resources/img/characters/6.png");
        field.addEntity(entity);
        field.addEntity(entity2);

        frame.add(field);
        frame.revalidate();
        frame.pack();
        frame.setVisible(true);
    }
}