package com.example.keetmalin.tictactoe_1;

import android.widget.Button;
import android.widget.ImageButton;

import java.util.Random;


public class SinglePlayerEasyGame extends Game  {
    public SinglePlayerEasyGame(String player){

        super(player,"Computer");
    }


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
            if(getPlayer1().getCount()+getPlayer2().getCount()<9){
                    Random rand = new Random();
                    int  n ;
                    while(true) {
                        n = rand.nextInt(9) + 1;
                        if (!this.getPlayer1().contains(n)&&!this.getPlayer2().contains(n) ) {
                            this.getPlayer1().add(n);
                            this.changePlayer();
                            buttons[n].setBackgroundResource(R.drawable.cross);
                            buttons[n].setEnabled(false);
                            if (this.getPlayer1().wins()) {
                                System.out.println("Winner " + getPlayer1().getName());
                                return true;
                            }else if(gameDrawn()){
                                System.out.println("Game Drawn");
                                return true;
                            }
                            break;
                        }
                    }
            }
        }
            //then the computer should play
        else {
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
                if(getPlayer1().getCount()+getPlayer2().getCount()<9){
                    Random rand = new Random();
                    int  n ;
                    while(true) {
                        n = rand.nextInt(9) + 1;
                        if (!this.getPlayer1().contains(n)&& !this.getPlayer2().contains(n)) {
                            this.getPlayer2().add(n);
                            this.changePlayer();
                            buttons[n].setBackgroundResource(R.drawable.zero);
                            buttons[n].setEnabled(false);
                            if (this.getPlayer2().wins()) {
                                System.out.println("Winner " + getPlayer2().getName());
                                return true;
                            }else if(gameDrawn()){
                                System.out.println("Game Drawn");
                                return true;
                            }
                            break;
                       }
                    }
                }
        }
        return false;
    }
}
