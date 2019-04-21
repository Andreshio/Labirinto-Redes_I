import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class PlayerSender extends Thread{
	private Player player;
	private DataOutputStream output;
	
	public PlayerSender(Player player) {
		this.player = player;
	}
	
	public void run() {
		try { 
			Socket socket = player.getSocket();
	        this.output = new DataOutputStream(socket.getOutputStream());
	        
	        while(true) {
	        	output.writeBytes(this.player.getGame().toString());
	        	Thread.sleep(100);
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}