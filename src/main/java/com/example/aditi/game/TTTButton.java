package com.example.aditi.game;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;
import android.widget.Toast;

public class TTTButton extends AppCompatButton{

    private int player = MainActivity.NO_PLAYER;

    public TTTButton(Context context) {
        super(context);
    }
    public void setPlayer(int player)
    {
        this.player =player;
        if(player == MainActivity.PLAYER_O) {
            setText("0");
            setTextSize(40);
        }
        else if(player == MainActivity.PLAYER_X) {
            setText("X");
            setTextSize(40);
        }
        setEnabled(false);
    }

    public int  getPlayer()
    {
        return this.player;
    }
    public boolean isEmpty()
    {
        if(this.player == MainActivity.NO_PLAYER)
            return true;
        else
            return false;

    }


}
