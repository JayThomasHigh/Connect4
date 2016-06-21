import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.eclipse.swt.widgets.Label;
public class GamePlay {

	/*public static void main(String[] args){
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
	
	*/
	
	
	static public void playGame() throws URISyntaxException, IOException{
		
		
		boolean gameOver = false;
		GameBoard gameboard = new GameBoard();
		Player player1 = new Player('r', "Jay", true);
		Player player2 = new Player('b', "Player", false);
		
		
	//	Scanner move = new Scanner(System.in);
		
		
		Player currentPlayer = player2;
		int moveCol = 0;
		boolean threadBreak = false;
		while(!gameOver){
			if(currentPlayer.equals(player1)){
				currentPlayer = player2;
				System.out.println("AI turn");
				if(gameboard.takeTurn(currentPlayer, 8, gameBoardGUI.pieces)){
					System.out.println("game over: AI WINS!");
					changeLabelText(gameBoardGUI.gameText, "game over: AI WINS!");
					break;
				}
			}else{
				currentPlayer = player1;
				
			
				boolean invalidMove = true;
				while(invalidMove){
					System.out.println("your move");
				//	String yourMove = move.next();
					try {
				       // moveCol = Integer.parseInt(yourMove);
						gameBoardGUI.moveReady = true;
						gameBoardGUI.condMove.acquire();
						moveCol = gameBoardGUI.moveMade;
				    } catch (InterruptedException e) {
				        e.printStackTrace();
				    }
					
				//exit code
					if(moveCol == 10){
						threadBreak = true;
						break; //if main menu button clicked then break gameplay loop
						
					}
					
				//hint code
					if(moveCol == 8){
						int bestMove = currentPlayer.MiniMax(gameboard, 8);
						System.out.println(bestMove +"yooo");
						invalidMove = true;
					}else invalidMove = gameboard.fullColumn(moveCol);
				}
				if(threadBreak==true) break;
				int rowIndex = gameboard.checkHighestRow(moveCol);
				gameboard.addPiece(moveCol, currentPlayer.getColor(), gameBoardGUI.pieces);
				gameOver = gameboard.checkMoveForWin(rowIndex, moveCol, currentPlayer.getColor());
				}
				if(gameOver)changeLabelText(gameBoardGUI.gameText, "Congratulations, you win!");


			}
			gameboard.displayBoard(); 
			System.out.println();
			
		}
		//move.close();

	
	
	

	static public void play2PGame() throws URISyntaxException, IOException{
		
		
		boolean gameOver = false;
		GameBoard gameboard = new GameBoard();
		Player player1 = new Player('r', "Player1", true);
		Player player2 = new Player('b', "Player2", false);
		
		
	//	Scanner move = new Scanner(System.in);
		
		
		Player currentPlayer = player2;
		int moveCol = 0;
		boolean threadBreak = false;
		while(!gameOver){
			if(currentPlayer.equals(player1)){
				currentPlayer = player2;
			} else {
				currentPlayer = player1;
				
			}
			boolean invalidMove = true;
			while(invalidMove){
				System.out.println("your move");
				//	String yourMove = move.next();
				try {
				       // moveCol = Integer.parseInt(yourMove);
					gameBoardGUI.moveReady = true;
					gameBoardGUI.condMove.acquire();
					moveCol = gameBoardGUI.moveMade;
			    } catch (InterruptedException e) {
			        e.printStackTrace();
			    }
					
				//exit code
				if(moveCol == 10){
					threadBreak = true;
					break; //if main menu button clicked then break gameplay loop
						
				}
					
				//hint code
				if(moveCol == 8){
					int bestMove = currentPlayer.MiniMax(gameboard, 8);
					System.out.println(bestMove +"yooo");
					invalidMove = true;
				}else invalidMove = gameboard.fullColumn(moveCol);
				}
				if(threadBreak==true) break;
				int rowIndex = gameboard.checkHighestRow(moveCol);
				gameboard.addPiece(moveCol, currentPlayer.getColor(), gameBoardGUI.pieces);
				gameOver = gameboard.checkMoveForWin(rowIndex, moveCol, currentPlayer.getColor());
				
				if(gameOver)changeLabelText(gameBoardGUI.gameText, "Congratulations, you win: "+ currentPlayer.name);


			}
			gameboard.displayBoard(); 
			System.out.println();
			
		}
		//move.close();

	
	
//takes in a label and a message you want to change the message displayed to	
public static void changeLabelText(Label label, String message){
	gameBoardGUI.display.asyncExec(new Runnable() {
		public void run(){
			label.setText(message);
		}
	});

}




}
	
	

