package com.example.aditi.game;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int PLAYER_X = 1;
    public static final int PLAYER_O = 0;
    public static final int NO_PLAYER = -1;

    public static final int INCOMPLETE=1;
    public static final int PLAYER_X_WON=2;
    public static final int PLAYER_O_WON=3;
    public static final int DRAW=4;
    public int currentStatus;

    LinearLayout rootLayout;
    private int m=3; //number of rows
    private  int n=3; //number of columns
    private ArrayList<LinearLayout> rows ;
    private TTTButton[][] board;
    public int currentPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootLayout =findViewById(R.id.linearLayout);

        setUpBoard();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //for showing menu
        getMenuInflater().inflate(R.menu.main_menu,menu);
        // Find the menuItem to add your SubMenu
        MenuItem myMenuItem = menu.findItem(R.id.sizeitem);

        // Inflating the sub_menu menu this way, will add its menu items
        // to the empty SubMenu you created in the xml
        getMenuInflater().inflate(R.menu.submenu, myMenuItem.getSubMenu());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //for handling items of menu
        int id =item.getItemId();
        if(id == R.id.resetItem){
            setUpBoard();
        }
        if(id == R.id.size3){
            m=3;
            n=3;
            setUpBoard();

        }
        if(id == R.id.size4){
            m=4;
            n=4;
            setUpBoard();
        }
        if(id == R.id.size5){
            m=5;
            n=5;
            setUpBoard();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUpBoard()
    {
        currentStatus=INCOMPLETE;
        currentPlayer =PLAYER_O;
        rows = new ArrayList<>();
        board =new TTTButton[m][n];
        rootLayout.removeAllViews();
        for(int i=0;i<m;i++)
        {

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL); //though by default it's horizontal
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);

            linearLayout.setLayoutParams(layoutParams);
            rows.add(linearLayout);
            rootLayout.addView(linearLayout);
        }

        for(int i=0;i<m;i++)
        {
            for(int j=0;j<n;j++)
            {
                TTTButton button = new TTTButton(this);
                LinearLayout.LayoutParams layoutParams =new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
                button.setLayoutParams(layoutParams);
                button.setOnClickListener(this);
                LinearLayout row = rows.get(i);
                row.addView(button); //add button in ith row
                board[i][j]= button;


            }
        }

    }



    @Override
    public void onClick(View view)
    {
        //if game is not complete yet
        if(currentStatus==INCOMPLETE) {
            TTTButton button = (TTTButton) view;
            button.setPlayer(currentPlayer);
            checkGameStatus();
            togglePlayer();
        }

    }
    public void checkGameStatus()
    {
        //for ROWS
        for(int i=0;i<m;i++)
        {
            //assume same player
            boolean rowSame=true;
            TTTButton firstButton =board[i][0]; //ith row and 0th column button
            for(int j=1;j<n;j++)
            {
                TTTButton button =board[i][j];
                if(button.isEmpty() || button.getPlayer() != firstButton.getPlayer())
                {
                    rowSame=false;
                    break;
                }
                }
            if(rowSame){
                int playerWon = firstButton.getPlayer();
                updateStatus(playerWon);
                return;
            }
        }

        //for COLUMNS
        for(int j=0;j<n;j++)
        {
            boolean colsame=true;
            TTTButton firstButton = board[0][j];
            for(int i=0;i<m;i++)
            {
                TTTButton button =board[i][j];
                if(button.isEmpty() || button.getPlayer() != firstButton.getPlayer())
                {
                    colsame=false;
                    break;
                }
            }
            if(colsame)
            {
                int playerWon = firstButton.getPlayer();
                updateStatus(playerWon);
                return;
            }
        }
        //for first DIAGNOL
        boolean diagSame =true;
        TTTButton firstButton = board[0][0];
        for(int i=0;i<m;i++)
        {

            TTTButton button =board[i][i];
            if(button.isEmpty() || button.getPlayer() != firstButton.getPlayer())
            {
                diagSame=false;
                break;
            }
        }
        if(diagSame)
        {
            int playerWon = firstButton.getPlayer();
            updateStatus(playerWon);
            return;
        }

        //for second DIAGNOL
        diagSame =true;
        firstButton = board[0][n-1];
        for(int i=0;i<m;i++)
        {

            TTTButton button =board[i][n-i-1];
            if(button.isEmpty() || button.getPlayer() != firstButton.getPlayer())
            {
                diagSame=false;
                break;
            }
        }
        if(diagSame)
        {
            int playerWon = firstButton.getPlayer();
            updateStatus(playerWon);
            return;
        }


        for(int i=0;i<m;i++)
            for(int j=0;j<n;j++)
            {
                TTTButton button =board[i][j];
                if(button.isEmpty())
                    return;
            }
        //DRAW when none of the button is empty

        currentStatus=DRAW;
        Toast.makeText(this, "GAME DRAW", Toast.LENGTH_SHORT).show();
    }


    public void togglePlayer()
    {
        if(currentPlayer == PLAYER_O)
            currentPlayer =PLAYER_X;
        else
            currentPlayer =PLAYER_O;
    }
    public void updateStatus(int playerWon)
    {
        if(playerWon== PLAYER_X) {
            currentStatus =PLAYER_X_WON;
            Toast.makeText(this, "PLAYER_X WON", Toast.LENGTH_SHORT).show();
        }
        if(playerWon== PLAYER_O) {
            currentStatus =PLAYER_O_WON;
            Toast.makeText(this, "PLAYER_O WON", Toast.LENGTH_SHORT).show();
        }
    }

}

