import javax.swing.*;
import java.util.*;

import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;  
import java.awt.event.MouseMotionListener;  
import java.awt.Dimension;

public class TileGrid extends JPanel implements MouseMotionListener{
    private int[][] grid;
    private int width, height;
    private int tileSize;
    private List<Point> path;

    TileGrid(int[][] grid) {
        width  = grid[0].length;
        height = grid.length;
        tileSize = 8;
        this.grid = grid;
        path = new LinkedList<Point>();
        this.addMouseMotionListener(this);
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
        this.path.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int y = 0; y < width; y++) {
            for(int x = 0; x < height; x++) {
                if(grid[y][x] == 1)
                    g.setColor(Color.WHITE);
                else
                    g.setColor(Color.BLACK);
                g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }
        g.setColor(Color.RED);
        for(Point p : path)
            g.fillRect(p.x * tileSize, p.y * tileSize, tileSize, tileSize);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width * tileSize, height * tileSize);
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX() / tileSize;
        int y = e.getY() / tileSize;
        if(grid[y][x] == 0)
            return;
        List<Point> _path = Algorithms.getPath(grid, new Point(0, 0), new Point(x, y));
        if(_path == null) return;
        this.path = _path;
        repaint();
    }
}
