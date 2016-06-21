import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

//This class is going to be used to will the gameboard
public class Move {
	public Shell shell;
	public int row;
	public int col;
	public char color;
	public final Label label;
	public int offsetY;
	public int offsetX;
	public int size;
	public Image whiteImage = new Image(null, "src/resources/white.png");
	public Image blackImage = new Image(null, "src/resources/black.png");
	public Image redImage = new Image(null, "src/resources/red.png");


	public Move(Shell shell, int row, int col, char color, int offsetX, int offsetY, int size){
		this.shell = shell;
		this.row = row;
		this.col = col;
		this.color = color;
		this.label = new Label(this.shell, SWT.NONE);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.size = size;
	}
	
	public void initiatePiece() {
		try{
			this.label.setImage(whiteImage);
			this.label.setBounds(50*col+offsetY,50*row + offsetX, 50, 50);
		}catch(Exception e){	
		}
	}
	
	public void changeColor(char colors){
			if(colors == 'r'){
					this.label.setImage(redImage);
				} else if(colors == 'b'){
					this.label.setImage(blackImage);
				} else{
					this.label.setImage(whiteImage);
				}
	}
	
	
	
	
	public int getRow(){
		return this.row;
	}
	
	public int getCol(){
		return this.col;
	}
	
	
	public char getColor(){
		return this.color;
	}
	

}