import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Map implements KeyListener {
	private int[][] maze = new int[5][5]; 						// Inicia o labirinto completamente zerado;
	private int[] position;
	
	public Map(int[] position) {
		this.position = position;
		maze[ position[0] ][ position[1] ] = 1;					// A posição inicial é preenchida com 1
		
		System.out.println(this);								// Mostra o labirinto inicial
	}
	
	// Quando KeyPressed for chamada no main
	// ela irá identificar a tecla clicada
	// e chamar a função adequada
	
	public void keyPressed(KeyEvent e) {						
		switch(e.getKeyCode()) {
			case 37:
				goLeft();
				break;
			case 38: 
				goUp();
				break;
			case 39:
				goRight();
				break;
			case 40:
				goDown();
				break;
		}
		System.out.println(this);
	}
	/*
	 * Funções de movimentação
	 * 
	 * se o movimento for permitido, 
	 * 		limpar o rastro anterior no labirinto
	 * 		atualizar o valor
	 * 		atualizar o mapa com o novo valor
	 * 
	 * 	*/
	private void goLeft() 	{ 
		if(position[0] > 0) {
			maze[ position[0] ][ position[1] ] = 0;
			position[0]--;
			maze[ position[0] ][ position[1] ] = 1;
		}
	}
	private void goRight() 	{ 
		if(position[0] < 4) {
			maze[ position[0] ][ position[1] ] = 0;
			position[0]++;
			maze[ position[0] ][ position[1] ] = 1;
		}
	}
	private void goUp() 	{ 
		if(position[1] > 0) {
			maze[ position[0] ][ position[1] ] = 0;
			position[1]--;
			maze[ position[0] ][ position[1] ] = 1;
		}
	}
	private void goDown() 	{ 
		if(position[1] < 4) {
			maze[ position[0] ][ position[1] ] = 0;
			position[1]++;
			maze[ position[0] ][ position[1] ] = 1;		
		}
	}
	
	
	//Necessários para a interface KeyListener
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	public String toString() {
		String out = "\n";
		for(int i=0; i<5;i++) {
			for(int j=0; j<5; j++) {
				out += maze[j][i]; 			// J e I invertidos para que 
			}								// o X seja horizontal
			out += "\n";
		}
		return out + "\n";
	}
}
