import java.util.Scanner;
public class GamePlay {

	public static void main(String[] args){
		boolean gameOver = false;
		GameBoard gameboard = new GameBoard();
		Player player1 = new Player('r', "Jay", true);
		Player player2 = new Player('b', "Player", false);
		Scanner move = new Scanner(System.in);
		Player currentPlayer = player2;
		int moveCol = 0;
		while(!gameOver){
			if(currentPlayer.equals(player1)){
				currentPlayer = player2;
				System.out.println("AI turn");
				gameboard.takeTurn(currentPlayer, 5);
			}else{
				currentPlayer = player1;
			
		
			boolean invalidMove = true;
			while(invalidMove){
				System.out.println("your move");
				String yourMove = move.next();
				try {
			        moveCol = Integer.parseInt(yourMove);
			    } catch (IllegalArgumentException e) {
			        e.printStackTrace();
			    }
				if(moveCol == 8){
					int bestMove = currentPlayer.MiniMax(gameboard, 8);
					System.out.println(bestMove +"yooo");
					invalidMove = true;
				}else invalidMove = gameboard.fullColumn(moveCol);
			}
			int rowIndex = gameboard.checkHighestRow(moveCol);
			gameboard.addPiece(moveCol, currentPlayer.getColor());
			gameOver = gameboard.checkMoveForWin(rowIndex, moveCol, currentPlayer.getColor());
			}
			gameboard.displayBoard(); 
			System.out.println();
			
		}
		System.out.println("game over");
		move.close();
	}
	
	
	static public void playGame(){
		boolean gameOver = false;
		GameBoard gameboard = new GameBoard();
		Player player1 = new Player('r', "Jay", true);
		Player player2 = new Player('b', "Player", false);
		Scanner move = new Scanner(System.in);
		Player currentPlayer = player2;
		int moveCol = 0;
		while(!gameOver){
			if(currentPlayer.equals(player1)){
				currentPlayer = player2;
				System.out.println("AI turn");
				gameboard.takeTurn(currentPlayer, 5, gameBoardGUI.pieces);
			}else{
				currentPlayer = player1;
			
		
			boolean invalidMove = true;
			while(invalidMove){
				System.out.println("your move");
				String yourMove = move.next();
				try {
			        moveCol = Integer.parseInt(yourMove);
			    } catch (IllegalArgumentException e) {
			        e.printStackTrace();
			    }
				if(moveCol == 8){
					int bestMove = currentPlayer.MiniMax(gameboard, 8);
					System.out.println(bestMove +"yooo");
					invalidMove = true;
				}else invalidMove = gameboard.fullColumn(moveCol);
			}
			int rowIndex = gameboard.checkHighestRow(moveCol);
			gameboard.addPiece(moveCol, currentPlayer.getColor(), gameBoardGUI.pieces);
			gameOver = gameboard.checkMoveForWin(rowIndex, moveCol, currentPlayer.getColor());
			}
			gameboard.displayBoard(); 
			System.out.println();
			
		}
		move.close();
		System.out.println("game over");
	}
	
	}
	
	

	
	

