package Client;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReader extends Thread {
	private BufferedReader input;
	private DrawTools dtools;
	private int [][] players;


	public ClientReader(BufferedReader input, DrawTools dtools) {
		this.input = input;
		this.dtools = dtools;
	}

	/*
	 * Recebe dados do jogo enviados pela classe PlayerSender
	 * 
	 * os dados vem no formato:
	 * 
	 * matriz_labirinto + "&" + pontua��o_players
	 * 
	 * as linhas da matriz_labirinto s�o separadas por "#" e suas colunas por " "
	 * 
	 * os jogadores da pontua��o_players s�o separados por "#"
	 * 
	 * o seu id e sua pontua��o separados por " "
	 */
	public void run() {
		try {
			String line;
			String[] aux;
			int[][] matrix;
			int[][] wallPath;
			
			input.readLine();				// L� a primeira linha vazia
			
	        while(true) {
				line = input.readLine();
				aux = line.split("&");
				
				System.out.println(aux[1]);
				
				matrix   = parser( splitter(aux[0]) );
				wallPath = parser( splitter(aux[1]) );
				
				/*id, pontos, wallRemovingIterations, objective coordinates (x y x y)*/
				players  = parser( splitter(aux[2]) );
				
				
				
				/*
				 * D� update no display
				 * */
				dtools.updateDrawMaze(matrix, wallPath, players);
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String[][] splitter(String s) {
		String lines[] = s.split("#");
		String matrix[][] = new String[lines.length][];

		for (int i = 0; i < lines.length; i++) {
			matrix[i] = lines[i].split(" ");
		}
		return matrix;
	}

	private int[][] parser(String[][] sMatrix) {
		int[][] matrix = new int[sMatrix.length][sMatrix[0].length];

		for (int i = 0; i < sMatrix.length; i++) {
			for (int j = 0; j < sMatrix[i].length; j++) {
				matrix[i][j] = Integer.parseInt(sMatrix[i][j]);
			}
		}
		return matrix;
	}
}
