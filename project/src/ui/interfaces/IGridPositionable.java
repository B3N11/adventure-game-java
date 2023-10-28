package ui.interfaces;

import exception.general.ArgumentNullException;
import uilogic.GridPosition;

public interface IGridPositionable {
    public GridPosition getGridPosition();
    public GridPosition setGridPosition(GridPosition newPosition) throws ArgumentNullException;
}
