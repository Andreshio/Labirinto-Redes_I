import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class ClientMain {

	public static void main(String[] args) throws Exception{
		
		// Serão usados pelo KeySender
		Socket socket = new Socket("127.0.0.1", 6789);
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		
		// Listener do teclado que envia as teclas clicadas
		// ao servidor pelo socket
		KeySender ks = new KeySender(socket, output);
		
		// É necessária uma janela para ser o foco do teclado
		JFrame aWindow = new JFrame("Labirinto");
		aWindow.setBounds(50, 100, 300, 300);
		aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Ativa o KeySender como um listener e o linka
		// à janela, assim a sua função keyPressed será 
		// chamada adequadamente
		JTextField typingArea = new JTextField(20);
		typingArea.addKeyListener(ks);
		
		aWindow.add(typingArea);
	    aWindow.setVisible(true);
	}
}


