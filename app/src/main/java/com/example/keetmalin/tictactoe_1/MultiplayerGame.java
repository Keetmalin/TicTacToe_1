package com.example.keetmalin.tictactoe_1;

import android.widget.Button;

/**
 * Created by KeetMalin on 8/2/2015.
 */
public class MultiplayerGame extends Game{
    public static String playerOne = "";
    public static String playerTwo = "";

    public MultiplayerGame(String player1, String player2){
        super(player1,player2);
        playerOne = player1;
        playerTwo = player2;
    }
    public MultiplayerGame(){
        super("player1","player2");
        playerOne = "player 1";
        playerTwo = "player 2";
    }
    @Override
    public int play(Button button, int number, Button buttons[]) throws InterruptedException {

        if (this.getPlayer()) {
            button.setBackgroundResource(R.drawable.zero);
            button.setEnabled(false);
            this.changePlayer();
            //player==true => player2
            //player==false => player1
            this.getPlayer2().add(new Integer(number));
            if (this.getPlayer2().wins()) {
                System.out.println("Winner " + getPlayer2().getName());
                return 2;
            }else if(gameDrawn()){
                System.out.println("Game Drawn");
                return 0;
            }
        } else {
            this.changePlayer();
            button.setEnabled(false);
            button.setBackgroundResource(R.drawable.cross);
            this.getPlayer1().add(new Integer(number));
            if (this.getPlayer1().wins()) {
                System.out.println("Winner " + getPlayer1().getName());
                return 1;
            }else if(gameDrawn()){
                System.out.println("Game Drawn");
                return 0;
            }
            return 3;
        }
        return 3;

    }
}
