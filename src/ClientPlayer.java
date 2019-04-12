import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientPlayer implements KeyListener{
	Socket socket;
	ObjectOutputStream output;
	
	public ClientPlayer(Socket socket, ObjectOutputStream output) {
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
				output.flush();
	        	output.writeObject(new Integer(32));
	        	output.flush();
	        	output.close();
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
