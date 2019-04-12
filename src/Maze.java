
public class Maze {
	private int[][] matrix;
	
	public Maze(int mazeSize) {
		this.matrix = new int[mazeSize][mazeSize];
	}
	
	/*
	 * Funções de movimentação
	 * 
	 * se o movimento for permitido, 
	 * 		limpar o rastro anterior no labirinto
	 * 		atualizar a posição do jogador
	 * 		atualizar o labirinto com a nova posição do jogador
	 * 
	 * 
	 *
	 * todo:
	 * 	no corpo do if existirá o método que diz se o movimento trará pontuação
	 *
	 *	synchronized ?
	 * */
	
	public synchronized  void goLeft(Player p) { 
		if( isMovementValid( p.getX()-1, p.getY()) ) {
			changeTileValue( p, 0);
			p.decreaseX();
			changeTileValue( p, p.getGameId()+1 );
		}
	}
	public synchronized void goRight(Player p) 	{ 
		if( isMovementValid( p.getX()+1, p.getY()) ) {
			changeTileValue(p, 0);
			p.increaseX();
			changeTileValue( p, p.getGameId()+1 );
		}
	}
	public synchronized void goUp(Player p) 	{ 
		if( isMovementValid( p.getX(), p.getY()-1 ) ) {
			changeTileValue(p, 0);
			p.decreaseY();
			changeTileValue( p, p.getGameId()+1 );
		}
	}
	public synchronized void goDown(Player p) 	{
		if( isMovementValid( p.getX(), p.getY()+1 ) ) {
			changeTileValue(p, 0);
			p.increaseY();
			changeTileValue( p, p.getGameId()+1 );		
		}
	}
	
	public void changeTileValue(Player p, int value) {
		this.matrix[ p.getX() ][ p.getY() ] = value;
	}
	private boolean isMovementValid(int x, int y) {
		int mazeSize = this.matrix[0].length;
		int tileValue = 0;
		
		boolean inBounds = x>=0 && x<mazeSize && y>=0 && y<mazeSize; 	// Diz se o movimento continuará dentro do labirinto
		
		if(inBounds)tileValue = this.matrix[x][y]; 						// Para evitar outOfBoundsExeption
		
		boolean tile_is_free = tileValue<1 || tileValue>3;				// Diz se o movimento não encontrará algo sólido
		
		return inBounds && tile_is_free;
	}
	
	private void givePlayerPoints(Player p, int x, int y) {
		//TODO
		//Checa se o movimento moverá o player a um tile
		//com pontos, e ativa a função changePoints do player.
		//Será chamado nos métodos de movimentação, após o if
	}
	
	public int[][] getMatrix(){
		return this.matrix;
	}
	
	public String toString() {
		String out = "\n";
		for(int i=0; i < this.matrix[0].length;i++) {
			for(int j=0; j < this.matrix[0].length; j++) {
				out += matrix[j][i]; 									// J e I invertidos para que 
			}															// o X seja horizontal
			out += "\n";
		}
		return out + "\n";
	}
}
