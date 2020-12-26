package imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.Maze;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BreadthFirstSearch extends Pathfinder
{
    private final Queue<Cell> queue;
    private final HashMap<Cell, Cell> cameFrom;
    private Cell[][] grid;
    private Cell previous;
    
    public BreadthFirstSearch()
    {
        super();
        queue = new LinkedList<>();
        cameFrom = new HashMap<>();
    }
    
    @Override
    public void setMaze(Maze maze)
    {
        super.setMaze(maze);
        grid = maze.getGrid();
        clearVisited(grid);
        queue.add(start);
        start.visited();
        start.setDisplay(Cell.Display.EXPLORE);
        changeList.push(start);
    }
    
    @Override
    public boolean step()
    {
        if (!queue.isEmpty())
        {
            var current = queue.poll();
            
            for (var neighbor : getNeighbors(current, grid))
            {
                if (neighbor.getVisited() == 0)
                {
                    neighbor.setDisplay(Cell.Display.EXPLORE);
                    changeList.push(neighbor);
    
                    cameFrom.put(neighbor, current);
                    if (neighbor == end)
                    {
                        path = reconstructPath(neighbor);
                        return true;
                    }
    
                    queue.add(neighbor);
                    neighbor.visited();
                }
            }
            return false;
        }
        else return true;
    }
    
    private Stack<Cell> reconstructPath(Cell current)
    {
        var path = new Stack<Cell>();
        path.push(current);
        
        while (cameFrom.containsKey(current))
        {
            current = cameFrom.get(current);
            path.push(current);
        }
        
        return path;
    }
}
