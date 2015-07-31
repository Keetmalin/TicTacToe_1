package com.example.keetmalin.tictactoe_1;

import android.widget.Button;


public class MultiplayerGame extends Game {

    public MultiplayerGame(String player1, String player2){
        super(player1,player2);
    }
    @Override
    public boolean play(Button button,int number,Button buttons[]) throws InterruptedException {
        if (this.getPlayer()) {
            button.setBackgroundResource(R.drawable.zero);
            button.setEnabled(false);
            this.changePlayer();
            //player==true => player2
            //player==false => player1
            this.getPlayer2().add(new Integer(number));
            if (this.getPlayer2().wins()) {
                System.out.println("Winner " + getPlayer2().getName());
                return true;
            }else if(gameDrawn()){
                System.out.println("Game Drawn");
                return true;
            }
        } else {
            this.changePlayer();
            button.setEnabled(false);
            button.setBackgroundResource(R.drawable.cross);
            this.getPlayer1().add(new Integer(number));
            if (this.getPlayer1().wins()) {
                System.out.println("Winner " + getPlayer1().getName());
                return true;
            }else if(gameDrawn()){
                System.out.println("Game Drawn");
                return true;
            }
            return false;
        }
        return false;

    }
}
