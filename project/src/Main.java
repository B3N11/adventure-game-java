import java.awt.Dimension;
import javax.swing.*;

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
        var layout = new MapLayoutData(20, 11, "project/resources/img/2.jpg");
        var field = new PlayfieldPanel(layout, handler);

        frame.add(field);
        frame.revalidate();
        frame.pack();
        frame.setVisible(true);
    }
}