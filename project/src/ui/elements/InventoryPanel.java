package ui.elements;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import game.utility.dataclass.MapLayoutData;
import ui.data.GridDimension;
import ui.data.GridPosition;

public class InventoryPanel extends JPanel{

    public static int COMPONENT_WIDTH = 48;
    public static int COMPONENT_HEIGHT = 48;

    private InteractiveGridPanel grid;
    
    public InventoryPanel(int width, int height, int rows, int columns, ActionListener buttonHandler) throws Exception{
        setBorder(BorderFactory.createTitledBorder("Inventory"));
        setUpContent(width, height, rows, columns, buttonHandler);
    }

    private void setUpContent(int width, int height, int rows, int columns, ActionListener buttonHandler) throws Exception{
        //SETUP: data columns fixed, rows depend on inventory size. playfield size horizontally: data.columns * griddimension, vertically: data.rows * griddimension
        int horizontalSize = columns * COMPONENT_WIDTH;
        int verticalSize = rows * COMPONENT_HEIGHT;
    
        var data = new MapLayoutData("", rows, columns, null, new GridPosition(0,0));
        grid = new InteractiveGridPanel(horizontalSize, verticalSize).setMapLayout(data, buttonHandler, false, new GridDimension(COMPONENT_WIDTH, COMPONENT_HEIGHT));

        var scrollbar = new JScrollPane(grid);
        scrollbar.setPreferredSize(new Dimension(width, height));
        scrollbar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollbar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollbar);
    }

    public InteractiveGridPanel getGrid() { return grid; }
}
