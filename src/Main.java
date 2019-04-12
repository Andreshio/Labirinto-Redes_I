import javax.swing.JFrame;
import javax.swing.JTextField;

public class Main {

	public static void main(String[] args) {
		
		Game g = new Game();
		//Cria os jogadores com as posições, e os adiciona ao jogo;
		int[] p1pos = {1,1};
		int[] p2pos = {4,4};
		int[] p3pos = {0,4};
		Player p1 = new Player(p1pos);
		Player p2 = new Player(p2pos);
		Player p3 = new Player(p3pos);
		g.addPlayer(p1);
		g.addPlayer(p2);
		g.addPlayer(p3);
		
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
