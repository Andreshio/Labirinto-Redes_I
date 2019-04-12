import java.io.*;
import java.net.*;

public class Player extends Thread { 
	private Game game;
	private int gameId;
	private int[] position;
	private int points;
	
	public Player(Game game, int id, int x, int y) {
		this.game = game;
		this.gameId = id;
		this.position = new int[2];
		this.position[0] = x;
		this.position[1] = y;
		this.points = 0;
	}
	
	public void run() {
		try {
	        ServerSocket socketRecepcao = new ServerSocket(6789);
	        Socket socketConexao = socketRecepcao.accept();
	        
	        ObjectInputStream input = new ObjectInputStream(socketConexao.getInputStream());

	        Integer move;
	        
	        while((move = (Integer) input.readObject())!=null) {
	        	Maze maze = this.game.getMaze();
	        	
	        	switch(move) {
				case 32: //Barra de espaço
					socketConexao.close();
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
	        	
	        	 move = 0;
	        	//System.out.println(this.game);
	        	
	        }
					
		} catch(Exception e) {
			System.out.println(e);
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
	
}
