import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PathFinder extends JFrame implements KeyListener {
    TileGrid tileGridPanel;

    PathFinder() {
        this.setTitle("Path Finder");
        this.addKeyListener(this);
        tileGridPanel = new TileGrid(Algorithms.mazeTileGrid(99, 99));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(tileGridPanel);
        this.pack();
        this.setLocationByPlatform(true);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'm')
            tileGridPanel.setGrid(Algorithms.mazeTileGrid(99, 99));
        if(e.getKeyChar() == 'r')
            tileGridPanel.setGrid(Algorithms.randomTileGrid(99, 99));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    public static void main(String[] args) {
        new PathFinder();
    }
}
