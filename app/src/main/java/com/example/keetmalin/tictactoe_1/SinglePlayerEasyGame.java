package com.example.keetmalin.tictactoe_1;

import android.widget.Button;

import java.util.Random;


public class SinglePlayerEasyGame extends Game  {
    //public static String playerOne = "";
    //public static String playerTwo = "";

    public SinglePlayerEasyGame(String player){
        //playerOne = player;
        super(player,"Computer");
        //playerOne = player;
        //playerTwo = "Computer";
    }
    public SinglePlayerEasyGame(){
        super("player","Computer");
        //playerOne = "player";
        //playerTwo = "Computer";
    }


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
                                return 1;
                            }else if(gameDrawn()){
                                System.out.println("Game Drawn");
                                return 0;
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
                return 1;
            }else if(gameDrawn()){
                System.out.println("Game Drawn");
                return 0;
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
                                return 2;
                            }else if(gameDrawn()){
                                System.out.println("Game Drawn");
                                return 0;
                            }
                            break;
                       }
                    }
                }
        }
        return 3;
    }
}
