package Server;
import java.io.*;
import java.net.*;
import java.awt.event.KeyEvent;

public class Player extends Thread { 
	private Game game;
	private boolean[] keys;
	private int gameId;
	private int[] position;
	private int points;
	private int[] objective;
	
	private int[] wallToRemove;
	private int wallRemovingIterations;
	
	private boolean connected;
	private PlayerReader reader;
	private PlayerSender sender;
	private long lastMove;
	
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
		
		this.wallToRemove = null;
		this.wallRemovingIterations = 0;
		this.lastMove = System.currentTimeMillis();
		
		
		System.out.println("Player " + (id+1) + " waiting client");
		this.server = new ServerSocket(6789+id+1); 
	}
	
	/*
	 * 
	 * Espera a conexão do ClientMain
	 * 
	 * */
	public void run() {
		try {
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
	        
	        /*
	         * Move o player de acordo
	         * com as teclas pressionadas;
	         * */
	        while(true) {
	        	moved = false;
	        	currentThread().sleep(50);
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
	        	 * Limita o número de movimentações
	        	 * */
	        	if(moved) {
	        		Thread.sleep(700);
	        	}
			}
	        
	        
		} catch(Exception e) {
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
	
	public boolean testObjective(int[] o) {
		System.out.println("\n\n\nobjective\n" + objective[0]+" "+objective[1] + " " + objective[2] + " " + objective[3]
				+"\n" + o[0] + " " + o[1] + " "+ o[2] + " " + o[3]);
		if(o[0]==objective[0] && o[1]==objective[1] && o[2]==objective[2] && o[3]==objective[3]) {
			return true;
		}
		return false;
	}
	
	public void changePoints(int points) {
		this.points += points;
		System.out.println("pontos: "+this.points+" ID: "+gameId);
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
	
	public String toString() {
		String out = this.gameId + " " + this.points + " " + this.wallRemovingIterations +" ";
		
		for(int i=0; i<this.objective.length; i++) {
			out+=this.objective[i];
			out+= i < this.objective.length-1 ? " " : "";
		}
		
		return out;
	}
	
}
