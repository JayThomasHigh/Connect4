package com.example.highjx.connect4android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.net.Inet4Address;

public class MainMenu extends AppCompatActivity {
    public static int gameMode = 0;
    public static Integer portNumber;
    public static String hostName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    /**
     * This function is for singaleplayer
     */
    public void singlePlayerOnClick(View v){
        gameMode = 2;
        startActivity(new Intent(MainMenu.this, gameBoardGUI.class));

    }

    /**
     * this is for offline multiplayer
     */
    public void offlineTwoPlayerOnClick(View v){
        gameMode = 1;
        startActivity(new Intent(MainMenu.this, gameBoardGUI.class));
    }

    /**
     * This function is for Online
     */
    public void onlineSettingsOnClick(View v){
        startActivity(new Intent(MainMenu.this, OnlineSettings.class));
    }
    }




