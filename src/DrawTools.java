import javax.swing.*;
import java.awt.*;
@SuppressWarnings("serial")
public class DrawTools extends JPanel {

	// Matriz do labirinto
	private int[][] maze;
	
	private int[][] wallPath;
	// Matriz com os pontos que é recebida da classe ClientReader
	private int[][] points;
	// Variavel que define o tamanho de cada quadrado do labirinto
	private int rectSize;
	
	private int thickness;
    private int margin;

	// Contrutor que recebe o tamanho de cada quadrado e o proprio labirinto
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

				int mValue = maze[i][j];
				switch (mValue) {
				case 1:
					drawSquare(i, j, Color.BLUE, g);
					break;
				case 2:
					drawSquare(i, j, Color.RED, g);
					break;
				case 3:
					drawSquare(i, j, Color.GREEN, g);
					break;
				case 4:
					drawSquare(i, j, Color.MAGENTA, g);
					break;
				case 9:
					drawSquare(i, j, Color.BLACK, g);
					break;
				case 0:
					break;

				default:
					if (mValue > 0)
						drawCircle(i, j, Color.YELLOW, g);
					else
						drawCircle(i, j, Color.GRAY, g);
					break;
				}

				g.setColor(Color.BLACK);
				g.drawString("PONTOS: ", 800, 15);
				g.drawString("PLAYER 1: " + points[0][1], 850, 30);
				g.drawString("PLAYER 2: " + points[1][1], 850, 45);
				g.drawString("PLAYER 3: " + points[2][1], 850, 60);
				g.drawString("PLAYER 4: " + points[3][1], 850, 75);

				//g.fillRect(j*rectSize + rectSize/4  ,i*rectSize + rectSize/4 ,rectSize/2,rectSize/2);
			}
		}
		
        g.setColor(Color.BLACK);
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
    

	private void drawSquare(int x, int y, Color color, Graphics g) {
		g.setColor(color);
		g.fillRect(y * rectSize, x * rectSize, rectSize, rectSize);
	}

	private void drawCircle(int x, int y, Color color, Graphics g) {
		g.setColor(color);
		g.fillOval(y * rectSize, x * rectSize, rectSize, rectSize);
	}

	// Troca um valor por outro - NAO UTILIZADO
	public void swapCell(int x, int y, int type) {
		maze[x][y] = type;
		repaint();
	}

	// Atualiza o labirinto a partir do labirinto recebido por parametro
	public void updateDrawMaze(int[][] updatedMaze, int[][] wallPath, int[][] points) {
		maze = updatedMaze;
		this.wallPath = wallPath;
		this.points = points;
		repaint();
	}
}
