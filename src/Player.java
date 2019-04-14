import java.io.*;
import java.net.*;
import java.awt.event.KeyEvent;

public class Player extends Thread { 
	private Game game;
	private boolean[] keys;
	private boolean connected;
	private int gameId;
	private int[] position;
	private int points;
	private PlayerReader reader;
	
	private ServerSocket server;
	private Socket socket;
	DataOutputStream output;
	
	public Player(Game game, int id, int x, int y) throws Exception{
		this.game = game;
		this.keys = new boolean[1024];
		this.connected = false;
		this.gameId = id;
		this.position = new int[2];
		this.position[0] = x;
		this.position[1] = y;
		this.points = 0;

		System.out.println("Player " + (id+1) + " waiting client");
		this.server = new ServerSocket(6789+id+1); 
	}
	
	/*
	 * 
	 * Espera a conexão do ClientMain
	 * e chama o método play()
	 * 
	 * */
	public void run() {
		try {
	        this.socket = this.server.accept();
			this.output = new DataOutputStream(this.socket.getOutputStream());
	        
			this.reader = new PlayerReader(this);
			this.reader.start();
			
			Maze maze = this.game.getMaze();
	       
	        
	        
	        /*
	         * Move o player de acordo
	         * com as teclas pressionadas;
	         * */
	        while(true) {
	        	if(this.connected) {
					if( this.keys[ KeyEvent.VK_UP ] ) {
						System.out.println("up");
						maze.goUp(this);
					}
					if( this.keys[ KeyEvent.VK_DOWN ] ) {
						maze.goDown(this);
					}
					if( this.keys[ KeyEvent.VK_LEFT ] ) {
						maze.goLeft(this);
					}
					if( this.keys[ KeyEvent.VK_RIGHT ] ) {
						maze.goRight(this);
					}
	        	}
			}
	        
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
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
		return "gameID: " + this.gameId + " points: " + this.points;
	}
	
}
