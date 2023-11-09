package ui.data;

import java.awt.GridBagConstraints;

import exception.general.InvalidArgumentException;

public class GridPosition {
    
    private int x;
    private int y;

    public GridPosition() throws InvalidArgumentException{
        this(0,0);
    }

    public GridPosition(int x, int y) throws InvalidArgumentException{
        setPosition(x, y);
    }

    public GridPosition(GridBagConstraints gbc) throws InvalidArgumentException{
        this(gbc.gridx, gbc.gridy);
    }

    public GridBagConstraints getAsGridBagConstraints(){
        var result = new GridBagConstraints();
        result.gridx = x;
        result.gridy = y;
        return result;
    }

    public int getX(){ return x; }
    public int getY(){ return y; }

    public GridPosition setPosition(int x, int y) throws InvalidArgumentException{
        if(x < 0 || y < 0)
            throw new InvalidArgumentException();
        
        this.x = x;
        this.y = y;

        return this;
    }

    public boolean equals(GridPosition cmp){
        return (x == cmp.x) && (y == cmp.y);
    }
}
