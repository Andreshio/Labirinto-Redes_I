package Server;
import java.io.*;
import java.net.*;
import java.awt.event.KeyEvent;

public class Player extends Thread { 
	private Game game;
	private boolean[] keys; // Teclas pressionadas
	private int gameId;
	private int[] position;
	private int points;
	private int[] objective;
	
	private int[] wallToRemove;
	private int wallRemovingIterations;
	private boolean playing;
	
	/*
	 * utilizado para interromper o while do
	 * m�todo connectAndPlay quando o jogador
	 * desconecta
	 * */
	private boolean interrupted;
	
	private boolean connected;
	private PlayerReader reader;
	private PlayerSender sender;

	
	private ServerSocket server;
	private Socket socket;
	DataOutputStream output;
	
	public Player(Game game, int id, int x, int y, int[] objective) throws Exception{
		this.game = game;
		this.keys = new boolean[1024];
		this.connected = false;
		this.gameId = id;
		this.position = new int[2];
		this.position[0] = x;
		this.position[1] = y;
		this.points = 0;
		this.objective = objective;
		this.playing = true;
		
		this.wallToRemove = null;
		this.wallRemovingIterations = 0;
		
		
		System.out.println("Player " + (id+1) + " waiting client");
		this.server = new ServerSocket(6789+id+1); 
	}
	
	public void run() {
		try {
			this.connectAndPlay();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 * Espera a conex�o do ClientMain
	 * 
	 * */
	public void connectAndPlay() throws IOException {
		try {
			/*
			 * Espera o Client
			 * */
	        this.socket = this.server.accept();
			this.output = new DataOutputStream(this.socket.getOutputStream());
	        
			/*
			 * 
			 * Inicializa as threads reader e sender.
			 * 
			 * */
			
			this.reader = new PlayerReader(this);
			this.reader.start();
			
			this.sender = new PlayerSender(this);
			this.sender.start();
			
			Maze maze = this.game.getMaze();
	       
	        boolean moved;
	        this.interrupted = false;
	        
	        /*
	         * Move o player de acordo
	         * com as teclas pressionadas;
	         * */
	        while(this.interrupted == false) {
	        	moved = false;
	        	Thread.sleep(50);
	        	if(this.connected) {
	        		if( this.keys[ KeyEvent.VK_UP ] && moved == false) {
						moved=maze.goUp(this, this.keys[ KeyEvent.VK_SPACE ]);
					}
	        		if( this.keys[ KeyEvent.VK_DOWN ] && moved == false) {
						moved=maze.goDown(this, this.keys[ KeyEvent.VK_SPACE ]);
					}
					if( this.keys[ KeyEvent.VK_LEFT ] && moved == false) {
						moved=maze.goLeft(this, this.keys[ KeyEvent.VK_SPACE ]);
					}
					if( this.keys[ KeyEvent.VK_RIGHT ] && moved == false) {
						moved=maze.goRight(this, this.keys[ KeyEvent.VK_SPACE ]);
					}
	        	}
	        	/*
	        	 * Limita o n�mero de movimenta��es
	        	 * */
	        	if(moved) {
	        		Thread.sleep(700);
	        	}
			}
	        
	        
		} catch(Exception e) {
			this.reestartConnection();
		}
	}  
	/*
	 * Chamado ao dar catch da excess�o na thread PlayerReader
	 * */
	public void reestartConnection() {
		try {
			this.output.close();
			this.reader.close();
			this.sender.close();
			this.socket.close();
		
			// Necess�rio para sair do while do m�todo connectAndPlay
			this.interrupted = true;
			// Necess�rio para voltar a ser conectado pela classe Game
			this.connected = false;
			
			this.connectAndPlay();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void resetRemovingWallIterations() {
		this.wallRemovingIterations=0;
	}
	/*
	 * E chamado multiplas vezes, acumulando wallRemovingIterations
	 * caso seja chamado para o mesmo linePoints consecutivamente.
	 * 
	 * wallRemovingInterations != 3 retorna false;
	 * wallRemovingIterations == 3 tira pontos e retorna true;
	 * 
	 * wallRemovingIterations e a base da cor do player
	 * */
	
	public boolean tick_removeWall(int[] linePoints) {
		if(this.wallToRemove == null) {
			this.wallToRemove = linePoints;
			this.wallRemovingIterations = 1;
		} else {
			boolean equal=true;
			for(int i=0;i<4;i++) {
				if(this.wallToRemove[i]!=linePoints[i]) {
					equal=false;
				}
			}
			if(equal) 	this.wallRemovingIterations++;
			else {		
				this.wallToRemove = linePoints;
				this.wallRemovingIterations=1;
			}
		}
		if(this.wallRemovingIterations==3) {
			this.changePoints(-15);
			this.wallToRemove = null;
			this.wallRemovingIterations = 0;
			return true;
		}
		return false;
	}
	
	/*
	 * Remove o Player do Labirinto;
	 * position -1 -1 o impedir� de se movimentar 
	 * */
	public void exitMaze() {
		this.position = new int[] {-1, -1};
		this.objective = new int[] {-1, -1, -1, -1};
		this.playing = false;
	}
	/*
	 * Caso o array de o for o objetivo, remover o player
	 * do jogo, e o dar pontos
	 * */
	public boolean testObjective(int[] o) {
		if(o[0]==objective[0] && o[1]==objective[1] && o[2]==objective[2] && o[3]==objective[3]) {
			this.game.removePlayerFromMaze(this);
			this.changePoints(150);
			return true;
		}
		return false;
	}
	
	public void changePoints(int points) {
		this.points += points;
	}
	public void decreaseX() {
		this.position[0]--;
	}
	public void increaseX() {
		this.position[0]++;
	}
	public void decreaseY() {
		this.position[1]--;
	}
	public void increaseY() {
		this.position[1]++;
	}
	public Game getGame() {
		return this.game;
	}
	public int getGameId() {
		return this.gameId;
	}
	public int getX() {
		return position[0];
	}
	public int getY() {
		return position[1];
	}
	public boolean isPlaying() {
		return this.playing;
	}
	public Socket getSocket() {
		return this.socket;
	}
	public boolean isConnected() {
		return this.connected;
	}
	public void setConnected(boolean c) {
		this.connected = c;
	}
	public void setKeyState(int key, boolean state) {
		this.keys[key] = state;
	}
	public int getPoints() {
		return points;
	}
	/*
	 * id pontos wallRemovingIterations x y x y
	 * 								   (objetivo)
	 * */
	public String toString() {
		String out = this.gameId + " " + this.points + " " + this.wallRemovingIterations +" ";
		
		for(int i=0; i<this.objective.length; i++) {
			out+=this.objective[i];
			out+= i < this.objective.length-1 ? " " : "";
		}
		
		return out;
	}
	
}
