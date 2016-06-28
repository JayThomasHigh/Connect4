package com.example.highjx.connect4android;

/**
 * Created by highjx on 6/22/2016.
 */
public class Player {
    public char color;
    public String name;
    public char oppositeColor;
    private static int [][] evaluationTable =   {{3, 4, 5, 7, 5, 4, 3},
            {4, 6, 8, 10, 8, 6, 4},
            {5, 8, 11, 13, 11, 8, 5},
            {5, 8, 11, 13, 11, 8, 5},
            {4, 6, 8, 10, 8, 6, 4},
            {3, 4, 5, 7, 5, 4, 3}};


    /**
     * Constructor for the player
     * @param color
     */
    public Player(char color, String name){
        this.color = color;
        if(color == 'b'){
            this.oppositeColor = 'r';
        } else this.oppositeColor = 'b';
        this.name = name;

    }


    /**
     * Initiates the MiniMax algorithm, to find the best move for the AI
     * @param game takes in the com.example.highjx.connect4android.GameBoard state the algorithm is being ran on
     * @param depth takes in the desired search depth for the algorithm
     * @returns the position of the best found move
     */
    public int MiniMax(GameBoard game, int depth){
        double maxScore = -10000;
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
                    double score = minMove(game, depth-1, 1);
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

    /**
     * the maxcall of the algorithm
     * @param game takes in the current game state
     * @param depth remaining depth of algorithm, algorithm stops when it reaches zero
     * @param depthOccurred takes in how long the algorithm has searched for, used to calculate score
     *                      so that further out wins/losses are not prioritized
     * @returns the score of the best move scenario of subtree from the current game state
     */
    public double maxMove(GameBoard game, int depth, int depthOccurred){
        if(depth == 0){
            return 0;
        }else{
            double maxScore = -10000;
            for(int i = 0; i<game.numCols; i++){
                if(!game.fullColumn(i)){
                    int rowMove = game.checkHighestRow(i);
                    game.addPiece(i, this.color);
                    if(game.checkMoveForWin(rowMove, i, this.color)){
                        game.deleteMove(i);
                        return 5000/depthOccurred;
                    } else {
                        double score = (game.checkMoveForScore(rowMove, i, this.color)*evaluationTable[rowMove][i] + minMove(game, depth-1, depthOccurred))/(depthOccurred*3);
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

    /**
     * the min call of the algorithm
     * @param game takes in the current game state
     * @param depth remaining depth of algorithm, algorithm stops when it reaches zero
     * @param depthOccurred takes in how long the algorithm has searched for, used to calculate score
     *                      so that further out wins/losses are not prioritized
     * @returns the score of the best move scenario of subtree from the current game state
     */
    public double minMove(GameBoard game, int depth, int depthOccurred){
        if(depth == 0){
            return 0; //might evaluate instead
        }else{
            double maxScore = -5000;
            for(int i = 0; i < game.numCols; i++){
                if(!game.fullColumn(i)){
                    int rowMove = game.checkHighestRow(i);
                    game.addPiece(i, this.oppositeColor);
                    if(game.checkMoveForWin(rowMove, i, this.oppositeColor)){
                        game.deleteMove(i);
                        return -5000/depthOccurred;
                    } else {
                        double score = (-1 *game.checkMoveForScore(rowMove, i, this.oppositeColor)* evaluationTable[rowMove][i] + maxMove(game, depth-1, depthOccurred+1))/(depthOccurred*3);
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