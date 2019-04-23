import javax.swing.*;
import java.awt.*;
@SuppressWarnings("serial")
public class DrawTools extends JPanel {

	// Matriz do labirinto
	private int[][] maze;
	
	// Matrix com os pontos que criam as paredes dos labirintos
	//Cada linha tem 4 numeros, xy do ponto inicial, xy do ponto final
	private int[][] wallPath;
	
	// Matriz com os pontos que � recebida da classe ClientReader
	private int[][] players;
	
	// Variavel que define o tamanho de cada quadrado do labirinto
	private int rectSize;
	
	private int thickness;
    private int margin;

	// Contrutor que recebe o tamanho de cada quadrado e o proprio labirinto
	public DrawTools(int rectSize, int[][] maze) {
		this.rectSize = rectSize;
		this.maze = maze;
		
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

		Color[][] playerColors = {
				{Color.BLUE, new Color(51,153,255), new Color(51,204,255)},
				{Color.RED, new Color(255, 51, 51), new Color(255,102,102)},
				{Color.GREEN, new Color(0,255,51), new Color(102,255,102)},
				{Color.BLACK, new Color(219,102,146), new Color(229,134,167)}
			};
		//new Color(208,65,126)
		// Laco que percorre o labirinto
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				
				int mValue = maze[i][j];
			
				if(mValue >=1 && mValue<=4) {
					drawSquare(i, j, playerColors[mValue-1][ this.players[mValue-1][2] ], g);
				} else {
				
					switch (mValue) {
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
				}
				/*
				 * Desenha a pontua��o
				 */
				g.setColor(Color.BLACK);
				g.drawString("PONTOS: ", 800, 15);
				g.drawString("PLAYER 1: " + players[0][1], 850, 30);
				g.drawString("PLAYER 2: " + players[1][1], 850, 45);
				g.drawString("PLAYER 3: " + players[2][1], 850, 60);
				g.drawString("PLAYER 4: " + players[3][1], 850, 75);

			}
		}
		
        drawWalls(g);
    }
    /*
     * Cria as paredes
     * Baseado no site:
     * https://dev.to/marksasp95/introducing-maze-generator-java-320g
     * */
	private void drawWalls(Graphics g) {
		g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        BasicStroke stroke = new BasicStroke(thickness);
        g2.setStroke(stroke);
        
        for (int i = 0; i < wallPath.length; i++) {
            g2.drawLine(wallPath[i][0] * (rectSize) + margin, 
                        wallPath[i][1] * (rectSize) + margin, 
                        wallPath[i][2] * (rectSize) + margin, 
                        wallPath[i][3] * (rectSize) + margin);
        }
	}
	

	private void drawSquare(int x, int y, Color color, Graphics g) {
		g.setColor(color);
		g.fillRect(y * rectSize + rectSize/4, x * rectSize+rectSize/4, rectSize/2, rectSize/2);
	}

	private void drawCircle(int x, int y, Color color, Graphics g) {
		g.setColor(color);
		g.fillOval(y * rectSize + rectSize/4, x * rectSize + rectSize/4, rectSize/2, rectSize/2);
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
		this.players = points;
		repaint();
	}
}
