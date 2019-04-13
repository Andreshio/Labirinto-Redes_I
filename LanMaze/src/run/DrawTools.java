package run;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawTools extends JPanel{
	
	private int[][] maze;
	private int rectSize;

	public DrawTools(int rectSize, int[][] maze) {
		this.rectSize = rectSize;
		this.maze = maze;
	}
	
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        //for (Rectangle r : rectMaze) {
        
        for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				
				if(maze[i][j]>0) {
					
					switch (maze[i][j]) {
					case 1:
						g.setColor(Color.BLUE);
						break;
					case 2:
						g.setColor(Color.RED);
						break;
					case 3:
						g.setColor(Color.GREEN);
						break;
					case 4:
						g.setColor(Color.YELLOW);
						break;
					case 9:
						g.setColor(Color.BLACK);
						break;

					default:
						break;
					}
					
					g.fillRect(j*rectSize,i*rectSize,rectSize,rectSize);
				}
			}
		}
    }
    
    public void swapCell(int x, int y, int type) {
    	maze[x][y] = type;
    	repaint();
    }
    
    public void updateDrawMaze(int[][] updatedMaze) {
    	maze=updatedMaze;
    	repaint();
    }
    
    //public void addRectangle(Rectangle rect) {
    //	//rectMaze.add(rect);
    	//maze[][]
    //	repaint();
    //}
	
	public void removeRectangle(int x, int y) {
		
		
	}
	
	
	
	
	
	
	
	
	
}
