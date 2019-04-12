import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends Thread implements KeyListener{
	private Player[] players;
	private int numPlayers;
	private int[][] maze;
	
	//********************************************************************************//
	//******************************     temporário     ******************************//
	//********************************************************************************//
	//Código que irá para outras classes
	private int currentPlayer = 0;
	
	public void changePlayer() {
		if(currentPlayer<numPlayers-1) 	currentPlayer++;
		else							currentPlayer=0;
	}
	
	// Quando KeyPressed for chamada no main
	// ela irá identificar a tecla clicada
	// e chamar a função adequada
	//
	//currentPlayer será o valor da thread
	//que chamou
	public void keyPressed(KeyEvent e) {	
		switch(e.getKeyCode()) {
			case 32: //Barra de espaço
				changePlayer();
				break;
			case 37:
				goLeft(currentPlayer);
				break;
			case 38: 
				goUp(currentPlayer);
				break;
			case 39:
				goRight(currentPlayer);
				break;
			case 40:
				goDown(currentPlayer);
				break;
		}
		System.out.println(this);
	}
	//Métodos Necessários pois a
	//classe extende KeyListener
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	
	//********************************************************************************//
	//******************************   Fim Temporário   ******************************//
	//********************************************************************************//

	
	
	public Game() {
		this.players = new Player[4];
		this.numPlayers = 0;
		this.maze = new int[5][5];
	}
	
	
	/*
	 * Adiciona um jogador, e o coloca no labirinto;
	 * */
	public void addPlayer(Player p) {
		if(this.numPlayers < 4) {
			this.players[ this.numPlayers ] = p;
			numPlayers++;
			
			this.maze[ p.getX() ][ p.getY() ] = numPlayers;
			
			System.out.println(this);
		}
	}
	
	
	/*
	 * Funções de movimentação
	 * 
	 * se o movimento for permitido, 
	 * 		limpar o rastro anterior no labirinto
	 * 		atualizar a posição do jogador
	 * 		atualizar o labirinto com a nova posição do jogador
	 * 
	 * 	*/
	/*
	 * todo:
	 * 	No teste do if existirá o método que diz se o movimento é válido
	 * 	no corpo do if existirá o método que diz se o movimento trará pontuação
	 *
	 *	synchronized ?
	 * */
	
	public synchronized  void goLeft(int playerIndex) { 
		Player p = this.players[playerIndex];
		if(p.getX() > 0) {
			this.maze[ p.getX() ][ p.getY() ] = 0;
			p.decreaseX();
			this.maze[ p.getX() ][ p.getY() ] = this.currentPlayer+1;
		}
	}
	public synchronized void goRight(int playerIndex) 	{ 
		Player p = this.players[playerIndex];
		if(p.getX() < 4) {
			this.maze[ p.getX() ][ p.getY() ] = 0;
			p.increaseX();
			this.maze[ p.getX() ][ p.getY() ] = this.currentPlayer+1;
		}
	}
	public synchronized void goUp(int playerIndex) 	{ 
		Player p = this.players[playerIndex];
		if(p.getY() > 0) {
			this.maze[ p.getX() ][ p.getY() ] = 0;
			p.decreaseY();
			this.maze[ p.getX() ][ p.getY() ] = this.currentPlayer+1;
		}
	}
	public synchronized void goDown(int playerIndex) 	{ 
		Player p = this.players[playerIndex];
		if(p.getY() < 4) {
			this.maze[ p.getX() ][ p.getY() ] = 0;
			p.increaseY();
			this.maze[ p.getX() ][ p.getY() ] = this.currentPlayer+1;		
		}
	}
	
	
	
	public String toString() {
		String out = "\n";
		for(int i=0; i<5;i++) {
			for(int j=0; j<5; j++) {
				out += maze[j][i]; 			// J e I invertidos para que 
			}								// o X seja horizontal
			out += "\n";
		}
		out += "numplayers: " + this.numPlayers;
		out += "\ncurrentPlayer: " + this.currentPlayer;
		return out + "\n";
	}
}
