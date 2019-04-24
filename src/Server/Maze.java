package Server;
import java.util.LinkedList;

public class Maze {
	private int[][] matrix;
	private Walls walls;
	
	public Maze(int mazeSize) {
		this.matrix = new int[mazeSize][mazeSize];
		
		// Para testar o sistema de pontos
		this.matrix[5][5] = 8;
		this.matrix[2][4] = -1;
		this.matrix[4][5] = 10;
		this.matrix[5][4] = 7;
		this.matrix[4][3] = -4;
		
		this.walls = new Walls(mazeSize);
	}
	
	
	
	
	/*
	 * Funções de movimentação
	 * 
	 * Se estiver de apertando espaço, e de frente a uma parede,
	 * tentar removê-la
	 * 
	 * se o movimento for permitido, 
	 * 		dar pontos ao jogador, se for o caso;
	 * 		limpar o rastro anterior no labirinto;
	 * 		atualizar a posição do jogador;
	 * 		atualizar o labirinto com a nova posição do jogador;
	 *
	 *	synchronized ?
	 * */
	public synchronized boolean goLeft(Player p, boolean spacePressed) { 
		if(spacePressed && p.getX() > 0) {
			this.walls.try_removeLeftWall(p);
			return true;
		}
		p.resetRemovingWallIterations();
		if( isMovementValid( p.getX()-1, p.getY()) && this.walls.left(p) == false) {
			givePlayerPoints(p, p.getX()-1, p.getY() );
			changeTileValue( p, 0);
			p.decreaseX();
			changeTileValue( p, p.getGameId()+1 );
			return true;
		}
		return false;
	}
	public synchronized boolean goRight(Player p, boolean spacePressed) 	{ 	
		if(spacePressed && p.getX() < this.matrix.length-1) {
			this.walls.try_removeRightWall(p);
			return true;
		}
		p.resetRemovingWallIterations();
		if( isMovementValid( p.getX()+1, p.getY()) && this.walls.right(p) == false ) {
			givePlayerPoints(p, p.getX()+1, p.getY() );
			changeTileValue(p, 0);
			p.increaseX();
			changeTileValue( p, p.getGameId()+1 );
			return true;
		}
		return false;
	}
	public synchronized boolean goUp(Player p, boolean spacePressed) 	{ 
		
		if(spacePressed && p.getY() > 0) {
			this.walls.try_removeTopWall(p);
			return true;
		}
		p.resetRemovingWallIterations();
		if( isMovementValid( p.getX(), p.getY()-1 ) && this.walls.top(p) == false) {
			givePlayerPoints(p, p.getX(), p.getY()-1 );
			changeTileValue(p, 0);
			p.decreaseY();
			changeTileValue( p, p.getGameId()+1 );
			return true;
		}
		return false;
	}
	public synchronized boolean goDown(Player p, boolean spacePressed) 	{
		
		if(spacePressed && p.getY() < this.matrix.length-1) {
			this.walls.try_removeBottomWall(p);
			return true;
		}
		p.resetRemovingWallIterations();
		if( isMovementValid( p.getX(), p.getY()+1 ) && this.walls.bottom(p) == false) {
			givePlayerPoints(p, p.getX(), p.getY()+1 );
			changeTileValue(p, 0);
			p.increaseY();
			changeTileValue( p, p.getGameId()+1 );		
			return true;
		}
		return false;
	}
	
	public void changeTileValue(Player p, int value) {
		this.matrix[ p.getX() ][ p.getY() ] = value;
	}
	private boolean isMovementValid(int x, int y) {
		int mazeSize = this.matrix[0].length;
		int tileValue = 0;
		
		boolean inBounds = x>=0 && x<mazeSize && y>=0 && y<mazeSize; 	// Diz se o movimento continuará dentro do labirinto
		
		if(inBounds)tileValue = this.matrix[x][y]; 						// Para evitar outOfBoundsExeption
		
		boolean tile_is_free = tileValue<1 || tileValue>5;				// Diz se o movimento não encontrará algo sólido
		
		return inBounds && tile_is_free;
	}
	
	private void givePlayerPoints(Player p, int x, int y) {
		p.changePoints( this.matrix[x][y] );
	}
	
	public int[][] getMatrix(){
		return this.matrix;
	}
	
	public String toString() {
		String out = "\n";
		for(int i=0; i < this.matrix[0].length;i++) {
			for(int j=0; j < this.matrix[0].length; j++) {
				out += matrix[j][i]; 									// J e I invertidos para que 
				out += j<this.matrix[0].length-1? " " : "";				// o X seja horizontal
			}															
			out += i<this.matrix[0].length-1?"#":"";
		}
		
		out+="&" + this.walls.toString();
		return out;
	}
}
