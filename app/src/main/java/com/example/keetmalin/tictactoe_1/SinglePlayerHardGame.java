package com.example.keetmalin.tictactoe_1;

import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SinglePlayerHardGame extends Game {
    public static String playerOne = "";
    public static String playerTwo = "";

    public SinglePlayerHardGame(String player) {
        super(player, "Computer");
        playerOne = player;
        playerTwo = "Computer";
    }
    public SinglePlayerHardGame(){
        super("player","Computer");
        playerOne = "player";
        playerTwo = "Computer";
    }
    @Override
    public int play(Button imageButton,int number,Button buttons[]) throws InterruptedException {
        if (this.getPlayer()) {
            imageButton.setBackgroundResource(R.drawable.zero);
            imageButton.setEnabled(false);
            this.changePlayer();
            //player==true => player2
            //player==false => player1
            this.getPlayer2().add(new Integer(number));
            if (this.getPlayer2().wins()) {
                return 2;
            }else if(gameDrawn()){
                return 0;
            }
            if(getPlayer1().getCount()+getPlayer2().getCount()<9){}{
                Random rand = new Random();
                int  n ;
                boolean found = false;
                List<Integer> availableSlots = new ArrayList<Integer>();
                for(int i = 1 ;i<10;i++){
                    if(!this.getPlayer1().contains(i)&&!this.getPlayer2().contains(i)){
                        availableSlots.add(i);
                    }
                }
                for(int i : availableSlots){

                    Player tempPlayer = Player.getClone(getPlayer1());
                    tempPlayer.add(i);
                    if (tempPlayer.wins()) {
                        this.getPlayer1().add(i);
                        this.changePlayer();
                        buttons[i].setBackgroundResource(R.drawable.cross);
                        buttons[i].setEnabled(false);
                        found = true;
                        return 1;
                    }

                }

                int option = otherPlayerWin(getPlayer2(),availableSlots);
                if(option!=0){
                    this.getPlayer1().add(option);
                    this.changePlayer();
                    buttons[option].setBackgroundResource(R.drawable.cross);
                    buttons[option].setEnabled(false);
                    if (this.getPlayer1().wins()) {
                        return 1;
                    }else if(gameDrawn()){
                        return 0;
                    }
                    found = true;
                }

                while(!found) {
                    n = rand.nextInt(9) + 1;
                    if (!this.getPlayer1().contains(n)&&!this.getPlayer2().contains(n) ) {
                        this.getPlayer1().add(n);
                        this.changePlayer();
                        buttons[n].setBackgroundResource(R.drawable.cross);
                        buttons[n].setEnabled(false);
                        if (this.getPlayer1().wins()) {
                            return 1;
                        }else if(gameDrawn()){
                            return 0;
                        }
                        found = true;
                    }
                }}
        }
        //then the computer should play
        else {
            imageButton.setBackgroundResource(R.drawable.cross);
            imageButton.setEnabled(false);
            this.changePlayer();
            //player==true => player2
            //player==false => player1
            this.getPlayer1().add(new Integer(number));
            if (this.getPlayer1().wins()) {
                return 1;
            }else if(gameDrawn()){
                return 0;
            }

            if(getPlayer1().getCount()+getPlayer2().getCount()<9){}{
                Random rand = new Random();
                int  n ;
                boolean found = false;
                List<Integer> availableSlots = new ArrayList<Integer>();
                for(int i = 1 ;i<10;i++){
                    if(!this.getPlayer1().contains(i)&&!this.getPlayer2().contains(i)){
                        availableSlots.add(i);
                    }
                }
                for(int i : availableSlots){

                    Player tempPlayer = Player.getClone(getPlayer2());
                    tempPlayer.add(i);
                    if (tempPlayer.wins()) {
                        this.getPlayer2().add(i);
                        this.changePlayer();
                        buttons[i].setBackgroundResource(R.drawable.zero);
                        buttons[i].setEnabled(false);
                        found = true;
                        return 2;
                    }

                }

                int option = otherPlayerWin(getPlayer1(),availableSlots);
                if(option!=0){
                    this.getPlayer2().add(option);
                    this.changePlayer();
                    buttons[option].setBackgroundResource(R.drawable.zero);
                    buttons[option].setEnabled(false);
                    if (this.getPlayer2().wins()) {
                        return 2;
                    }else if(gameDrawn()){
                        return 0;
                    }
                    found = true;
                }

                while(!found) {
                    n = rand.nextInt(9) + 1;
                    if (!this.getPlayer1().contains(n)&&!this.getPlayer2().contains(n) ) {
                        this.getPlayer2().add(n);
                        this.changePlayer();
                        buttons[n].setBackgroundResource(R.drawable.zero);
                        buttons[n].setEnabled(false);
                        if (this.getPlayer2().wins()) {
                            return 2;
                        }else if(gameDrawn()){
                            return 0;
                        }
                        found = true;
                    }
                }}
        }
        return 3;
    }
    private int otherPlayerWin(Player player,List<Integer> availableSlots){ //
        if( player.contains(1)&&player.contains(2)&&availableSlots.contains(3)){return  3 ;}
        else if (player.contains(1)&&player.contains(3)&&availableSlots.contains(2)){return 2  ;}
        else if (player.contains(2)&&player.contains(3)&&availableSlots.contains(1)){return  1 ;}

        else if (player.contains(4)&&player.contains(5)&&availableSlots.contains(6)){return  6 ;}
        else if (player.contains(4)&&player.contains(6)&&availableSlots.contains(5)){return 5  ;}
        else if (player.contains(5)&&player.contains(6)&&availableSlots.contains(4)){return 4  ;}

        else if (player.contains(7)&&player.contains(8)&&availableSlots.contains(9)){return  9 ;}
        else if (player.contains(7)&&player.contains(9)&&availableSlots.contains(8)){return  8 ;}
        else if (player.contains(8)&&player.contains(9)&&availableSlots.contains(7)){return  7 ;}


        else if (player.contains(1)&&player.contains(4)&&availableSlots.contains(7)){return 7  ;}
        else if (player.contains(1)&&player.contains(7)&&availableSlots.contains(4)){return  4 ;}
        else if (player.contains(4)&&player.contains(7)&&availableSlots.contains(1)){return  1 ;}

        else if (player.contains(2)&&player.contains(5)&&availableSlots.contains(8)){return 8  ;}
        else if (player.contains(2)&&player.contains(8)&&availableSlots.contains(5)){return  5 ;}
        else if (player.contains(5)&&player.contains(8)&&availableSlots.contains(2)){return 2  ;}

        else if (player.contains(3)&&player.contains(6)&&availableSlots.contains(9)){return  9 ;}
        else if (player.contains(3)&&player.contains(9)&&availableSlots.contains(6)){return  6 ;}
        else if (player.contains(6)&&player.contains(9)&&availableSlots.contains(3)){return  3 ;}

        else if (player.contains(1)&&player.contains(5)&&availableSlots.contains(9)){return  9 ;}
        else if (player.contains(1)&&player.contains(9)&&availableSlots.contains(5)){return  5 ;}
        else if (player.contains(5)&&player.contains(9)&&availableSlots.contains(1)){return  1 ;}

        else if (player.contains(3)&&player.contains(5)&&availableSlots.contains(7)){return  7 ;}
        else if (player.contains(3)&&player.contains(7)&&availableSlots.contains(5)){return  5 ;}
        else if (player.contains(5)&&player.contains(7)&&availableSlots.contains(3)){return  3 ;}

        else{return 0;}



    }
}
