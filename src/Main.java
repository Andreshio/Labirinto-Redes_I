import javax.swing.JFrame;
import javax.swing.JTextField;

public class Main {

	public static void main(String[] args) {
		
		Game g = new Game(7);
		//Cria os jogadores com as posições, e os adiciona ao jogo;
		g.addPlayer(1, 2);
		g.addPlayer(4, 4);
		g.addPlayer(4, 0);
		
		//É necessária uma janela para ser o foco do teclado
		JFrame aWindow = new JFrame("Labirinto");
		aWindow.setBounds(50, 100, 300, 300);
		aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Ativa o mapa como um listener, assim a
		// função keyPressed será chamada adequadamente
		JTextField typingArea = new JTextField(20);
		typingArea.addKeyListener(g);
		
		
		aWindow.add(typingArea);
	    aWindow.setVisible(true);
	}

}
