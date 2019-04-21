import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class PlayerReader extends Thread{
	private Player player;
	private BufferedReader input;
	
	public PlayerReader(Player player) {
		this.player = player;
	}
	
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
			e.printStackTrace();
		}
	}
	public void listen() throws Exception{
		String command[];
		int key;
		boolean state;
		while(true) {
			command = this.input.readLine().split(" ");
			
			key = Integer.parseInt( command[0] );
			state = Boolean.parseBoolean( command[1] );
			if(key>0 && key<1024) {
				player.setKeyState(key, state);
			}
		}
	}
}
