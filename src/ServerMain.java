import java.io.*;
import java.net.*;

public class ServerMain {

	public static void main(String[] args ) throws Exception{
		Game g = new Game(7);
		
		ServerSocket server = new ServerSocket(6789); 
	  
		/*
		 * Espera a conexão do ClientMain
		 * 
		 * Envia a PORT do primeiro Player
		 * livre que encontrar
		 * 
		 * Envia -1 se não encontrar nenhum
		 *
		 * Fecha todas as comunicações com o
		 * ClientMain atual
		 * 
		 * Espera o próximo
		 * */
	    
		while(true) {
			Socket socket = server.accept();
			
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());	
			
			int playerId = g.getFreePlayerId();
			
			String line = input.readLine();
			
			if(line.equals("GET_PLAYER_PORT") && playerId != -1) {
				int newPort = 6789+playerId+1;
				output.writeBytes(newPort + "\n");
	        } else {
	        	output.writeBytes(-1 + "\n");
	        }
			
			output.close();
			input.close();
			socket.close();
		}
	}

}
