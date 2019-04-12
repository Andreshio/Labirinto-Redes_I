import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener{
	private Player[] players;
	private int numPlayers;
	private Maze maze;
	
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
	// e chamará a função adequada
	//
	//currentPlayer será o valor da thread
	//que chamou
	public void keyPressed(KeyEvent e) {	
		Player p = this.players[ this.currentPlayer ];
		switch(e.getKeyCode()) {
			case 32: //Barra de espaço
				changePlayer();
				break;
			case 37:
				this.maze.goLeft(p);
				break;
			case 38: 
				this.maze.goUp(p);
				break;
			case 39:
				this.maze.goRight(p);
				break;
			case 40:
				this.maze.goDown(p);
				break;
		}
		//clear console
		System.out.println(this);
	}
	//Métodos Necessários pois a
	//classe extende KeyListener
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	
	//********************************************************************************//
	//******************************   Fim Temporário   ******************************//
	//********************************************************************************//

	public Game(int mazeSize) {
		this.players = new Player[4];
		this.numPlayers = 0;
		this.maze = new Maze(mazeSize);
	}
	
	/*
	 * Adiciona um jogador, e o coloca no labirinto;
	 * */
	public void addPlayer(int x, int y) {
		Player p = new Player(this.numPlayers, x, y);
		if(this.numPlayers < 4) {
			this.players[ this.numPlayers ] = p;
			numPlayers++;
			
			this.maze.changeTileValue(p, numPlayers);
			
			System.out.println(this);
		}
	}
	
	public String toString() {
		String out = this.maze.toString();
		out += "numplayers: " + this.numPlayers;
		out += "\ncurrentPlayer: " + this.currentPlayer;
		return out + "\n";
	}
}
