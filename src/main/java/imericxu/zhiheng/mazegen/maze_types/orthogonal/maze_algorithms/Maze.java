package imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.Orthogonal;

import java.util.ArrayList;
import java.util.Random;

/**
 * A maze composed of squares
 */
public abstract class Maze extends Orthogonal
{
    protected final OCell[][] grid;
    /**
     * Random number generator (better than Math.random())
     */
    protected final Random r;
    
    /**
     * Generates a rectangular maze
     */
    protected Maze(int rows, int cols)
    {
        grid = new OCell[rows][cols];
        r = new Random();
        initializeGrid();
        randomizeStartAndEnd();
    }
    
    
    /* * * * * * * * * * * * * * * * * * * * *
    Methods
    * * * * * * * * * * * * * * * * * * * * */
    
    public OCell[][] getGrid()
    {
        return grid;
    }
    
    /**
     * @param OCell cell to get neighbors of
     * @return an array of cells on the four sides of this cell;
     * null if out of bounds
     */
    public ArrayList<OCell> getNeighbors(OCell OCell)
    {
        var neighbors = new ArrayList<OCell>();
        int row = OCell.getRow();
        int col = OCell.getCol();
        
        if (row > 0) neighbors.add(grid[row - 1][col]);
        if (row < grid.length - 1) neighbors.add(grid[row + 1][col]);
        if (col > 0) neighbors.add(grid[row][col - 1]);
        if (col < grid[0].length - 1) neighbors.add(grid[row][col + 1]);
        
        return neighbors;
    }
    
    protected void setWallsBetween(OCell c1, OCell c2, boolean hasWall)
    {
        if (c1.getRow() < c2.getRow())
        {
            c1.setWall(OCell.BOTTOM, hasWall);
            c2.setWall(OCell.TOP, hasWall);
        }
        else if (c1.getRow() > c2.getRow())
        {
            c1.setWall(OCell.TOP, hasWall);
            c2.setWall(OCell.BOTTOM, hasWall);
        }
        else if (c1.getCol() < c2.getCol())
        {
            c1.setWall(OCell.RIGHT, hasWall);
            c2.setWall(OCell.LEFT, hasWall);
        }
        else
        {
            c1.setWall(OCell.LEFT, hasWall);
            c2.setWall(OCell.RIGHT, hasWall);
        }
    }
    
    /* * * * * * * * * * * * * * * * * * * * *
    Helper Methods
    * * * * * * * * * * * * * * * * * * * * */
    
    private void initializeGrid()
    {
        for (int row = 0; row < grid.length; ++row)
        {
            for (int col = 0; col < grid[0].length; ++col)
            {
                grid[row][col] = new OCell(row, col);
            }
        }
    }
    
    private void randomizeStartAndEnd()
    {
        int sRow, sCol, eRow, eCol;
        int sWall, eWall;
    
        int random = r.nextInt(4);
        switch (random)
        {
        case 0 -> { // Up
            sRow = 0;
            sCol = r.nextInt(grid[0].length);
            sWall = OCell.TOP;
    
            eRow = grid.length - 1;
            eCol = r.nextInt(grid[0].length);
            eWall = OCell.BOTTOM;
        }
        case 1 -> { // Down
            sRow = grid.length - 1;
            sCol = r.nextInt(grid[0].length);
            sWall = OCell.BOTTOM;
    
            eRow = 0;
            eCol = r.nextInt(grid[0].length);
            eWall = OCell.TOP;
        }
        case 2 -> { // Left
            sRow = r.nextInt(grid.length);
            sCol = 0;
            sWall = OCell.LEFT;
    
            eRow = r.nextInt(grid.length);
            eCol = grid[0].length - 1;
            eWall = OCell.RIGHT;
        }
        case 3 -> { // Right
            sRow = r.nextInt(grid.length);
            sCol = grid[0].length - 1;
            sWall = OCell.RIGHT;
    
            eRow = r.nextInt(grid.length);
            eCol = 0;
            eWall = OCell.LEFT;
        }
        default -> throw new IllegalStateException("Unexpected value: " + random);
        }
        
        start = grid[sRow][sCol];
        end = grid[eRow][eCol];
        start.setWall(sWall, false);
        end.setWall(eWall, false);
    }
}