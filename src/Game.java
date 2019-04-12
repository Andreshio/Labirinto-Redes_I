
public class Game {
	private Player[] players;
	private int numPlayers;
	private Maze maze;
	
	//********************************************************************************//
	//******************************     tempor�rio     ******************************//
	//********************************************************************************//
	//C�digo que ir� para outras classes
	
	private int currentPlayer = 0;
	
	public void changePlayer() {
		if(currentPlayer<numPlayers-1) 	currentPlayer++;
		else							currentPlayer=0;
	}
	
	//********************************************************************************//
	//******************************   Fim Tempor�rio   ******************************//
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
		Player p = new Player(this, this.numPlayers, x, y);
		if(this.numPlayers < 4) {
			this.players[ this.numPlayers ] = p;
			numPlayers++;
			
			this.maze.changeTileValue(p, numPlayers);
			
			p.start();
			System.out.println(this);
		}
	}
	public Maze getMaze(){
		return this.maze;
	}
	
	public String toString() {
		String out = this.maze.toString();
		out += "numplayers: " + this.numPlayers;
		out += "\ncurrentPlayer: " + this.currentPlayer;
		return out + "\n";
	}
}
