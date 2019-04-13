import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;

public class KeySender implements KeyListener{
	Socket socket;
	DataOutputStream output;										// o ideal seria o ObjectOutputStream
	
	BufferedReader input;
	
	public KeySender(Socket socket, DataOutputStream output, BufferedReader input) {
		this.socket = socket;
		this.output = output;
		this.input = input;
	}
	/*
	 * Quando uma tecla é pressionada pelo
	 * cliente este método é chamado.
	 * 
	 * Envia o código da tecla para a Thread
	 * da classe Player
	 * */
	public void keyPressed(KeyEvent event) {	
		int code = event.getKeyCode();
		System.out.println(code);
		try {
			if(code==32) {
				this.socket.close();
			} else {
	        	output.writeBytes(code + "\n");
			}
			System.out.println(this.input.readLine());			//COMMAND RECEIVED
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//Métodos Necessários pois a
	//classe extende KeyListener
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
