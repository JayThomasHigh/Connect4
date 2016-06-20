
public class Player {
	private boolean turn;
	private char color;
	private char oppositeColor;
	private static int [][] evaluationTable =   {{3, 4, 5, 7, 5, 4, 3}, 
												{4, 6, 8, 10, 8, 6, 4},
												{5, 8, 11, 13, 11, 8, 5}, 
												{5, 8, 11, 13, 11, 8, 5},
												{4, 6, 8, 10, 8, 6, 4},
												{3, 4, 5, 7, 5, 4, 3}};
	
	public Player(char color, String name, boolean turn){
		this.color = color;
		this.turn = turn;
		if(color == 'b'){
			this.oppositeColor = 'r';
		} else this.oppositeColor = 'b';

	}
	

	public void changeTurn(){
		if(this.turn == true){
			this.turn = false;
		}
		else{
			this.turn = true;
		}
	}
	
	public char getColor(){
		return this.color;
	}
	
	//now we write the minimax algorithm for the artificial intelligence
	public int MiniMax(GameBoard game, int depth){
		int maxScore = 0;
		int maxCol = 0;
		for(int j = 0; j < game.numCols; j++){
			if(!game.fullColumn(j)){
				maxCol = j;
				break;
			}
		}
		for(int i = 0; i<game.numCols; i++){
			if(!game.fullColumn(i)){
				int rowMove = game.checkHighestRow(i);
				game.addPiece(i, this.color);
				if(game.checkMoveForWin(rowMove, i, this.color)){
					game.deleteMove(i);
					return i;
				} else {
					int score = minMove(game, depth-1);
					if(score > maxScore){
						maxScore = score;
						maxCol = i;
					}
					game.deleteMove(i);
				}
			}
		}
		return maxCol;
	}

	public int maxMove(GameBoard game, int depth){
		if(depth == 0){
			return 0; //might evaluate instead
		}else{
			int maxScore = 0;
			//int maxCol = 0;
			for(int i = 0; i<game.numCols; i++){
				if(!game.fullColumn(i)){
					int rowMove = game.checkHighestRow(i);
					game.addPiece(i, this.color);
					if(game.checkMoveForWin(rowMove, i, this.color)){
						game.deleteMove(i);
						return 1000;
					} else {
						int score = evaluationTable[rowMove][i] + minMove(game, depth-1);
						if(score > maxScore){
							maxScore = score;
						}
						game.deleteMove(i);
					}
				}
			}
			return maxScore;
		}	
	}
	
	public int minMove(GameBoard game, int depth){
		if(depth == 0){
			return 0; //might evaluate instead
		}else{
			int maxScore = 0;
			//int maxCol = 0;
			for(int i = 0; i < game.numCols; i++){
				if(!game.fullColumn(i)){
					int rowMove = game.checkHighestRow(i);
					game.addPiece(i, this.oppositeColor);
					if(game.checkMoveForWin(rowMove, i, this.oppositeColor)){
						game.deleteMove(i);
						return -1000;
					} else {
						int score = -1 * evaluationTable[rowMove][i] + maxMove(game, depth-1);
						if(score > maxScore){
							maxScore = score;
						}
						game.deleteMove(i);
					}
				}
			}
			return maxScore;
		}	
	}
	
}
