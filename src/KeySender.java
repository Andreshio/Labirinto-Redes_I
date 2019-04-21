import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;

public class KeySender implements KeyListener{
	Socket socket;
	DataOutputStream output;										// o ideal seria o ObjectOutputStream
	
	public KeySender(DataOutputStream output) {
		this.output = output;
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
		//System.out.println(code + " PRESSED");
		try {
			
	        output.writeBytes(code + " true\n");
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void keyReleased(KeyEvent event) {	
		int code = event.getKeyCode();
		//System.out.println(code + " RELEASED");
		try {
			
	        output.writeBytes(code + " false\n");
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void keyTyped(KeyEvent event) {}

}
