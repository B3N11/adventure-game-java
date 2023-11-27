package main.ui.elements;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import main.file.elements.MapLayoutData;
import main.ui.data.GridDimension;
import main.uilogic.GridPosition;

/**
 * This class represents an inventory panel in the game. It uses the InteractiveGridPanel class to display the inventory.
 * It extends the JPanel class and is used to display the inventory area.
 * 
 * The class contains the following fields:
 * - COMPONENT_WIDTH: The width of each component in the inventory panel.
 * - COMPONENT_HEIGHT: The height of each component in the inventory panel.
 * - PANEL_WIDTH: The width of the inventory panel.
 * - PANEL_HEIGHT: The height of the inventory panel.
 * - rowCount: The number of rows in the inventory panel.
 * - columnCount: The number of columns in the inventory panel.
 * - grid: The interactive grid panel of the inventory panel.
 */
public class InventoryPanel extends JPanel{

    public static int COMPONENT_WIDTH = 48;
    public static int COMPONENT_HEIGHT = 48;
    public static int PANEL_WIDTH = 300;
    public static int PANEL_HEIGHT = 500;

    private int rowCount;
    private int columnCount;

    private InteractiveGridPanel grid;
    
    /**
     * Constructor for the InventoryPanel class.
     * Initializes the inventory panel with the specified number of rows and columns and the specified button handler.
     * @param rows The number of rows in the inventory panel.
     * @param columns The number of columns in the inventory panel.
     * @param buttonHandler The button handler of the inventory panel.
     * @throws Exception if the initialization of the inventory panel throws an Exception.
     */
    public InventoryPanel(int rows, int columns, ActionListener buttonHandler) throws Exception{
        setBorder(BorderFactory.createTitledBorder("Inventory"));
        setUpContent(rows, columns, buttonHandler);
    }

    /**
     * Sets up the content of the inventory panel.
     * Initializes the interactive grid panel with the specified number of rows and columns and the specified button handler, sets the preferred size and scroll bar policies of the scroll pane, and adds the scroll pane to the inventory panel.
     * @param rows The number of rows in the inventory panel.
     * @param columns The number of columns in the inventory panel.
     * @param buttonHandler The button handler of the inventory panel.
     * @throws Exception if the setting up of the content throws an Exception.
     */
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
