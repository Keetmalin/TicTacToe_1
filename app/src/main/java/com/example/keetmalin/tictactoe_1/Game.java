package com.example.keetmalin.tictactoe_1;

import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Pasindu Tennage on 7/17/2015.
 */
public abstract class Game {
    //public static String playerOne = "";
    //public static String playerTwo = "";
    private Player player1;
    private Player player2;
    private boolean player; // true : player2 false : player 1
    public Game(String player1, String player2){
        //playerOne = player1;
        //playerTwo=player2;
        this.player1 = new Player(player1);
        this.player2 = new Player(player2);
        player = false;

    }
    public boolean getPlayer(){
        return this.player;
    }
    public boolean gameDrawn(){
        for(int i = 1 ;i<10;i++){
            if(!(this.getPlayer1().contains(i)||this.getPlayer2().contains(i))){
                return false;
            }
        }return true;
    }
    public void changePlayer(){
        player=!player;
    }

    public Player getPlayer1(){return player1;};
    public Player getPlayer2(){return player2;};
    public abstract int play(Button button, int number, Button buttons[]) throws InterruptedException;
}
