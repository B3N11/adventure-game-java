package ui.interfaces;

import exception.general.ArgumentNullException;
import ui.data.GridPosition;

public interface IGridPositionable {
    public GridPosition getGridPosition();
    public GridPosition setGridPosition(GridPosition newPosition) throws ArgumentNullException;
}
