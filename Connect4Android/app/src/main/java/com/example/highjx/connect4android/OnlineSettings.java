package com.example.highjx.connect4android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class OnlineSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_settings);
    }

    /**
     * Takes in user entered port info and runs the game as the server
     */
    public void onlineServerOnClick(View v) {
        try {
            MainMenu.gameMode = 3;
            EditText editPort = (EditText) findViewById(R.id.editPort);
            MainMenu.portNumber = Integer.parseInt(editPort.getText().toString());
        } catch (NullPointerException n) {
            System.out.println("errorNull");
        }
        startActivity(new Intent(OnlineSettings.this, gameBoardGUI.class));
    }


    /**
     * Takes in user entered port and host info and runs the game as the client
     */
    public void onlineClientOnClick(View v){
        MainMenu.gameMode = 4;
        try{
            EditText editPort = (EditText) findViewById(R.id.editPort);
            MainMenu.portNumber = Integer.parseInt(editPort.getText().toString());
            EditText editHost = (EditText) findViewById(R.id.editHost);
            MainMenu.hostName = editHost.getText().toString();
        } catch (NullPointerException n){
            System.out.println("null error");
        }

        startActivity(new Intent(OnlineSettings.this, gameBoardGUI.class));

    }



}
