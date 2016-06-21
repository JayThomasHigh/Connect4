import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
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

public static void hostGamePlay( int port) throws IOException{
	
		ServerSocket serverSocket = new ServerSocket(port);
		Socket clientSocket = serverSocket.accept(); //waits for a socket to connect to the port of serverSocket
		PrintWriter out =new PrintWriter(clientSocket.getOutputStream(), true); //server output
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));	//server input

		int inputMove, outputMove;
		boolean gameOver = false;
		GameBoard gameboard = new GameBoard();
		Player serverPlayer = new Player('r', "Jay", true);
		Player clientPlayer = new Player('b', "Player", false);
		
		
	//	Scanner move = new Scanner(System.in);
		
		
		Player currentPlayer = clientPlayer;
		int moveCol = 0;
		boolean threadBreak = false;
		while(!gameOver){
			if(currentPlayer.equals(serverPlayer)){
				currentPlayer = clientPlayer; //this is the case that it is the clients move
				changeLabelText(gameBoardGUI.gameText, "Clients Turn");
				out.print("Clients Turn");
				boolean clientInvalid = true;
				while(clientInvalid == true){
					while(!in.ready()){//block while waiting
					}
					String clientMove = in.readLine();
					inputMove = Integer.parseInt(clientMove);
					if(gameboard.fullColumn(inputMove)){
						out.println("invalidMove");
						continue;
					} else{
						int rowIndex = gameboard.checkHighestRow(inputMove);
						gameboard.addPiece(moveCol, currentPlayer.getColor(), gameBoardGUI.pieces);
						clientInvalid = false;
						out.print("validMove");
						out.print(rowIndex); 
						gameOver = gameboard.checkMoveForWin(rowIndex, moveCol, currentPlayer.getColor());
						if(gameOver = true){
							out.print("youwin");
							changeLabelText(gameBoardGUI.gameText, "You bad, Client Wins!");
						}
					}	
				}
			}else{
				currentPlayer = serverPlayer;
				out.println("Server Turn");

			
				boolean invalidMove = true;
				while(invalidMove){
					try {
						gameBoardGUI.moveReady = true;
						gameBoardGUI.condMove.acquire();
						moveCol = gameBoardGUI.moveMade;
				    } catch (InterruptedException e) {
				        e.printStackTrace();
				    }
					
					if(moveCol == 10){ //we should probably send message saying that player left the game
						threadBreak = true;
						break; 				
					}else invalidMove = gameboard.fullColumn(moveCol);
					int rowIndex = gameboard.checkHighestRow(moveCol);
					gameboard.addPiece(moveCol, currentPlayer.getColor(), gameBoardGUI.pieces);
					
					//Here we want to send message to client to update their gui
					out.print(rowIndex);
					out.print(moveCol);
					
					gameOver = gameboard.checkMoveForWin(rowIndex, moveCol, currentPlayer.getColor());
					}
					if(gameOver){
						changeLabelText(gameBoardGUI.gameText, "Congratulations, you win!");
						out.print("serverwin");
					}
					
				}
				if(threadBreak==true){ 
					break;
				}
				
			}
		in.close();
		out.close();
		clientSocket.close();
		serverSocket.close();
}



		
		
		
		
		
	public static void clientPlay(String hostName, int portNumber) throws UnknownHostException, IOException{
		char yourColor = 'r';
		char opponentColor = 'b';
		Socket clientSocket = new Socket(hostName, portNumber);
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
		boolean gameOver = false;
		while(!gameOver){
			while(!in.ready()){ //good old spinlock
			}
			String serverResponse = in.readLine();
			
			//if it is your turn
			if(serverResponse.equals("Clients Turn")){
				changeLabelText(gameBoardGUI.gameText, "Your Turn");
				boolean invalidMove = true;
				while(invalidMove){
					int moveCol = 0;
					try {
						gameBoardGUI.moveReady = true;
						gameBoardGUI.condMove.acquire();
						moveCol = gameBoardGUI.moveMade;
				    } catch (InterruptedException e) {
				        e.printStackTrace();
				    }
					out.print(moveCol);
					while(!in.ready()){} //spinlock 
					if(in.readLine().equals("invalidMove")){
						continue;
					} else if(in.readLine().equals("validMove")) {
						while(!in.ready()){}
						String rowMove = in.readLine();
						int rowInt = Integer.parseInt(rowMove);
						gameBoardGUI.pieces[rowInt][moveCol].changeColor(yourColor);	//sets our gui to desired color								
					}
				
				}
			} else if (serverResponse.equals("Server Turn")){
				changeLabelText(gameBoardGUI.gameText, "Server's Turn");
				while(!in.ready()){} //spinlock
				int rowMove = Integer.parseInt(in.readLine());
				while(!in.ready()){}
				int colMove = Integer.parseInt(in.readLine());
				gameBoardGUI.pieces[rowMove][colMove].changeColor(opponentColor);	//sets our gui to desired color				
			} else if (serverResponse.equals("youwin")){
				changeLabelText(gameBoardGUI.gameText, "Congratulations you win");
				gameOver = true;
			}else if (serverResponse.equals("serverwin")){
				changeLabelText(gameBoardGUI.gameText, "The Server wins");
				gameOver = true;
			}			
		}
		in.close();
		out.close();
		clientSocket.close();
	}
	}
		
		
		
		
		
		
		
		
	





	
	

