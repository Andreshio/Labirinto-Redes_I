import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends Thread implements KeyListener{
	private Player[] players;
	private int numPlayers;
	private int[][] maze;
	
	//********************************************************************************//
	//******************************     tempor�rio     ******************************//
	//********************************************************************************//
	//C�digo que ir� para outras classes
	private int currentPlayer = 0;
	
	public void changePlayer() {
		if(currentPlayer<numPlayers-1) 	currentPlayer++;
		else							currentPlayer=0;
	}
	
	// Quando KeyPressed for chamada no main
	// ela ir� identificar a tecla clicada
	// e chamar a fun��o adequada
	//
	//currentPlayer ser� o valor da thread
	//que chamou
	public void keyPressed(KeyEvent e) {	
		switch(e.getKeyCode()) {
			case 32: //Barra de espa�o
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
	//M�todos Necess�rios pois a
	//classe extende KeyListener
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	
	//********************************************************************************//
	//******************************   Fim Tempor�rio   ******************************//
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
	 * Fun��es de movimenta��o
	 * 
	 * se o movimento for permitido, 
	 * 		limpar o rastro anterior no labirinto
	 * 		atualizar a posi��o do jogador
	 * 		atualizar o labirinto com a nova posi��o do jogador
	 * 
	 * 	*/
	/*
	 * todo:
	 * 	No teste do if existir� o m�todo que diz se o movimento � v�lido
	 * 	no corpo do if existir� o m�todo que diz se o movimento trar� pontua��o
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
