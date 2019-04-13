import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;

public class KeySender implements KeyListener{
	Socket socket;
	DataOutputStream output;										// o ideal seria o ObjectOutputStream
	
	public KeySender(Socket socket, DataOutputStream output) {
		this.socket = socket;
		this.output = output;
	}

	public void keyPressed(KeyEvent event) {	
		int code = event.getKeyCode();
		System.out.println(code);
		try {
			if(code==32) {
				this.socket.close();
			} else {
	        	output.writeBytes(code + "\n");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//Métodos Necessários pois a
	//classe extende KeyListener
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
