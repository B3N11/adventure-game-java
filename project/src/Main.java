import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.*;

import ui.elements.GridEntityComponent;
import ui.elements.PlayfieldPanel;
import uilogic.GridButtonHandler;
import uilogic.GridPosition;
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
        frame.add(field);

        var size = field.getComponentSize();
        var gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        var entity = new GridEntityComponent("punk-01", size.getHorizontal(), size.getVertical(), gbc).setImage("project/resources/img/characters/7.png");
        gbc.gridx = 3;
        gbc.gridy = 3;
        var entity2 = new GridEntityComponent("punk-02", size.getHorizontal(), size.getVertical(), gbc).setImage("project/resources/img/characters/6.png");
        field.addEntity(entity);
        field.addEntity(entity2);

        frame.revalidate();
        frame.pack();
        frame.setVisible(true);

        Thread.sleep(3000);

        field.replaceEntity(entity, new GridPosition(4, 3));
        frame.revalidate();
    }
}