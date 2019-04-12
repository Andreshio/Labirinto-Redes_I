
public class Player {
	private int[] position;
	private int points;
	
	public Player(int[] position) {
		this.position = position;
	}
	
	public int getX() {
		return position[0];
	}
	public int getY() {
		return position[1];
	}
	public void decreaseX() {
		this.position[0]--;
	}
	public void increaseX() {
		this.position[0]++;
	}
	public void decreaseY() {
		this.position[1]--;
	}
	public void increaseY() {
		this.position[1]++;
	}
	
}
