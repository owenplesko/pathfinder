import java.util.*;
import java.awt.Point;

public class Algorithms {

    public static int[][] randomTileGrid(int width, int height) {
        int[][] grid = new int[height][width];
        Random rand = new Random(System.currentTimeMillis());

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(rand.nextInt(3) != 0)
                    grid[y][x] = 1;
            }
        }

        return grid;
    }

    public static int[][] mazeTileGrid(int width, int height) {
        int[][] grid = new int[height][width];
        Stack<Point> liveCells = new Stack<>();
        ArrayList<Point> neighbors = new ArrayList<>();
        Random rand = new Random(System.currentTimeMillis());

        // start maze
        liveCells.push(new Point(0, 0));
        grid[0][0] = 1;

        // populate maze
        while(!liveCells.empty()) {
            neighbors.clear();
            Point cell = liveCells.peek();

            // get neighbors
            if(cell.y >= 2 && grid[cell.y - 2][cell.x] == 0)
                neighbors.add(new Point(cell.x, cell.y - 2));
            if(cell.y < height - 2 && grid[cell.y + 2][cell.x] == 0)
                neighbors.add(new Point(cell.x, cell.y + 2));
            if(cell.x >= 2 && grid[cell.y][cell.x - 2] == 0)
                neighbors.add(new Point(cell.x - 2, cell.y));
            if(cell.x < width - 2 && grid[cell.y][cell.x + 2] == 0)
                neighbors.add(new Point(cell.x + 2, cell.y));

            // grow to neighbor or kill cell
            if(!neighbors.isEmpty()) {
                Point neighbor = neighbors.get(rand.nextInt(neighbors.size()));
                grid[neighbor.y][neighbor.x] = 1;
                grid[(cell.y + neighbor.y) / 2][(cell.x + neighbor.x) / 2] = 1;
                liveCells.push(neighbor);
            }
            else {
                liveCells.pop();
            }
        }
        return grid;
    }

    // A* algorithm
    public static List<Point> getPath(int[][] grid, Point _start, Point _end) {
        Point start = new Point(_start);
        Point end   = new Point(_end);
        int gridWidth  = grid[0].length;
        int gridHeight = grid.length;

        class Node {
            Point point;
            Node parent;
            int g, h, f;

            Node(Point point) {
                this.point = point;
                parent = null;
                g = 0;
                h = Math.abs(point.x - end.x) + Math.abs(point.y - end.y);
                f = h;
            }

            int getX() {return point.x;}
            int getY() {return point.y;}

            void setParent(Node parent) {
                this.parent = parent;
                g = parent.g + 1;
                f = g + h;
            }
        }

        Set<Point> expanded = new HashSet<>();
        Map<Point, Node> expandable = new HashMap<>();
        PriorityQueue<Node> expansionQueue = new PriorityQueue<>((n1, n2) -> {
            if(n1.f == n2.f)
                return n1.g - n2.g;
            return n1.f - n2.f;
        });

        // start pathfinding
        Node startNode = new Node(start);
        expandable.put(startNode.point, startNode);
        expansionQueue.add(startNode);

        // pathfinding loop
        while(!expansionQueue.isEmpty() && !expansionQueue.peek().point.equals(end)) {
            // expand lowest f cost node
            Node n = expansionQueue.poll();
            expanded.add(n.point);
            
            // explore neighbors
            Point up = new Point(n.getX(), n.getY() - 1);
            if(up.getY() >= 0 && grid[up.y][up.x] != 0 && !expanded.contains(up)) {
                if(expandable.containsKey(up)) {
                    Node neighbor = expandable.get(up);
                    if(neighbor.g > n.g + 1) {
                        neighbor.setParent(n);
                        expansionQueue.remove(neighbor);
                        expansionQueue.add(neighbor);
                    }
                }
                else {
                    Node neighbor = new Node(up);
                    neighbor.setParent(n);
                    expandable.put(up, neighbor);
                    expansionQueue.add(neighbor);
                }
            }
            Point down = new Point(n.getX(), n.getY() + 1);
            if(down.getY() < gridHeight && grid[down.y][down.x] != 0 && !expanded.contains(down)) {
                if(expandable.containsKey(down)) {
                    Node neighbor = expandable.get(down);
                    if(neighbor.g > n.g + 1) {
                        neighbor.setParent(n);
                        expansionQueue.remove(neighbor);
                        expansionQueue.add(neighbor);
                    }
                }
                else {
                    Node neighbor = new Node(down);
                    neighbor.setParent(n);
                    expandable.put(down, neighbor);
                    expansionQueue.add(neighbor);
                }
            }
            Point left = new Point(n.getX() - 1, n.getY());
            if(left.getX() >= 0 && grid[left.y][left.x] != 0 && !expanded.contains(left)) {
                if(expandable.containsKey(left)) {
                    Node neighbor = expandable.get(left);
                    if(neighbor.g > n.g + 1) {
                        neighbor.setParent(n);
                        expansionQueue.remove(neighbor);
                        expansionQueue.add(neighbor);
                    }
                }
                else {
                    Node neighbor = new Node(left);
                    neighbor.setParent(n);
                    expandable.put(left, neighbor);
                    expansionQueue.add(neighbor);
                }
            }
            Point right = new Point(n.getX() + 1, n.getY());
            if(right.getX() < gridWidth && grid[right.y][right.x] != 0 && !expanded.contains(right)) {
                if(expandable.containsKey(right)) {
                    Node neighbor = expandable.get(right);
                    if(neighbor.g > n.g + 1) {
                        neighbor.setParent(n);
                        expansionQueue.remove(neighbor);
                        expansionQueue.add(neighbor);
                    }
                }
                else {
                    Node neighbor = new Node(right);
                    neighbor.setParent(n);
                    expandable.put(right, neighbor);
                    expansionQueue.add(neighbor);
                }
            }
        }
        if(expansionQueue.isEmpty())
            return null;
        
        LinkedList<Point> path = new LinkedList<>();
        for(Node n = expansionQueue.peek(); n != null; n = n.parent)
            path.addFirst(n.point);
        return path;
    }
}
