import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class ClientMain {

	public static void main(String[] args) throws Exception{
		
		Socket socket = new Socket("127.0.0.1", 6789);
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		
		ClientPlayer cp = new ClientPlayer(socket, output);
		
		JFrame aWindow = new JFrame("Labirinto");
		aWindow.setBounds(50, 100, 300, 300);
		aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Ativa o mapa como um listener, assim a
		// função keyPressed será chamada adequadamente
		JTextField typingArea = new JTextField(20);
		typingArea.addKeyListener(cp);
		
		
		aWindow.add(typingArea);
	    aWindow.setVisible(true);
	}
}


