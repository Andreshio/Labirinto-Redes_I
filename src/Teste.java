import javax.swing.JFrame;
import javax.swing.JTextField;


public class Teste {

	public static void main(String[] args) {
		int[] position = {1,1};
		Map m = new Map(position);
		
		//É necessária uma janela para ser o foco do teclado
		JFrame aWindow = new JFrame("Labirinto");
		aWindow.setBounds(50, 100, 300, 300);
		aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Ativa o mapa como um listener, assim a
		// função keyPressed será chamada adequadamente
		JTextField typingArea = new JTextField(20);
		typingArea.addKeyListener(m);
		
		
		aWindow.add(typingArea);
	    aWindow.setVisible(true);
	}

}
