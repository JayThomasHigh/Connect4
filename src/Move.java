
public class Move {
	private int row;
	private int col;
	private int score; 
	private char color;


	public Move(int row, int col, int score, char color){
		this.row = row;
		this.col = col;
		this.score = score;
		this.color = color;
	}
	
	public int getRow(){
		return this.row;
	}
	
	public int getCol(){
		return this.col;
	}
	
	public int getScore(){
		return this.score;
	}
	
	public char getColor(){
		return this.color;
	}
	

}