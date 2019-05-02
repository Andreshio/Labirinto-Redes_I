package Server;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class PlayerSender extends Thread{
	private Player player;
	private DataOutputStream output;
	private boolean interrupted;
	
	public PlayerSender(Player player) {
		this.player = player;
		this.interrupted = false;
	}
	/*
	 * 
	 * Envia o estado do jogo para o cliente
	 * 
	 * */
	public void run() {
		try { 
			Socket socket = player.getSocket();
	        this.output = new DataOutputStream(socket.getOutputStream());
	        
	        while(this.interrupted == false) {
	        	output.writeBytes(this.player.getGame().toString());
	        	Thread.sleep(100);
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void close() {
		this.interrupted = true;
	}
}