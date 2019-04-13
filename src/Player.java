import java.io.*;
import java.net.*;

public class Player extends Thread { 
	private Game game;
	private boolean connected;
	private int gameId;
	private int[] position;
	private int points;
	
	private Socket socket;
	DataOutputStream output;
	BufferedReader input;
	
	public Player(Game game, int id, int x, int y) {
		this.game = game;
		this.connected = false;
		this.gameId = id;
		this.position = new int[2];
		this.position[0] = x;
		this.position[1] = y;
		this.points = 0;
	}
	
	/*
	 * 
	 * Espera a conecção do ClientMain
	 * e chama o método play()
	 * 
	 * */
	public void run() {
		try {
			System.out.println("rodando");
			int PORT = 6789+this.gameId+1;
			
	        this.socket = new ServerSocket(PORT).accept(); 
	        this.output = new DataOutputStream(this.socket.getOutputStream());
	        this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));		// O ideal seria o ObjectInputStream
	       
	        System.out.println("accepted");
	        
	        if( this.input.readLine().equals("START") ) {
	        	System.out.println("CONNECTED");
	        	this.connected = true;
	        	this.play();
	        }		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}  
	
	
	
	/*
	 * Médodo que recebe do socket a tecla
	 * clicada pelo cliente, (vinda do KeySender)
	 * e chama o método adequado
	 * */
	private void play() throws Exception{
		Maze maze = this.game.getMaze();
		Integer move;
        while(true) {
	        move = Integer.parseInt( this.input.readLine() );
	        
        	switch(move) {
			case 33: //Barra de espaço
				socket.close();
				break;
			case 37:
				maze.goLeft(this);
				break;
			case 38: 
				maze.goUp(this);
				break;
			case 39:
				maze.goRight(this);
				break;
			case 40:
				maze.goDown(this);
				break;
        	}
        	output.writeBytes("COMMAND RECEIVED\n");
        	
        	move = 0;
        	System.out.println(this.game);
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
	public boolean isConnected() {
		return this.connected;
	}
	
	public String toString() {
		return "gameID: " + this.gameId + " points: " + this.points;
	}
	
}
