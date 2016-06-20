
public class GameBoard {
	char[][] board; //The actual game board, will hold w, b, and r where w is blank
	int numRows;
	int numCols;
	
	//Constructor
	public GameBoard(){
		this.numRows = 6;
		this.numCols = 7;
		this.board = new char[numRows][numCols];
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				board[i][j] = 'w'; //Fill in every slot as white
			}
		}
	}

///Functions

	
	
	//Takes in a designated column, returns true if the column is full and false if the column has room for more moves
	public boolean fullColumn(int col){
		try{
			if(this.board[numRows-1][col] == 'w'){
				return false;
			}
			return true;
		}catch(Exception ex){
			System.out.println("The column you requested is out of bounds");
			return true; //This will return true as the column is nonexistent and thus does not have room for moves
		}
	}
	
	public int checkHighestRow(int col){
		int row = 0;
		while(this.board[row][col] != 'w'){
			row++;
			if(row == 5){
				return 5;
			}
		}
		return row;
	}
	
	//takes in the column that the player would like to make a move in, as well as the player color
	public void addPiece(int col, char color){
		for(int i = 0; i < this.numRows; i++){
			if(this.board[i][col] == 'w'){
				this.board[i][col] = color;
				break;
			}
		}
	}
	
	//takes in the column that the player would like to make a move in, as well as the player color
	public void addPiece(int col, char color, Move[][] pieces){
		for(int i = 0; i < this.numRows; i++){
			if(this.board[i][col] == 'w'){
				this.board[i][col] = color;
				pieces[i][col].changeColor(color);	
				break;
			}
		}
	}
	
	public boolean checkMoveForWin(int rowOrigin, int colOrigin, char color){
		/*char otherColor;
		if(color == 'r') otherCol = 'b';
		if(color == 'b') otherCol = 'r';*/
		int row = rowOrigin;
		int col = colOrigin;
		int rowCount = 1;
		int colCount = 1;
		int lDiagCount = 1;
		int rDiagCount = 1;
		//Check row for win
		while(row > 0){
			row--;
			if(this.board[row][col] == color){
				rowCount++;
			} else{
				break;
			}
		}
		row = rowOrigin;
		while(row < this.numRows-1){
			row++;
			if(this.board[row][col] == color){
				rowCount++;
			} else {
				break;
			}
		}
		row = rowOrigin;
		//System.out.println(Integer.toString(rowCount));
	//	System.out.println(Integer.toString(rowOrigin));
		if(rowCount == 4) return true;
		//check column for win
		while(col > 0){
			col--;
			if(this.board[row][col] == color){
				colCount++;
			} else break;
		}
		col = colOrigin;
		while(col < this.numCols-1){
			col++;
			if(this.board[row][col] == color){
				colCount++;
			} else break;
		}
		col = colOrigin;
		if(colCount==4) return true;
		//check Diagnal for win
		while(col > 0 && row < numRows-1){
			col--;
			row++;
			if(this.board[row][col] == color){
				lDiagCount++;
			} else break;
		}
		col = colOrigin;
		row = rowOrigin;
		while(col < numCols-1 && row > 0){
			col++;
			row--;
			if(this.board[row][col] == color){
				lDiagCount++;
			} else break;
		}
		col = colOrigin;
		row = rowOrigin;
		if(lDiagCount == 4) return true;			
		//check other diagonal for win
		//check Diagnal for win
		while(col > 0 && row > 0){
			col--;
			row--;
			if(this.board[row][col] == color){
				rDiagCount++;
			} else break;
		}
		col = colOrigin;
		row = rowOrigin;
		while(col < numCols-1 && row < numRows-1){
			col++;
			row++;
			if(this.board[row][col] == color){
				rDiagCount++;
			} else break;
		}
		if(rDiagCount == 4) return true;		
		return false;
	}
	
	
	//Display the gameboard
	public void displayBoard(){
		for(int i = numRows-1; i >= 0; i--){
			for(int j = 0; j < numCols; j++){
				System.out.print(Character.toString(this.board[i][j]) + " ");
			}
			System.out.println();
		}
	}
	
	public void deleteMove(int col){
		int deleteIndex = this.numRows;
		while(deleteIndex > 0){
			deleteIndex--;
			if(this.board[deleteIndex][col] != 'w'){
				this.board[deleteIndex][col] = 'w';
				break;
			}
		}
	}
	
	
	
	//The following functions take in a move for the minimax functions
	public boolean checkMoveForWin(Move move){
		int rowOrigin = move.getRow();
		int colOrigin = move.getCol();
		char color = move.getColor();
		int row = rowOrigin;
		int col = colOrigin;
		int rowCount = 1;
		int colCount = 1;
		int lDiagCount = 1;
		int rDiagCount = 1;
		//Check row for win
		while(row > 0){
			row--;
			if(this.board[row][col] == color){
				rowCount++;
			} else{
				break;
			}
		}
		row = rowOrigin;
		while(row < this.numRows-1){
			row++;
			if(this.board[row][col] == color){
				rowCount++;
			} else {
				break;
			}
		}
		row = rowOrigin;
		System.out.println(Integer.toString(rowCount));
		System.out.println(Integer.toString(rowOrigin));
		if(rowCount == 4) return true;
		//check column for win
		while(col > 0){
			col--;
			if(this.board[row][col] == color){
				colCount++;
			} else break;
		}
		col = colOrigin;
		while(col < this.numCols-1){
			col++;
			if(this.board[row][col] == color){
				colCount++;
			} else break;
		}
		col = colOrigin;
		if(colCount==4) return true;
		//check Diagnal for win
		while(col > 0 && row < numRows-1){
			col--;
			row++;
			if(this.board[row][col] == color){
				lDiagCount++;
			} else break;
		}
		col = colOrigin;
		row = rowOrigin;
		while(col < numCols-1 && row > 0){
			col++;
			row--;
			if(this.board[row][col] == color){
				lDiagCount++;
			} else break;
		}
		col = colOrigin;
		row = rowOrigin;
		if(lDiagCount == 4) return true;			
		//check other diagonal for win
		//check Diagnal for win
		while(col > 0 && row > 0){
			col--;
			row--;
			if(this.board[row][col] == color){
				rDiagCount++;
			} else break;
		}
		col = colOrigin;
		row = rowOrigin;
		while(col < numCols-1 && row < numRows-1){
			col++;
			row++;
			if(this.board[row][col] == color){
				rDiagCount++;
			} else break;
		}
		if(rDiagCount == 4) return true;		
		return false;
	}
	
	
	
	


	
	
	
	
	
	
	public void takeTurn(Player player, int depth){
		int move = player.MiniMax(this, depth);
		addPiece(move, player.getColor());
	}
	
	public void takeTurn(Player player, int depth, Move[][] piece){
		int move = player.MiniMax(this, depth);
		addPiece(move, player.getColor(), piece);
	}
	
	
	
	
	
	/*
	
	//Calculate the score of a certain position, based on the surrounding pieces
	//This will later be used to calculate the strength of a move that does not necessarily cause a win or loss
	public int calculateScore (int depth, char color){
		if(depth != 0){
			int max = 0;
			for(int i = 0; i < numCols; i++){
				int rowIndex = checkHighestRow(i);
				this.addPiece(rowIndex, i);
				if(checkMoveForWin()){
					return 1000;
				} 
				//now we msut remove the piece
			}
		}
		
		return 0;
	}

	*/
	
}
