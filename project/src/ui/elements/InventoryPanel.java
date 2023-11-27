package ui.elements;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import file.elements.MapLayoutData;
import ui.data.GridDimension;
import ui.data.GridPosition;

public class InventoryPanel extends JPanel{

    public static int COMPONENT_WIDTH = 48;
    public static int COMPONENT_HEIGHT = 48;
    public static int PANEL_WIDTH = 300;
    public static int PANEL_HEIGHT = 500;

    private int rowCount;
    private int columnCount;

    private InteractiveGridPanel grid;
    
    public InventoryPanel(int rows, int columns, ActionListener buttonHandler) throws Exception{
        setBorder(BorderFactory.createTitledBorder("Inventory"));
        setUpContent(rows, columns, buttonHandler);
    }

    private void setUpContent(int rows, int columns, ActionListener buttonHandler) throws Exception{
        //SETUP: data columns fixed, rows depend on inventory size. playfield size horizontally: data.columns * griddimension, vertically: data.rows * griddimension
        int horizontalSize = columns * COMPONENT_WIDTH;
        int verticalSize = rows * COMPONENT_HEIGHT;
    
        var data = new MapLayoutData("", columns, rows, null, new GridPosition(0,0));
        grid = new InteractiveGridPanel(horizontalSize, verticalSize).setMapLayout(data, buttonHandler, false, new GridDimension(COMPONENT_WIDTH, COMPONENT_HEIGHT));

        var scrollbar = new JScrollPane(grid);
        scrollbar.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        scrollbar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollbar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollbar);

        rowCount = rows;
        columnCount = columns;
    }

    public InteractiveGridPanel getGrid() { return grid; }
    public int getRowCount() { return rowCount; }
    public int getColumnCount() { return columnCount; }
}
