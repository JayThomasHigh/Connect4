package com.example.highjx.connect4android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by highjx on 6/23/2016.
 */

public class moveAdapter extends BaseAdapter {

    Context context;

    moveAdapter(Context context){
        this.context = context;


    }



    /**
     * Returns number of elements inside our grid
     */
    @Override
    public int getCount() {
        return 42;
    }

    /**
     * Returns object at a specific index
     */
    @Override
    public Object getItem(int position) {
        int row = position/7;
        int column = position%7;
        return gameBoardGUI.moves[row][column];
    }

    /**
     *
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View view, ViewGroup parent) //view is null when creating row for first time, not null otherwise
    {
        int rowIndex = position/7;
        int column = position%7;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view == null) {
            view = inflater.inflate(R.layout.single_layout, parent, false);
        }
        //Our Views
        ImageView img =(ImageView) view.findViewById(R.id.imageView);

        //set data
       if(gameBoardGUI.moves[position/7][position%7]!=null) {
           img.setImageResource(gameBoardGUI.moves[position / 7][position % 7].imageID);
       } else {
           img.setImageResource(R.drawable.white);
       }
        return view;
    }

}

