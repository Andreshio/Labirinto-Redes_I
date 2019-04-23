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
	private PlayerSender sender;
	private long lastMove;
	
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
	        		if( this.keys[ KeyEvent.VK_UP ] ) {
						moved=maze.goUp(this);
					}
	        		if( this.keys[ KeyEvent.VK_DOWN ] ) {
						moved=maze.goDown(this);
					}
					if( this.keys[ KeyEvent.VK_LEFT ] ) {
						moved=maze.goLeft(this);
					}
					if( this.keys[ KeyEvent.VK_RIGHT ] ) {
						moved=maze.goRight(this);
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
		return this.gameId + " " + this.points;
	}
	
}
