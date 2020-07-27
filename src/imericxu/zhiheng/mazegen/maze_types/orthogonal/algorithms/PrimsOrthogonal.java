package imericxu.zhiheng.mazegen.maze_types.orthogonal.algorithms;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.MazeOrthogonal;

import java.util.ArrayList;
import java.util.Random;

/**
 * Uses Prim's algorithm to generate orthogonal mazes
 */
public class PrimsOrthogonal extends MazeOrthogonal
{
    /**
     * Cells surrounding the already explored cells
     */
    private final ArrayList<OCell> frontiers;
    
    /**
     * Generates a rectangular maze
     */
    public PrimsOrthogonal(int rows, int cols)
    {
        super(rows, cols);
        frontiers = new ArrayList<>();
        addFrontiersOf(start);
    }
    
    @Override
    public boolean step()
    {
        if (!frontiers.isEmpty())
        {
            OCell current = frontiers.remove(r.nextInt(frontiers.size()));
            addFrontiersOf(current);
            ArrayList<OCell> choices = getChoices(current);
            OCell selected = choices.get(r.nextInt(choices.size()));
            removeWallsBetween(current, selected);
            
            return true;
        }
        else return false;
    }
    
    /**
     * Marks {@code cell} as visited.<br/>
     * Gets the neighbors of {@code cell} and adds them to
     * {@link #frontiers} if not already added and the {@link OCell}
     * has not been visited
     */
    private void addFrontiersOf(OCell cell)
    {
        cell.visited();
        cell.setDisplay(Cell.Display.SHOW);
        
        for (OCell neighbor : getNeighbors(cell))
        {
            if (neighbor == null) continue;
            if (!frontiers.contains(neighbor) && neighbor.getVisited() == 0)
            {
                frontiers.add(neighbor);
                neighbor.setDisplay(Cell.Display.COLOR_1);
            }
        }
    }
    
    /**
     * Gets the neighbors of {@code current} and adds if the
     * {@link OCell} has previously been explored
     *
     * @param current the current frontier
     * @return an ArrayList containing possible {@link OCell}s {@code current} can connect to
     */
    private ArrayList<OCell> getChoices(OCell current)
    {
        var visited = new ArrayList<OCell>();
        for (OCell cell : getNeighbors(current))
        {
            if (cell != null && cell.getVisited() >= 1)
            {
                visited.add(cell);
            }
        }
        return visited;
    }
}
