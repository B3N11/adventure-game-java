package uilogic;

import exception.general.ArgumentNullException;

public interface IGridPositionable {
    public GridPosition getGridPosition();
    public GridPosition setGridPosition(GridPosition newPosition) throws ArgumentNullException;
}
