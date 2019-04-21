
public class Tile {
	private boolean top;
	private boolean right;
	private boolean bottom;
	private boolean left;
	private int value;	
	
	public Tile() {
		this.top = this.right = this.bottom = this.left = false;
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
	
}