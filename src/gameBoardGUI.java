import org.eclipse.swt.widgets.Display;
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
	public	static	Move[][] pieces = new Move[6][7];
	public static int gameMode = 0;
	protected Shell shell;
	public static Display display = Display.getDefault();

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
			
			Button btnSinglePlayer = new Button(shell, SWT.NONE);
			btnSinglePlayer.setBounds(70, 359, 203, 25);
			btnSinglePlayer.setText("Single Player");
			
			Button btnTwoPlayer = new Button(shell, SWT.NONE);
			btnTwoPlayer.addSelectionListener(new SelectionAdapter() {
				@Override
				
				
				public void widgetSelected(SelectionEvent e) {
				btnSinglePlayer.dispose();
				lblChooseYourSettings.dispose();
				lblL.dispose();
				btnTwoPlayer.dispose();
				gameMode = 2;
				createGameBoard(pieces);
				
				GamePlay.playGame();
				
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
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 7; j++){
				
				pieces[i][j] = new Move(shell, 5-i, j, 'w', 100 ,10, 90  );
				pieces[i][j].initiatePiece();
				
			}
		}	
		//display.update();

	}
}


