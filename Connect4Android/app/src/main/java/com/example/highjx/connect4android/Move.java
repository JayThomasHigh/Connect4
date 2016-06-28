package com.example.highjx.connect4android;

import android.widget.ImageView;

/**
 * Created by highjx on 6/22/2016.
 */
public class Move {
    public int row;
    public int col;
    public int offsetY;
    public int offsetX;
    public int size;
    public char color;
    public int imageID;

    //The constructor
    public Move(int row, int col, int offsetY, int offsetX, int size, char color, int imageID){
        this.row = row;
        this.col = col;
        this.offsetY = offsetY;
        this.offsetX = offsetX;
        this.size = size;
        this.color = color;
        this.imageID = imageID;
    }




    /**
     * This takes in a desired color, and sets the color and imadeID associated with that color
     */
    public void changeColor(char c){
        this.color = c;
        if(color == 'b'){
            imageID = R.drawable.black;
        }else if (color == 'r') {
            imageID = R.drawable.red;
        } else imageID = R.drawable.white;
    }


}
