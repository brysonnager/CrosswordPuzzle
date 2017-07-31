
public class Clue {
	public String clue;
	public int x_start;
	public int y_start;
	public String word;
	public boolean horizontal;
	public String direction;
	
	public Clue(String clue,int x_start, int y_start,String word, boolean horizontal) {
		this.clue = clue;
		this.x_start = x_start;
		this.y_start = y_start;
		this.word = word;
		this.horizontal = horizontal;
		if (horizontal == true) {
			direction = "horizontal";
		} else {
			direction = "vertical";
		}
	}
}
