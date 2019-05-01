package Server;
import java.util.LinkedList;
import java.util.Random;

public class Maze {
	private int[][] matrix;
	private Walls walls;
	
	public Maze(int mazeSize) {
		this.matrix = new int[mazeSize][mazeSize];
		
		// Para testar o sistema de pontos
		//this.matrix[5][5] = 8;
		//this.matrix[2][4] = -1;
		//this.matrix[4][5] = 10;
		//this.matrix[5][4] = 7;
		//this.matrix[4][3] = -4;
		
		this.walls = new Walls(mazeSize);
		
		generatePoints(mazeSize, 10);
	}
	
	
	// Gera 5 tipos de pontos no labirinto recebendo por parametro o tamanho do labirinto e o numero de pontos que será gerado
	// Cada tipo de ponto sera gerado com base na sua respectiva probabilidade sendo de 0 a 100
	private void generatePoints(int mazeSize, int numPoints) {
		// array com os tipos de pontos
		int[] points = {10,-15,30,40,50};
		// variaveis para calculo de probabilidade
		int a = 63; int ab = 83; int ac = 93; int ad = 98;
		
		// iteracao para gerar os pontos
		for (int i = 0; i < numPoints; i++) {
			
			// gera a posicao do ponto no labirinto
			int randX = randomBetween(0, mazeSize-1);
			int randY = randomBetween(0, mazeSize-1);
			
			// verifica se a posicao é valida
			if(this.matrix[randX][randY] == 0) {
				
				// variavel para calculo de probabilidade do tipo de ponto
				int randProb = randomBetween(0, 100);
				int randPoint = 0;
				
				// condicionais para definicao do tipo de ponto
				if(randProb<a) {
					randPoint = points[0];
				}
				else if(randProb>=a && randProb<ab) {
					randPoint = points[1];
				}
				else if(randProb>=ab && randProb<ac) {
					randPoint = points[2];
				}
				else if(randProb>=ac && randProb<ad) {
					randPoint = points[3];
				}
				else {
					randPoint = points[4];
				}
				
				// adiciona ponto ao labirinto
				this.matrix[randX][randY] = randPoint;
			}
			
		}
		
	}
	
	private int randomBetween(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	
	
	/*
	 * Funções de movimentação
	 * 
	 * Se estiver de apertando espaço, e de frente a uma parede,
	 * tentar removê-la. 
	 * Na terceira tentativa consecutiva na mesma parede, remove-la
	 * 
	 * se o movimento for permitido, 
	 * 		dar pontos ao jogador, se for o caso;
	 * 		limpar o rastro anterior no labirinto;
	 * 		atualizar a posição do jogador;
	 * 		atualizar o labirinto com a nova posição do jogador;
	 *
	 * */
	public synchronized boolean goLeft(Player p, boolean spacePressed) { 
		if(spacePressed) {
			p.testObjective( this.walls.leftWallCoordinates(p) );
			if(p.getX() > 0) 
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
		if(spacePressed) {
			p.testObjective( this.walls.rightWallCoordinates(p) );
			if(p.getX() < this.matrix.length-1)
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
		
		if(spacePressed) {
			p.testObjective( this.walls.topWallCoordinates(p) );
			if(p.getY() > 0) 
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
		
		if(spacePressed) {
			p.testObjective( this.walls.bottomWallCoordinates(p) );
			if(p.getY() < this.matrix.length-1) 
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
