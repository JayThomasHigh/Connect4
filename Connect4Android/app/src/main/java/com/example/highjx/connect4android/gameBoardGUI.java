package com.example.highjx.connect4android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class gameBoardGUI extends AppCompatActivity {
    public static Move[][] moves = new Move[6][7];
    GridView myGrid;
    public static int moveMade;
    public static boolean moveReady = false;
    public static Semaphore condMove = new Semaphore(0);
    final moveAdapter adapter = new moveAdapter(this);
    public static GameBoard gameboard = new GameBoard();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gameboard = new GameBoard();
        createGameBoard();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardgui);
        myGrid = (GridView) findViewById(R.id.gridView);

        //ADAPTER: This is used to create the grid of pieces used for gameplay
        myGrid.setAdapter(adapter);


        //Depending on the gamemode, start a thread which starts the game with desired settings
        if(MainMenu.gameMode == 1) {
            Runnable r = new Runnable() {
                public void run() {
                    offlineGamePlay();
                }
            };
            Thread gameThread = new Thread(r);
            gameThread.start();
        }

        if(MainMenu.gameMode == 2) {
            Runnable r = new Runnable() {
                public void run() {
                    singleGamePlay();
                }
            };
            Thread gameThread = new Thread(r);
            gameThread.start();
        }

        if(MainMenu.gameMode == 3) {
            Runnable r = new Runnable() {
                public void run(){
                    try{
                    serverGamePlay();
                    }catch(IOException i){
                        System.out.println("IOException");
                    }
                }
            };
            Thread gameThread = new Thread(r);
            gameThread.start();
        }

        if(MainMenu.gameMode == 4) {
            Runnable r = new Runnable() {
                public void run(){
                    try{
                        clientPlay();
                    }catch(IOException i){
                        System.out.println("IOException");
                    }
                }
            };
            Thread gameThread = new Thread(r);
            gameThread.start();
        }




        //EVENTS
        myGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (moveReady) {
                    moveMade = position % 7;
                    moveReady = false;
                    condMove.release();
                }
            }
        });
    }

    /**
     * Takes in the mainmeun view button, returns to main menu and closes the activity on press
     */
    public void mainMenuOnClick(View v) {
        moveMade = 10;
        moveReady = false;
        condMove = new Semaphore(0);
        startActivity(new Intent(gameBoardGUI.this, MainMenu.class));
        finish();

    }


    protected void createGameBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                moves[i][j] = new Move(i, j, 1, 1, 1, 'w', R.drawable.white); //what to pass in to create a new one
            }
        }
    }

    /**
     * This is the offline two player gameplay logic
     */
    public void offlineGamePlay() {
        boolean gameOver = false;
        Player player1 = new Player('r', "Player 1");
        Player player2 = new Player('b', "Player 2");
        Random random = new Random();
       boolean randomTurn = random.nextBoolean();
        System.out.println();
        Player currentPlayer = player2;
        boolean quitGame = false;
        if(randomTurn) currentPlayer = player1;
        int moveCol = 0;
        while (!gameOver) {
            if(gameboard.isFull()){
                updateText("Game Over: Tie");
                gameOver = true;
                continue;
            }
            if (currentPlayer.equals(player1)) {
                currentPlayer = (player2);
            } else {
                currentPlayer = player1;
            }
            updateText(currentPlayer.name + "'s turn: ");
            boolean invalidMove = true;
            while (invalidMove) {
                System.out.println("your move");
                try {
                    gameBoardGUI.moveReady = true;
                    gameBoardGUI.condMove.acquire();
                    moveCol = gameBoardGUI.moveMade;
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
                if(moveCol == 10){
                    quitGame = true;
                    break;
                }
                invalidMove = gameboard.fullColumn(moveCol);
            }
            if( quitGame){
                break;
            }
            int rowIndex = gameboard.checkHighestRow(moveCol);
            gameboard.addPiece(moveCol, currentPlayer.color);
            updateBoard(rowIndex, moveCol, currentPlayer.color);
            if (gameboard.checkMoveForWin(rowIndex, moveCol, currentPlayer.color)) {
                gameOver = true;
                updateText("GAME OVER: "+ currentPlayer.name.toUpperCase() + " WON!!");
            }
        }


    }

    /**
     * This is the offline two player gameplay logic
     */
    public void singleGamePlay() {
        boolean gameOver = false;
        Player player1 = new Player('r', "Player 1");
        Player player2 = new Player('b', "Player 2");
        Random random = new Random();
        boolean randomTurn = random.nextBoolean();
        boolean moveTaken = false;
        boolean quitGame = false;
        int randomMove = random.nextInt(6);
        System.out.println("yo");
        gameboard.displayBoard();
        System.out.println();
        Player currentPlayer = player2;
        if(randomTurn) currentPlayer = player1;
        int moveCol = 0;
        while (!gameOver) {
            if(gameboard.isFull()){
                updateText("Game Over: Tie");
                gameOver = true;
            }
            if (currentPlayer.equals(player1)) {
                //AI TURN
                currentPlayer = (player2);
                if(!moveTaken){
                    int rowIndex = gameboard.checkHighestRow(randomMove);
                    gameboard.addPiece(randomMove, currentPlayer.color);
                    updateAIBoard(rowIndex, randomMove, currentPlayer.color);
                    moveTaken = true;
                } else{
                    int col = currentPlayer.MiniMax(gameboard, 8);
                    int row = gameboard.checkHighestRow(col);
                    gameboard.addPiece(col, currentPlayer.color);
                    updateBoard(row, col, currentPlayer.color);
                    if(gameboard.checkMoveForWin(row, col, currentPlayer.color)){
                        updateText("GAME OVER: AI WINS!");
                        gameOver = true;
                    }

                }
            } else {
                currentPlayer = player1;
                updateText(currentPlayer.name + "'s turn: ");
                boolean invalidMove = true;
                while (invalidMove) {
                    System.out.println("your move");
                    try {
                        gameBoardGUI.moveReady = true;
                        gameBoardGUI.condMove.acquire();
                        moveCol = gameBoardGUI.moveMade;
                    } catch (InterruptedException e) {
                        System.out.println("interrupted");
                    }
                    if(moveCol == 10){
                        quitGame = true;
                        break; //if main menu button clicked then break gameplay loop
                    }
                    invalidMove = gameboard.fullColumn(moveCol);
                }
                if(quitGame) break;
                int rowIndex = gameboard.checkHighestRow(moveCol);
                gameboard.addPiece(moveCol, currentPlayer.color);
                updateBoard(rowIndex, moveCol, currentPlayer.color);
                if (gameboard.checkMoveForWin(rowIndex, moveCol, currentPlayer.color)) {
                    gameOver = true;
                    updateText("GAME OVER YOU WON!");
                }
            }
            }


    }

    /**
     * The following function is ran by the thread which hosts an online game
     */
    public void serverGamePlay() throws IOException{
        updateText("Connection waiting");
        ServerSocket serverSocket = new ServerSocket(MainMenu.portNumber);
        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        updateText("Connection Made");
        Random random = new Random();
        boolean randomPlayer = random.nextBoolean();
        int inputMove;
        boolean gameOver = false;
        boolean threadBreak = false;
        Player serverPlayer = new Player('r', "Player 1");
        Player clientPlayer = new Player('b', "Player 2");
        Player currentPlayer = clientPlayer;
        if(!randomPlayer) currentPlayer = serverPlayer;
        int moveCol = 0;
        while(!gameOver){
            if(gameboard.isFull()){
                updateText("Game Over: Tie");
                gameOver = true;
                out.println("tie");
                continue;
            }
            if(currentPlayer.equals(serverPlayer)){
                currentPlayer = clientPlayer; //this is the case that it is the clients move
                updateText("Clients Turn");
                out.println("Clients Turn");
                boolean clientInvalid = true;
                while(clientInvalid == true){
                    String clientMove = in.readLine();
                    inputMove = Integer.parseInt(clientMove);
                    if(inputMove == 10){
                        threadBreak = true;
                        break; //if main menu button clicked then break gameplay loop
                    }
                    if(gameboard.fullColumn(inputMove)){
                        out.println("invalidMove");
                        continue;
                    } else{
                        int rowIndex = gameboard.checkHighestRow(inputMove);
                        gameboard.addPiece(inputMove, currentPlayer.color);
                        updateBoard(rowIndex, inputMove, currentPlayer.color);
                        clientInvalid = false;
                        out.println("validMove");
                        out.println(rowIndex);
                        gameOver = gameboard.checkMoveForWin(rowIndex, inputMove, currentPlayer.color);
                        if(gameOver == true){
                            out.println("youwin");
                            updateText("BOO CLIENT WINS");
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
                    if(invalidMove) continue;
                    int rowIndex = gameboard.checkHighestRow(moveCol);
                    gameboard.addPiece(moveCol, currentPlayer.color);
                    updateBoard(rowIndex, moveCol, currentPlayer.color);
                    out.println(rowIndex);
                    out.println(moveCol);

                    gameOver = gameboard.checkMoveForWin(rowIndex, moveCol, currentPlayer.color);
                }
                if(gameOver){
                    updateText("CONGRATULATIONS YOU WIN!");
                    out.println("serverwin");
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


    /**
     * This function is used for the client gameplay for online
     */
    public void clientPlay() throws IOException{
        updateText("Connection waiting");
        final char yourColor = 'r';
        final char opponentColor = 'b';
        Socket clientSocket = new Socket(MainMenu.hostName, MainMenu.portNumber);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
        updateText("Connection Made");
        boolean gameOver = false;
        boolean threadBreak = false;
        while(!gameOver){
            String serverResponse = in.readLine();
            if(serverResponse.equals("Clients Turn")){
                updateText("Your Turn");
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
                    out.println(moveCol);
                    if(moveCol == 10){
                        threadBreak = true;
                        break;
                    }
                    String moveCondition = in.readLine();
                    if(moveCondition.equals("invalidMove")){
                        continue;
                    } else if(moveCondition.equals("validMove")) {
                        String rowMove = in.readLine();
                        int rowInt = Integer.parseInt(rowMove);
                        invalidMove = false;
                        updateBoard(rowInt, moveCol, yourColor);
                    }

                }
            } else if (serverResponse.equals("Server Turn")){
                updateText("Opponents Turn");
                int rowMove = Integer.parseInt(in.readLine());
                int colMove = Integer.parseInt(in.readLine());
                updateBoard(rowMove, colMove, opponentColor);
            } else if (serverResponse.equals("youwin")){
                updateText("CONGRATS YOU WIN");
                gameOver = true;
            }else if (serverResponse.equals("serverwin")){
                updateText( "OPPONENT WINS");
                gameOver = true;
            } else if (serverResponse.equals("tie")){
                 updateText( "Game Over: Tie");
                  gameOver = true;
        }
            if(threadBreak) break;

        }
        in.close();
        out.close();
        clientSocket.close();
    }




    /**
     *This logic is used to update the graphical interface of the gameboard, and display a Toast of
     * the update
     */
    public void updateBoard(int row, int col, char color) {
        final int COL = col;
        final char COLOR = color;
        final int ROW = row;
        this.runOnUiThread(new Runnable() {
            public void run() {
                moves[5 - ROW][COL].changeColor(COLOR);
                adapter.notifyDataSetChanged();
            }

        });
    }

    /**
     *This logic is used to update the graphical interface of the gameboard
     */
    public void updateAIBoard(int row, int col, char color) {
        final int COL = col;
        final char COLOR = color;
        final int ROW = row;
        this.runOnUiThread(new Runnable() {
            public void run() {
                moves[5 - ROW][COL].changeColor(COLOR);
             //   Toast.makeText(getApplicationContext(), "AI Clicked " + Integer.toString(COL), Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }

        });
    }

    /**
     * This takes in a desired string, and tells the GUI to change the text displayed on screen
     */
    public void updateText(String text){
        final String TEXT = text;
        this.runOnUiThread(new Runnable() {
            public void run() {
                TextView textView = (TextView) findViewById(R.id.gameText);
                textView.setText(TEXT);
                adapter.notifyDataSetChanged();
            }
        });
    }


}






