package Server;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class PlayerReader extends Thread{
	private Player player;
	private BufferedReader input;
	private boolean interrupted;
	
	public PlayerReader(Player player) {
		this.interrupted = false;
		this.player = player;
	}
	/*
	 * 
	 * Recebe as ações no teclado do cliente, e atualiza os valores
	 * do player no servidor
	 * 
	 * */
	public void run() {
		try { 
			Socket socket = player.getSocket();
	        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));		// O ideal seria o ObjectInputStream
	        
	        if(input.readLine().equals("START") ) {
	        	System.out.println("Player " + player.getGameId()+1 +" CONNECTED");
	        	this.player.setConnected(true);
	        	this.listen();
	        }		
		} catch(Exception e) {
			this.player.reestartConnection();
		}
	}
	public void close() {
		try {
			this.input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.interrupted = true;
	}
	public void listen() throws Exception{
		String command[];
		int key;
		boolean state;
		while(this.interrupted == false) {
			command = this.input.readLine().split(" ");
			
			key = Integer.parseInt( command[0] );
			state = Boolean.parseBoolean( command[1] );
			if(key>0 && key<1024) {
				player.setKeyState(key, state);
			}
		}
	}
}
