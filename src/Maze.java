
public class Maze {
	private int[][] matrix;
	private Tile[][] maze;
	private int[][] wallPath;
	private int wallPathIndex;
	
	public Maze(int mazeSize) {
		this.matrix = new int[mazeSize][mazeSize];
		
		// Para testar o sistema de pontos
		this.matrix[5][5] = 8;
		this.matrix[4][3] = -4;
		
		this.wallPath = new int[ (mazeSize+1)*(mazeSize+1) ][4];
		this.wallPathIndex = 0;
		
		this.createMaze(mazeSize+1);
	}
	
	/*
	 * Funções de movimentação
	 * 
	 * se o movimento for permitido, 
	 * 		dar pontos ao jogador, se for o caso;
	 * 		limpar o rastro anterior no labirinto;
	 * 		atualizar a posição do jogador;
	 * 		atualizar o labirinto com a nova posição do jogador;
	 *
	 *	synchronized ?
	 * */
	
	public Tile getTile(int x, int y) {
		return maze[x][y];
	}
	
	private void createMaze(int mazeSize) {
		int nullTiles = mazeSize*mazeSize;
		
		System.out.println(mazeSize);
		
		this.maze = new Tile[mazeSize][mazeSize];
		for(int i=0; i<mazeSize; i++) {
			for(int j=0; j<mazeSize; j++) {
				this.maze[i][j] = new Tile();
			}
		}
		
		int[] point = {(int)Math.floor(Math.random()*mazeSize), (int)Math.floor(Math.random()*mazeSize)};
		
		System.out.println("\n\n\n\n Creating MAZE \n\n\n\n");
		while(nullTiles>0) {
			int[] nextPoint = getNextPoint(point, mazeSize); // [lado, x, y];
			if(nextPoint[0] != -1) {
				if(maze[ point[0] ][ point[1] ].visited() == false) {
					maze[ point[0] ][ point[1] ].setVisited(true);
					nullTiles--;
				}
				if(maze[ nextPoint[1] ][ nextPoint[2] ].visited() == false) {
					
					maze[ nextPoint[1] ][ nextPoint[2] ].setVisited(true);
					nullTiles--;
					/*
					tile = maze[ point[0] ][ point[1] ];
					nextTile = maze[ nextPoint[1] ][ nextPoint[2] ];
					*/
					
					this.wallPath[ this.wallPathIndex ][0] = point[0];
					this.wallPath[ this.wallPathIndex ][1] = point[1];
					this.wallPath[ this.wallPathIndex ][2] = nextPoint[1];
					this.wallPath[ this.wallPathIndex ][3] = nextPoint[2];
					this.wallPathIndex++;
					
					this.makePath(nextPoint[0], nextPoint[1], nextPoint[2], mazeSize);
				}
				
				point[0] = nextPoint[1];
				point[1] = nextPoint[2];
			}
		}
		for(int i=0; i<mazeSize*mazeSize; i++) {
			System.out.println(wallPath[i][0]+" "+wallPath[i][1] + " " + wallPath[i][2] + " " + wallPath[i][3]);
		}
		System.out.println("\n\n\n MAZE CREATED \n\n\n");
	}
	//carve
	public void makePath(int side, int x, int y, int mazeSize) {
		/*
		 * X e Y formam o ponto final da linha
		 * */
		
		System.out.print("\n" + x + " " + y);
		if(side == 0 && y > 0) {
			this.maze[x][y].setTop(false);
			this.maze[x][y-1].setBottom(false);
		}
		if(side == 1 && x>0) { //top
			this.maze[x][y].setLeft(false);
			this.maze[x-1][y].setRight(false);
		}
		if(side == 2 && x>0 && y>0) {	//right
			this.maze[x-1][y].setTop(false);
			this.maze[x-1][y-1].setBottom(false);
		}
		if(side == 3 && x>0 && y>0) {
			this.maze[x][y-1].setLeft(false);
			this.maze[x-1][y-1].setRight(false);
		}
	}
	
	public int[] getNextPoint(int[] point, int mazeSize) {
		/*
		 * left, top, right, bottom
		 * 
		 * x0, y0, xsize, ysize
		 * */
		
		int[] nextPoint = null;
		int randomSide = (int)Math.floor(Math.random()*4);
		if(randomSide == 0 && point[0] > 0) { //Esquerda
			nextPoint = new int[2];
			nextPoint[0] = point[0]-1;
			nextPoint[1] = point[1];
		}
		if(randomSide == 1 && point[1] > 0) { //Acima
			nextPoint = new int[2];
			nextPoint[0] = point[0];
			nextPoint[1] = point[1]-1;
		}
		if(randomSide == 2 && point[0] < mazeSize-1) { //Direita
			nextPoint = new int[2];
			nextPoint[0] = point[0]+1;
			nextPoint[1] = point[1];
		}
		if(randomSide == 3 && point[1] < mazeSize-1) { //Abaixo
			nextPoint = new int[2];
			nextPoint[0] = point[0];
			nextPoint[1] = point[1]+1;
		}
		
		if(nextPoint != null) {
			int[] out = {randomSide, nextPoint[0], nextPoint[1]};
			return out;
		}
		int[] out = {-1, point[0], point[1]};
		return out;
	}
	
	public synchronized boolean goLeft(Player p) { 
		if( isMovementValid( p.getX()-1, p.getY()) && this.maze[ p.getX() ][ p.getY() ].pathLeft()  ) {
			givePlayerPoints(p, p.getX()-1, p.getY() );
			changeTileValue( p, 0);
			p.decreaseX();
			changeTileValue( p, p.getGameId()+1 );
			return true;
		}
		return false;
	}
	public synchronized boolean goRight(Player p) 	{ 
		if( isMovementValid( p.getX()+1, p.getY()) && this.maze[ p.getX() ][ p.getY() ].pathRight() ) {
			givePlayerPoints(p, p.getX()+1, p.getY() );
			changeTileValue(p, 0);
			p.increaseX();
			changeTileValue( p, p.getGameId()+1 );
			return true;
		}
		return false;
	}
	public synchronized boolean goUp(Player p) 	{ 
		if( isMovementValid( p.getX(), p.getY()-1 ) && this.maze[ p.getX() ][ p.getY() ].pathTop() ) {
			givePlayerPoints(p, p.getX(), p.getY()-1 );
			changeTileValue(p, 0);
			p.decreaseY();
			changeTileValue( p, p.getGameId()+1 );
			return true;
		}
		return false;
	}
	public synchronized boolean goDown(Player p) 	{
		if( isMovementValid( p.getX(), p.getY()+1 ) && this.maze[ p.getX() ][ p.getY() ].pathBottom() ) {
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
		out+="&";
		for(int i=0; i<this.wallPath.length; i++) {
			for(int j=0; j<this.wallPath[i].length; j++) {
				out+=wallPath[i][j];
				out+=j<wallPath[i].length-1?" ":"";
			}
			out += i<wallPath.length-1?"#":"";
		}
		return out;
	}
}
