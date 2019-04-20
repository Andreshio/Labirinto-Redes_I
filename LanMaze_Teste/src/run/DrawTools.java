package run;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawTools extends JPanel{
	
	// Matriz do labirinto
	private int[][] maze;
	// Variavel que define o tamanho de cada quadrado do labirinto
	private int rectSize;
	
	//Contrutor que recebe o tamanho de cada quadrado e o proprio labirinto
	public DrawTools(int rectSize, int[][] maze) {
		this.rectSize = rectSize;
		this.maze = maze;
	}
	
	// Desenha o labirinto conforme cada elemento numerico
	// Ex. 
	// 1 = Azul; 2 = Vermelho; 3 = Verde; 4 = Rosa; 9 = Preto; 0 = Branco
	// Outros Valores Positivos = Amarelo
	// Outros Valores Negativos = Cinza
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        // Laco que percorre o labirinto
        for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				
				//if(maze[i][j]>0) {
					
				int mValue = maze[i][j];
				switch (mValue) {
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
					g.setColor(Color.MAGENTA);
					break;
				case 9:
					g.setColor(Color.BLACK);
					break;
				case 0:
					g.setColor(Color.WHITE);
					break;

				default:
					if(mValue>0)
						g.setColor(Color.YELLOW);
					else
						g.setColor(Color.GRAY);
					break;
				}
				
				g.fillRect(j*rectSize,i*rectSize,rectSize,rectSize);
				//}
			}
		}
    }
    
    // Troca um valor por outro - NAO UTILIZADO
    public void swapCell(int x, int y, int type) {
    	maze[x][y] = type;
    	repaint();
    }
    
    // Atualiza o labirinto a partir do labirinto recebido por parametro
    public void updateDrawMaze(int[][] updatedMaze) {
    	maze=updatedMaze;
    	repaint();
    }
}
