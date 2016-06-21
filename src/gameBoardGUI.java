import org.eclipse.swt.widgets.Display;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

//import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class gameBoardGUI {
	public	static Move[][] pieces = new Move[6][7];
	public static int gameMode = 0;
	protected static Shell shell;
	public static Display display = Display.getDefault();
	public static int moveMade;
	public static boolean moveReady = false;
	public static Semaphore condMove = new Semaphore(0);
	public static Label gameText;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		try {
			gameBoardGUI window = new gameBoardGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
	//	Display display = Display.getDefault();
		createContents();
		//createGameBoard(pieces);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(390, 550);
		shell.setText("SWT Application");
		try{
			Image bgImage = new Image(null, "src/resources/bg.jpg");
			shell.setBackgroundImage(bgImage);
			
			Label lblL = new Label(shell, SWT.NONE);
			lblL.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			lblL.setFont(SWTResourceManager.getFont("Small Fonts", 15, SWT.BOLD));
			lblL.setAlignment(SWT.CENTER);
			lblL.setBounds(56, 59, 217, 46);
			lblL.setText("CONNECT FOUR");
			
			Label lblChooseYourSettings = new Label(shell, SWT.NONE);
			lblChooseYourSettings.setFont(SWTResourceManager.getFont("Small Fonts", 13, SWT.NORMAL));
			lblChooseYourSettings.setBounds(93, 301, 180, 20);
			lblChooseYourSettings.setText("Choose Your Settings");
			
			Button btnTwoPlayer = new Button(shell, SWT.NONE);

			
			Button btnSinglePlayer = new Button(shell, SWT.NONE);
			btnSinglePlayer.setBounds(70, 359, 203, 25);
			btnSinglePlayer.setText("Single Player");
			btnSinglePlayer.addSelectionListener(new SelectionAdapter() {
				@Override

				public void widgetSelected(SelectionEvent e) {
				//clears the old pieces
				btnSinglePlayer.dispose();
				lblChooseYourSettings.dispose();
				lblL.dispose();
				btnTwoPlayer.dispose();
				gameMode = 2;
				
				//creates the gameboard
				createGameBoard(pieces);

				
				//runs the singleplayer game
				final Thread singleGame = new Thread(new Runnable() {
					public void run(){
						try {
							GamePlay.playGame();
						} catch (URISyntaxException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				singleGame.start();
				}
			});
			
			btnTwoPlayer.addSelectionListener(new SelectionAdapter() {
				@Override

				public void widgetSelected(SelectionEvent e) {
				//clears the old pieces
				btnSinglePlayer.dispose();
				lblChooseYourSettings.dispose();
				lblL.dispose();
				btnTwoPlayer.dispose();
				gameMode = 2;
				
				//creates the gameboard
				createGameBoard(pieces);

				
				//runs the singleplayer game
				final Thread multiGame = new Thread(new Runnable() {
					public void run(){
						try {
							GamePlay.play2PGame();
						} catch (URISyntaxException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				multiGame.start();
				}
			});
			btnTwoPlayer.setBounds(70, 413, 203, 25);
			btnTwoPlayer.setText("Two Player");
		} catch (Exception e){
			
		}

	}
	/**
	 * Create the main game menu
	 */
/*	protected void mainMenu(){
		try{
			Image titleImage = new Image(null, "src/resources/title.jpg");
			Label titleLabel = new Label(this.shell, SWT.NONE);
			//titleLabel.setImage(titleImage);
			titleLabel.setBounds(35,20, 300, 100);

		} catch (Exception e){
			
		}
	}
	*/
	/**
	 * Create the gameboard, and initiate game moves
	 */
	protected void createGameBoard(Move[][] pieces){
		
		final Image btImage = new Image(null, "src/resources/button.png");
		

		//creates the buttons
		
		Button mainMenuButton = new Button(shell, SWT.NONE);
		mainMenuButton.setBounds(90, 10, 200, 30);
		mainMenuButton.setText("Return to Menu");
		mainMenuButton.setFont(SWTResourceManager.getFont("Miriam Fixed", 14, SWT.NORMAL));
		mainMenuButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(moveReady = true){
					moveMade = 10; //This will be the termination code
					moveReady = false;
					condMove = new Semaphore(0);	
				    gameMode = 0;
				    display = Display.getDefault();
					try {
						shell.dispose();
						gameBoardGUI window = new gameBoardGUI();
						window.open();
					} catch (Exception ee) {
						ee.printStackTrace();
					}
					
			}
			}
		});
		
		Button btnMoveOne = new Button(shell, SWT.NONE);
		btnMoveOne.setBounds(10, 50, 50, 50);
		btnMoveOne.setImage(btImage);
		btnMoveOne.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			if(moveReady = true){
				moveMade = 0;
				moveReady = false;
				condMove.release();
			}
			}
			
		});
		
		
		Button btnMoveTwo = new Button(shell, SWT.NONE);
		btnMoveTwo.setBounds(60, 50, 50, 50);
		btnMoveTwo.setImage(btImage);
		btnMoveTwo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			if(moveReady = true){
				moveMade = 1;
				moveReady = false;
				condMove.release();
			}
			}
			
		});
		
		Button btnMoveThree = new Button(shell, SWT.NONE);
		btnMoveThree.setBounds(110, 50, 50, 50);
		btnMoveThree.setImage(btImage);
		btnMoveThree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			if(moveReady = true){
				moveMade = 2;
				moveReady = false;
				condMove.release();
			}
			}
			
		});
		
		Button btnMoveFour = new Button(shell, SWT.NONE);
		btnMoveFour.setBounds(160, 50, 50, 50);
		btnMoveFour.setImage(btImage);	
		btnMoveFour.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			if(moveReady = true){
				moveMade = 3;
				moveReady = false;
				condMove.release();
			}
			}
			
		});
		
		Button btnMoveFive = new Button(shell, SWT.NONE);
		btnMoveFive.setBounds(210, 50, 50, 50);
		btnMoveFive.setImage(btImage);
		btnMoveFive.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			if(moveReady = true){
				moveMade = 4;
				moveReady = false;
				condMove.release();
			}
			}
			
		});
		
		Button btnMoveSix = new Button(shell, SWT.NONE);
		btnMoveSix.setBounds(260, 50, 50, 50);
		btnMoveSix.setImage(btImage);
		btnMoveSix.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			if(moveReady = true){
				moveMade = 5;
				moveReady = false;
				condMove.release();
			}
			}
			
		});
		
		Button btnMoveSeven = new Button(shell, SWT.NONE);
		btnMoveSeven.setBounds(310, 50, 50, 50);
		btnMoveSeven.setImage(btImage);
		btnMoveSeven.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			if(moveReady = true){
				moveMade = 6;
				moveReady = false;
				condMove.release();
			}
			}
			
		});
		
		gameText = new Label(shell, SWT.NONE);
		gameText.setBounds(30, 450, 200, 20);
		gameText.setText("Player 1's Turn");
		gameText.setFont(SWTResourceManager.getFont("Miriam Fixed", 14, SWT.NORMAL));

		
		
		//creates the main pieces
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 7; j++){
				
				pieces[i][j] = new Move(shell, 5-i, j, 'w', 100 ,10, 90  );
				pieces[i][j].initiatePiece();
				
			}
		}	
		
		display.update();
		if (!gameBoardGUI.display.readAndDispatch()) {
			gameBoardGUI.display.sleep();
		}

	}
	
	
	
	
	
	
	
	
}



