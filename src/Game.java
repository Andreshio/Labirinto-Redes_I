
public class Game {
	private Player[] players;
	private int numPlayers;
	private Maze maze;

	public Game(int mazeSize) throws Exception{
		this.players = new Player[4];
		this.numPlayers = 0;
		this.maze = new Maze(mazeSize);
		
		
		/*
		 * Inicia os 4 players, um em cada canto
		 * do labirinto.
		 * 
		 * Suas threads são iniciadas, e ficam
		 * na espera da conexão do cliente
		 * */
		this.addPlayer(0, 0);
		this.addPlayer(0, mazeSize-1);
		this.addPlayer(mazeSize-1, 0);
		this.addPlayer(mazeSize-1, mazeSize-1);
	}
	
	
	/*
	 * Retorna o id do primeiro player não conectado que encontrar
	 * */
	public int getFreePlayerId() {
		for(int i=0; i<4; i++) {
			if(this.players[i].isConnected() == false) {
				return i;										//O id dos jogadores é a posição deles no this.players;
			}
		}
		return -1;
	}
	
	
	/*
	 * Adiciona um jogador, o coloca no labirinto,
	 * e inicia sua thread
	 *
	 * */
	private void addPlayer(int x, int y) throws Exception{
		Player p = new Player(this, this.numPlayers, x, y);
		if(this.numPlayers < 4) {
			this.players[ this.numPlayers ] = p;
			numPlayers++;
			
			this.maze.changeTileValue(p, numPlayers);		//Coloca o id+1 do player no labirinto, em seu x e y
			
			p.start(); 										//inicia a thread ( método run() )
		}
	}
	public Maze getMaze(){
		return this.maze;
	}
	
	public String toString() {
		String out = this.maze.toString();
		out+= "&";
		for(int i=0; i<this.numPlayers; i++) {
			out += this.players[i].toString();
			out += i<this.numPlayers-1?"#":"";
		}
		return out;
	}
}
