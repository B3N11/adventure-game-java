import java.awt.Dimension;
import javax.swing.*;

import game.behaviour.abstracts.EnemyBehaviourController;
import game.behaviour.entities.enemy.Enemy;
import game.behaviour.entities.enemy.EnemyEntity;
import game.behaviour.entities.enemy.EnemyType;
import game.behaviour.entities.enemy.controller.RangerEnemyController;
import game.global.EnemyTypeStorage;
import ui.elements.GridEntityComponent;
import ui.elements.PlayFrame;
import ui.elements.PlayfieldPanel;
import ui.elements.UtilityButtonPanel;
import uilogic.GridButtonHandler;
import uilogic.GridPosition;
import uilogic.MapLayoutData;
import uilogic.UIHandler;

public class Main {

    public static void main(String[] args) throws Exception{
        //test();

        var uiHandler = new UIHandler();
        uiHandler.start();
    }

    static void test() throws Exception{
        var frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        var handler = new GridButtonHandler();
        var layout = new MapLayoutData(20, 11, "resources/img/maps/2.jpg");
        var field = new PlayfieldPanel(1200, 675).setMapLayout(layout, handler, false);
        frame.add(field);

        var size = field.getComponentSize();
        var gridPos = new GridPosition(6, 1);
        var entity = new GridEntityComponent("punk-01", size.getHorizontal(), size.getVertical(), gridPos).setImage("resources/img/characters/7.png");
        var gridPos2 = new GridPosition(3, 4);
        var entity2 = new GridEntityComponent("punk-02", size.getHorizontal(), size.getVertical(), gridPos2).setImage("resources/img/characters/6.png");
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