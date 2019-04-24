
public class Tile {
	private boolean top;
	private boolean right;
	private boolean bottom;
	private boolean left;
	private boolean visited;	
	
	public Tile() {
		this.top = this.right = this.bottom = this.left = false;
		this.visited = false;
	}
	
	public boolean pathTop() {
		return this.top;
	}
	public boolean pathRight() {
		return this.right;
	}
	public boolean pathBottom() {
		return this.bottom;
	}
	public boolean pathLeft() {
		return this.left;
	}
	
	public boolean visited() {
		return this.visited;
	}
	public void setVisited(boolean b) {
		this.visited = b;
	}
	
	public void setTop(boolean b){
		this.top = b;
	}
	public void setRight(boolean b){
		this.right = b;
	}
	public void setBottom(boolean b){
		this.bottom = b;
	}
	public void setLeft(boolean b){
		this.left = b;
	}
	
	public String toString() {
		return "Top: "+top + " left: " + left + " right: " + right + " bottom: " + bottom;
	}
	
}