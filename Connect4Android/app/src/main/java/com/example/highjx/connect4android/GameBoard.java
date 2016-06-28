package com.example.highjx.connect4android;

import android.widget.Adapter;
import android.widget.Toast;

/**
 * Created by highjx on 6/22/2016.
 */
public class GameBoard {
    char[][] board; //The actual game board, will hold w, b, and r where w is blank
    int numRows;
    int numCols;

    //Constructor
    public GameBoard() {
        this.numRows = 6;
        this.numCols = 7;
        this.board = new char[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                board[i][j] = 'w'; //Fill in every slot as white
            }
        }
    }

///Functions
    /**
     * Returns true if the gameboard is full and no more moves can be made, false otherwise
     */
    public boolean isFull() {
        for (int i = 0; i < 7; i++) {
            if (!fullColumn(i)) {
                return false;
            }
        }
        return true;
    }


    /**
     *
     * @param col Takes in a column index for a move
     * @return returns true if that column is full, false otherwise
     */
    public boolean fullColumn(int col) {
        try {
            if (this.board[numRows - 1][col] == 'w') {
                return false;
            }
            return true;
        } catch (Exception ex) {
            System.out.println("The column you requested is out of bounds");
            return true; //This will return true as the column is nonexistent and thus does not have room for moves
        }
    }

    /**
     * Given a column index, returns the lowest possible row index such that a move can be made
     */
    public int checkHighestRow(int col) {
        int row = 0;
        while (this.board[row][col] != 'w') {
            row++;
            if (row == 5) {
                return 5;
            }
        }
        return row;
    }

    /**
     * Takes in the move index column, as well as the move color and adds that move to the gameboard
     */
    public void addPiece(int col, char color) {
        for (int i = 0; i < this.numRows; i++) {
            if (this.board[i][col] == 'w') {
                this.board[i][col] = color;
                break;
            }
        }
    }

    /**
     * This takes in a row and column of the most recent move as well as its color and checks
     * all possible directions for a four in a row. It then returns true if the game has been won
     * and false otherwise.
     */
    public boolean checkMoveForWin(int rowOrigin, int colOrigin, char color) {
        int row = rowOrigin;
        int col = colOrigin;
        int rowCount = 1;
        int colCount = 1;
        int lDiagCount = 1;
        int rDiagCount = 1;
        while (row > 0) {
            row--;
            if (this.board[row][col] == color) {
                rowCount++;
            } else {
                break;
            }
        }
        row = rowOrigin;
        while (row < this.numRows - 1) {
            row++;
            if (this.board[row][col] == color) {
                rowCount++;
            } else {
                break;
            }
        }
        row = rowOrigin;
        if (rowCount >= 4) return true;
        while (col > 0) {
            col--;
            if (this.board[row][col] == color) {
                colCount++;
            } else break;
        }
        col = colOrigin;
        while (col < this.numCols - 1) {
            col++;
            if (this.board[row][col] == color) {
                colCount++;
            } else break;
        }
        col = colOrigin;
        if (colCount >= 4) return true;
        while (col > 0 && row < numRows - 1) {
            col--;
            row++;
            if (this.board[row][col] == color) {
                lDiagCount++;
            } else break;
        }
        col = colOrigin;
        row = rowOrigin;
        while (col < numCols - 1 && row > 0) {
            col++;
            row--;
            if (this.board[row][col] == color) {
                lDiagCount++;
            } else break;
        }
        col = colOrigin;
        row = rowOrigin;
        if (lDiagCount >= 4) return true;
        while (col > 0 && row > 0) {
            col--;
            row--;
            if (this.board[row][col] == color) {
                rDiagCount++;
            } else break;
        }
        col = colOrigin;
        row = rowOrigin;
        while (col < numCols - 1 && row < numRows - 1) {
            col++;
            row++;
            if (this.board[row][col] == color) {
                rDiagCount++;
            } else break;
        }
        if (rDiagCount >= 4) return true;
        return false;
    }


    /**
     * Given paramaters describing the most recent move, this will return a score based on how
     * good the move is.
     */
    public double checkMoveForScore(int rowOrigin, int colOrigin, char color) {
        int row = rowOrigin;
        int col = colOrigin;
        int rowCount = 1;
        int colCount = 1;
        int lDiagCount = 1;
        int rDiagCount = 1;
        while (row > 0) {
            row--;
            if (this.board[row][col] == color) {
                rowCount++;
            } else {
                break;
            }
        }
        row = rowOrigin;
        while (row < this.numRows - 1) {
            row++;
            if (this.board[row][col] == color) {
                rowCount++;
            } else {
                break;
            }
        }
        row = rowOrigin;
        if (rowCount >= 4) return 4;
        while (col > 0) {
            col--;
            if (this.board[row][col] == color) {
                colCount++;
            } else break;
        }
        col = colOrigin;
        while (col < this.numCols - 1) {
            col++;
            if (this.board[row][col] == color) {
                colCount++;
            } else break;
        }
        col = colOrigin;
        if (colCount >= 4) return 4;
        while (col > 0 && row < numRows - 1) {
            col--;
            row++;
            if (this.board[row][col] == color) {
                lDiagCount++;
            } else break;
        }
        col = colOrigin;
        row = rowOrigin;
        while (col < numCols - 1 && row > 0) {
            col++;
            row--;
            if (this.board[row][col] == color) {
                lDiagCount++;
            } else break;
        }
        col = colOrigin;
        row = rowOrigin;
        if (lDiagCount >= 4) return 4;
        while (col > 0 && row > 0) {
            col--;
            row--;
            if (this.board[row][col] == color) {
                rDiagCount++;
            } else break;
        }
        col = colOrigin;
        row = rowOrigin;
        while (col < numCols - 1 && row < numRows - 1) {
            col++;
            row++;
            if (this.board[row][col] == color) {
                rDiagCount++;
            } else break;
        }
        if (rDiagCount >= 4) return 4;
        int maxScore = Math.max(Math.max(Math.max(rowCount, colCount), lDiagCount), rDiagCount);
        return 2 ^ maxScore;
    }


    /**
     * This function displays the board in text on the terminal, was used for testing purposes
     */
    public void displayBoard() {
        for (int i = numRows - 1; i >= 0; i--) {
            for (int j = 0; j < numCols; j++) {
                System.out.print(Character.toString(this.board[i][j]) + " ");
            }
            System.out.println();
        }
    }

    /**
     * Given a column, this will delete the most recent move made there
     */
    public void deleteMove(int col) {
        int deleteIndex = this.numRows;
        while (deleteIndex > 0) {
            deleteIndex--;
            if (this.board[deleteIndex][col] != 'w') {
                this.board[deleteIndex][col] = 'w';
                break;
            }
        }
    }

    /**
     * Performs the minimax algorithm to a specified depth on a given boardgame, then performs the
     * best found move and returns true if the move wins the game
     */
    public boolean takeTurn(Player player, int depth, Move[][] piece) {
        int move = player.MiniMax(this, depth);
        int rowIndex = checkHighestRow(move);
        addPiece(move, player.color, piece);
        return checkMoveForWin(rowIndex, move, player.color);
    }

    /**
     * This will take in a column, color and an array of moves for the GUI to make a move
     */
    public void addPiece(int col, char color, final Move[][] pieces) {
        for (int row = 0; row < this.numRows; row++) {
            if (this.board[row][col] == 'w') {
            }
        }
    }
}
