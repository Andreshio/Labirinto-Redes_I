package Server;

import java.util.LinkedList;

public class Game {
	private Player[] players;
	private int numPlayers;
	private Maze maze;

	public Game(int mazeSize) throws Exception{
		this.players = new Player[4];
		this.numPlayers = 0;
		this.maze = new Maze(mazeSize);
		
		LinkedList<int[]> outWalls = this.createPlayerObjectives(mazeSize);
		
		/*
		 * Inicia os 4 players, um em cada canto
		 * do labirinto.
		 * 
		 * Suas threads são iniciadas, e ficam
		 * na espera da conexão do cliente
		 * */
		this.addPlayer(0, 0, outWalls.get(0) );
		this.addPlayer(0, mazeSize-1, outWalls.get(1) );
		this.addPlayer(mazeSize-1, 0, outWalls.get(2) );
		this.addPlayer(mazeSize-1, mazeSize-1, outWalls.get(3) );
	}
	
	public LinkedList<int[]> createPlayerObjectives(int mazeSize) {
		LinkedList<int[]> outWalls = new LinkedList<int[]>();
		
		while(outWalls.size() < 4) {
			int[] w;
			int w0, w1, w2, w3;
			boolean newWall = false;
			
			while(newWall == false) {
				newWall =  true;
				w = createOutWall(mazeSize);
				System.out.println(outWalls.size());
				for(int i=0; i<outWalls.size(); i++) {
					w0 = outWalls.get(i)[0];
					w1 = outWalls.get(i)[1];
					w2 = outWalls.get(i)[2];
					w3 = outWalls.get(i)[3];
					
					if(w0==w[0] && w1==w[1] && w2==w[2] && w3==w[3]) 
						newWall = false;
				}
				System.out.println(w[0] + " "+w[1] +" "+ w[2] +" "+ w[3]);
				outWalls.add(w);
			}
		}
		return outWalls;
	}
	
	public int[] createOutWall(int mazeSize) {
		int[] wallPoints = new int[4];
		int side = (int)Math.floor( Math.random()*4 );
		int n = (int)Math.floor( Math.random()*mazeSize );
		
		if(side == 0) {
			wallPoints = new int[] {n, 0, n+1, 0};
		}
		if(side == 1) {
			wallPoints = new int[] {n, mazeSize, n+1, mazeSize};
		}
		if(side == 2) {
			wallPoints = new int[] {0, n, 0, n+1};
		}
		if(side == 3) {
			wallPoints = new int[] {mazeSize, n, mazeSize, n+1};
		}
		return wallPoints;
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
	private void addPlayer(int x, int y, int[] objective) throws Exception{
		Player p = new Player(this, this.numPlayers, x, y, objective);
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
