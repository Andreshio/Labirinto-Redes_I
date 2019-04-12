
public class Player {
	private int id;
	private int[] position;
	private int points;
	
	public Player(int id, int x, int y) {
		this.id = id;
		this.position = new int[2];
		this.position[0] = x;
		this.position[1] = y;
		this.points = 0;
	}
	
	public void changePoints(int points) {
		this.points += points;
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
	
	public int getId() {
		return this.id;
	}
	public int getX() {
		return position[0];
	}
	public int getY() {
		return position[1];
	}
	
}
