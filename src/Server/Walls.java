package Server;
import java.util.LinkedList;

public class Walls {
	private Tile[][] movementBlocker;
	private LinkedList<int[]> linePaths;
	
	public Walls(int mazeSize) {
		int movementBlockerSize = mazeSize + 1;
		
		this.linePaths = new LinkedList<int[]>();
		
		this.movementBlocker = new Tile[ movementBlockerSize ][ movementBlockerSize ];
		for(int i=0; i<movementBlockerSize; i++) {
			for(int j=0; j<movementBlockerSize; j++) {
				this.movementBlocker[i][j] = new Tile();
			}
		}
		
		createPaths();
	}
	
	/*
	 * Cria as paredes do labirinto de acordo com o algoritmo Aldous-Broder
	 * Visto em: https://dev.to/marksasp95/introducing-maze-generator-java-320g
	 * */
	
	public void createPaths() {
		int size = this.movementBlocker.length;
		int nullTiles = size * size;
		
		int[] point = {(int)Math.floor(Math.random()*size), (int)Math.floor(Math.random()*size)};
		
		System.out.println("\n\n\n\n Creating MAZE \n\n\n\n");
		while(nullTiles>0) {
			/*
			 * Sorteia um lado, e pega o proximo ponto
			 * -1 significa que o lado e invalido, e deve ser
			 * sorteado novamente
			 * */
			int[] nextPoint = getNextPoint(point); // [lado, x, y];
			if(nextPoint[0] != -1) {
				if(movementBlocker[ point[0] ][ point[1] ].visited() == false) {
					movementBlocker[ point[0] ][ point[1] ].setVisited(true);
					nullTiles--;
				}
				if(movementBlocker[ nextPoint[1] ][ nextPoint[2] ].visited() == false) {
					
					movementBlocker[ nextPoint[1] ][ nextPoint[2] ].setVisited(true);
					nullTiles--;
					
					/*
					 * Salva os pontos para serem desenhados pelo DrawTools
					 * point é o ponto inicial da linha
					 * nextPoint é o ponto final da linha
					 * */					
					
					int[] w = {point[0], point[1], nextPoint[1], nextPoint[2]};
					this.linePaths.add(w);
					
					/*
					 * Bloqueia os movimentos, modificando tileMovements
					 * TileMovements e verificado antes do movimento
					*/
					this.blockMovement(nextPoint[0], nextPoint[1], nextPoint[2]);
				}
				
				point[0] = nextPoint[1];
				point[1] = nextPoint[2];
			}
		}
		System.out.println("\n\n\n MAZE CREATED \n\n\n");
	}
	public int[] getNextPoint(int[] point) {
		int size = this.movementBlocker.length;
		int[] nextPoint = null;
		int randomSide = (int)Math.floor(Math.random()*4);
		if(randomSide == 0 && point[0] > 0) { //left
			nextPoint = new int[2];
			nextPoint[0] = point[0]-1;
			nextPoint[1] = point[1];
		}
		if(randomSide == 1 && point[1] > 0) { //top
			nextPoint = new int[2];
			nextPoint[0] = point[0];
			nextPoint[1] = point[1]-1;
		}
		if(randomSide == 2 && point[0] < size-1) { //right
			nextPoint = new int[2];
			nextPoint[0] = point[0]+1;
			nextPoint[1] = point[1];
		}
		if(randomSide == 3 && point[1] < size-1) { //bottom
			nextPoint = new int[2];
			nextPoint[0] = point[0];
			nextPoint[1] = point[1]+1;
		}
		
		/*
		 * Caso tenha encontrado um ponto valido.
		 * */
		if(nextPoint != null) {
			int[] out = {randomSide, nextPoint[0], nextPoint[1]};
			return out;
		}
		int[] out = {-1, point[0], point[1]};
		return out;
	}
	public void blockMovement(int side, int x, int y) {	
		/*
		 * X e Y sao o final de uma linha movida a esquerda,
		 * bloquear o topo do proprio tile e o fundo do tile
		 * acima
		 * */
		if(side == 0 && y > 0) {
			this.movementBlocker[x][y].setTop(true);
			this.movementBlocker[x][y-1].setBottom(true);
		}
		/*
		 * X e Y sao o final de uma linha movida para cima,
		 * bloquear a esquerda do proprio tile e a direita do 
		 * tile anterior (a esquerda, x-1)
		 * */
		if(side == 1 && x>0) { //top
			this.movementBlocker[x][y].setLeft(true);
			this.movementBlocker[x-1][y].setRight(true);
		}
		/*
		 * X e Y sao o final de uma linha movida a direita,
		 * bloquear o topo do tile anterior (x-1), e o fundo 
		 * do tile 1 linha acima e 1 coluna a esquerda;
		 * */
		if(side == 2 && x>0 && y>0) {	//right
			this.movementBlocker[x-1][y].setTop(true);
			this.movementBlocker[x-1][y-1].setBottom(true);
		}
		/*
		 * X e Y sao o final de uma linha movida para baixo,
		 * bloquear o a esquerda do tile acima e a direita do 
		 * tile 1 linha acima e 1 coluna a esquerda
		 * */
		if(side == 3 && x>0 && y>0) { //bottom
			this.movementBlocker[x][y-1].setLeft(true);
			this.movementBlocker[x-1][y-1].setRight(true);
		}
	}
	
	public void try_removeLeftWall(Player p) {
		// linha a esquerda tem
		// ponto inicial	 x, y
		// ponto final 		x, y+1
		// x não é 0
		
		int x = p.getX();
		int y = p.getY();
		int w0, w1, w2, w3;
		for(int i=0; i< this.linePaths.size(); i++) {
			//4 pontos que criam a linha da parede
			
			w0 = linePaths.get(i)[0];
			w1 = linePaths.get(i)[1];
			w2 = linePaths.get(i)[2];
			w3 = linePaths.get(i)[3];
			
			/*
			 * Testa se a linha i é a parede à esquerda,
			 * tanto de A a B quanto de B a A;
			 * */
			boolean test1 = w0==x && w1==y && w2==x && w3==y+1;
			boolean test2 = w0==x && w1==y+1 && w2==x && w3==y;
			
			if(test1 || test2) {
				/*
				 * Conta 3 iterações, mudando a cor do player;
				 * 	se chegar a 3, remove a linha, e limpa seus
				 * 	bloqueios
				 * */
				if(p.tick_removeWall(linePaths.get(i))) {
					linePaths.remove(i);
					this.movementBlocker[x][y].setLeft(false);
					this.movementBlocker[x-1][y].setRight(false);
				}
			}
		}
	}
	
	public void try_removeRightWall(Player p) {
		// Linha a direita tem
		// ponto inicial 	x+1, y
		// ponto final		x+1, y+1
		// x não é length-1
		
		int x = p.getX();
		int y = p.getY();
		int w0, w1, w2, w3;
		for(int i=0; i< this.linePaths.size(); i++) {
			//4 pontos que criam a linha da parede
			w0 = linePaths.get(i)[0];
			w1 = linePaths.get(i)[1];
			w2 = linePaths.get(i)[2];
			w3 = linePaths.get(i)[3];
			
			/*
			 * Testa se a linha i e a parede a direita,
			 * tanto de A a B quanto de B a A;
			 * */
			boolean test1 = w0==x+1 && w1==y && w2==x+1 && w3==y+1;
			boolean test2 = w0==x+1 && w1==y+1 && w2==x+1 && w3==y;
			
			if(test1 || test2) {
				/*
				 * Conta 3 iterações, mudando a cor do player;
				 * 	se chegar a 3, remove a linha, e limpa seus
				 * 	bloqueios
				 * */
				if(p.tick_removeWall(linePaths.get(i))) {
					linePaths.remove(i);
					this.movementBlocker[x][y].setRight(false);
					this.movementBlocker[x+1][y].setLeft(false);
				}
			}
		}
	}
	
	public void try_removeTopWall(Player p) {
		//	Linha acima tem:
		//	ponto inicial 	x, y
		//	ponto final 	x+1, y
		// 	y não e 0
		
		int x = p.getX();
		int y = p.getY();
		int w0, w1, w2, w3;
		for(int i=0; i< this.linePaths.size(); i++) {
			//4 pontos que criam a linha da parede
			w0 = linePaths.get(i)[0];
			w1 = linePaths.get(i)[1];
			w2 = linePaths.get(i)[2];
			w3 = linePaths.get(i)[3];
			
			/*
			 * Testa se a linha i e a parede acima,
			 * tanto de A a B quanto de B a A;
			 * */
			boolean test1 = w0==x && w1==y && w2==x+1 && w3==y;
			boolean test2 = w0==x+1 && w1==y && w2==x && w3==y;
			
			if(test1 || test2) {
				/*
				 * Conta 3 iterações, mudando a cor do player;
				 * 	se chegar a 3, remove a linha, e limpa seus
				 * 	bloqueios
				 * */
				if(p.tick_removeWall(linePaths.get(i))) {
					linePaths.remove(i);
					this.movementBlocker[x][y].setTop(false);
					this.movementBlocker[x][y-1].setBottom(false);
				}
			}
		}
	}
	public void try_removeBottomWall(Player p) {
		//	Linha abaixo tem:
		//	ponto inicial 	x, y+1
		//	ponto final 	x+1, y+1
		// 	y nao e lenght-1
		
		int x = p.getX();
		int y = p.getY();
		int w0, w1, w2, w3;
		
		for(int i=0; i< this.linePaths.size(); i++) {
			//4 pontos que criam a linha da parede
			w0 = linePaths.get(i)[0];
			w1 = linePaths.get(i)[1];
			w2 = linePaths.get(i)[2];
			w3 = linePaths.get(i)[3];
			
			/*
			 * Testa se a linha i e a parede abaixo,
			 * tanto de A a B quanto de B a A;
			 * */
			boolean test1 = w0==x && w1==y+1 && w2==x+1 && w3==y+1;
			boolean test2 = w0==x+1 && w1==y+1 && w2==x && w3==y+1;
			
			if(test1 || test2) {
				/*
				 * Conta 3 iterações, mudando a cor do player;
				 * 	se chegar a 3, remove a linha, e limpa seus
				 * 	bloqueios
				 * */
				if(p.tick_removeWall(linePaths.get(i))) {
					linePaths.remove(i);
					this.movementBlocker[x][y].setBottom(false);
					this.movementBlocker[x][y+1].setTop(false);
				}
			}
		}
	}
	public boolean left(Player p) {
		return this.movementBlocker[ p.getX() ][ p.getY() ].pathLeft(); 
	}
	public boolean top(Player p) {
		return this.movementBlocker[ p.getX() ][ p.getY() ].pathTop(); 
	}
	public boolean right(Player p) {
		return this.movementBlocker[ p.getX() ][ p.getY() ].pathRight(); 
	}
	public boolean bottom(Player p) {
		return this.movementBlocker[ p.getX() ][ p.getY() ].pathBottom(); 
	}
	
	public String toString() {
		String out = "";
		for(int i=0; i<this.linePaths.size(); i++) {
			for(int j=0; j<this.linePaths.get(i).length; j++) {
				out+=linePaths.get(i)[j];
				out+=j<linePaths.get(i).length-1?" ":"";
			}
			out += i<linePaths.size()-1?"#":"";
		}
		return out;
	}
	
	
}
