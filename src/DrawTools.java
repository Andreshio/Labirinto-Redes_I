import javax.swing.*;
import java.awt.*;
@SuppressWarnings("serial")
public class DrawTools extends JPanel{
	
	// Matriz do labirinto
	private int[][] maze;
	
	private int[][] wallPath;
	
	// Variavel que define o tamanho de cada quadrado do labirinto
	private int rectSize;
	
	private int dims;
    private int thickness;
    private int margin;
	
	//Contrutor que recebe o tamanho de cada quadrado e o proprio labirinto
	public DrawTools(int rectSize, int[][] maze) {
		this.rectSize = rectSize;
		this.maze = maze;
		
		this.dims = rectSize;
		this.thickness = 5;
		this.margin = 3;
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
				
				g.fillRect(j*rectSize + rectSize/4  ,i*rectSize + rectSize/4 ,rectSize/2,rectSize/2);
			}
		}
		
        Graphics2D g2 = (Graphics2D) g;
        BasicStroke stroke = new BasicStroke(thickness);
        g2.setStroke(stroke);
        
        for (int i = 0; i < wallPath.length; i++) {
            g2.drawLine(wallPath[i][0] * (900/7) + margin, 
                        wallPath[i][1] * (900/7) + margin, 
                        wallPath[i][2] * (900/7) + margin, 
                        wallPath[i][3] * (900/7) + margin);
        }
    }
    
    // Troca um valor por outro - NAO UTILIZADO
    public void swapCell(int x, int y, int type) {
    	maze[x][y] = type;
    	repaint();
    }
    
    // Atualiza o labirinto a partir do labirinto recebido por parametro
    public void updateDrawMaze(int[][] updatedMaze, int[][] wallPath) {
    	this.maze=updatedMaze;
    	this.wallPath = wallPath;
    	repaint();
    }
}
