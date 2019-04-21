import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReader extends Thread{
	private BufferedReader input;
	
	public ClientReader(BufferedReader input) {
		this.input = input;
	}
	/*
	 * Recebe dados do jogo enviados pela classe PlayerSender
	 * 
	 * os dados vem no formato:
	 * 
	 * matriz_labirinto + "&" + pontuação_players
	 * 
	 * as linhas da matriz_labirinto são separadas por "#"
	 * e suas colunas por " "
	 
	 * os jogadores da pontuação_players são separados por "#"
	 * 
	 * o seu id e sua pontuação separados por " "
	 * */
	public void run() {
		try { 
	        String line;
	        String[] aux;
			int[][] matrix;
			int[][] players;
			
			input.readLine();				// Lê a primeira linha vazia
			
	        while(true) {
				line = input.readLine();
				aux = line.split("&");
				
				matrix = parser( splitter(aux[0]) );
				players = parser( splitter(aux[1]) );
					
				System.out.println(line);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private String[][] splitter(String s){
		String lines[] = s.split("#");
		String matrix[][] = new String[ lines.length ][];
		
		for(int i=0; i<lines.length; i++) {
			matrix[i] = lines[i].split(" ");
		}
		return matrix;
	}
	private int[][] parser(String[][] sMatrix){
		int [][] matrix = new int[ sMatrix.length ][ sMatrix[0].length ];
		
		
		for(int i=0; i<sMatrix.length; i++) {
			for(int j=0; j<sMatrix[i].length; j++) {
				matrix[i][j] = Integer.parseInt( sMatrix[i][j] );
			}
		}
		return matrix;
	}
}
