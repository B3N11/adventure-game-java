package uilogic;

import java.awt.GridBagConstraints;

import exception.general.ArgumentNullException;

public class GridPosition {
    
    private int x;
    private int y;

    public GridPosition(){
        this(0,0);
    }

    public GridPosition(int horizontal, int vertical){
        x = horizontal;
        y = vertical;
    }

    public GridPosition(GridBagConstraints gbc){
        this(gbc.gridx, gbc.gridy);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean equals(GridPosition cmp){
        return (x == cmp.x) && (y == cmp.y);
    }
}
