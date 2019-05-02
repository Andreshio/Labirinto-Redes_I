package Server;
import java.util.LinkedList;

public class Game {
	LinkedList<Player> players;
	private Maze maze;
	private int timeLeft;
	private int winner;

	public Game(int mazeSize) throws Exception{
		this.players = new LinkedList<Player>();
		this.maze = new Maze(mazeSize);
		this.timeLeft = 20;
		this.winner = -1;
		
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
		
		/*
		 * É necessário uma nova thread poins o méthodo
		 * sleep() iria atraplhar o inicio da comunicação
		 * com o client
		 * */
		Thread timeThread = new Thread(){
		    public void run(){  
		    	try {    
		    		while(timeLeft>0) {
		    			Thread.sleep(1000);
		    			timeLeft--;
		    		}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	endGame();
		    }
		  };

		  timeThread.start();
	}
	/*
	 * Cria objetivos para os jogadores.
	 * Objetivos são paredes que ao apertar espaço,
	 * o jogador ganha muitos pontos e é removido do jogo
	 * */
	public LinkedList<int[]> createPlayerObjectives(int mazeSize) {
		LinkedList<int[]> outWalls = new LinkedList<int[]>();
		
		while(outWalls.size() < 4) {
			int[] w;
			int w0, w1, w2, w3;
			boolean newWall = false;
			
			while(newWall == false) {
				newWall =  true;
				w = createOutWall(mazeSize);
				for(int i=0; i<outWalls.size(); i++) {
					w0 = outWalls.get(i)[0];
					w1 = outWalls.get(i)[1];
					w2 = outWalls.get(i)[2];
					w3 = outWalls.get(i)[3];
					
					if(w0==w[0] && w1==w[1] && w2==w[2] && w3==w[3]) 
						newWall = false;
				}
				outWalls.add(w);
			}
		}
		return outWalls;
	}
	
	public void removePlayerFromMaze(Player p) {
		this.maze.changeTileValue(p, 0);
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
	 * Retorna o índice do player com maior pontuação
	 * -1 se o jogo estiver em andamento
	 * -2 caso seja um empate
	 * */
	public void endGame() {
		int maxPointsPlayer = -1;
		int maxPoints = -1;
		boolean draw = false;
		for(int i = 0; i < 4; i++) {
			if(players.get(i).getPoints() == maxPoints) 
				draw = true;
			if(players.get(i).getPoints() > maxPoints) {
				maxPoints = players.get(i).getPoints();
				maxPointsPlayer = i;
				draw = false;
			}
		}
		Player p;
		for(int i = 0; i < 4; i++) {
			p = this.players.get(i);
			if(p.isPlaying()) {
				this.removePlayerFromMaze( p );
				p.exitMaze();
			}
		}
		
		if(draw)
			this.winner = -2;
		else 
			this.winner = maxPointsPlayer;
		
	}
	
	/*
	 * Retorna o id do primeiro player não conectado que encontrar
	 * */
	public int getFreePlayerId() {
		for(int i=0; i<4; i++) {
			if(this.players.get(i).isConnected() == false) {
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
		Player p = new Player(this, this.players.size(), x, y, objective);
		if(this.players.size() < 4) {
			this.players.add(p);
			
			this.maze.changeTileValue(p, this.players.size());		//Coloca o id+1 do player no labirinto, em seu x e y
			
			p.start(); 												//inicia a thread ( método run() )
		}
	}
	public Maze getMaze(){
		return this.maze;
	}
	/*
	 * Contém a informação relevante do 
	 * jogo. Será enviado para o client.
	 * */
	public String toString() {
		String out = this.maze.toString();
		out+= "&";
		for(int i=0; i<this.players.size(); i++) {
			out += this.players.get(i).toString();
			out += i<this.players.size()-1?"#":"";
		}
		out += "&" + this.timeLeft + " " + this.winner;
		return out;
	}
}
