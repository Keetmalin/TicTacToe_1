package com.example.keetmalin.tictactoe_1;

import android.widget.Button;

import java.util.LinkedList;
import java.util.Random;


public class SinglePlayerMediumGame extends Game {
    public static String playerOne = "";
    public static String playerTwo = "";

    public SinglePlayerMediumGame(String player){

        super(player,"Computer");
        playerOne = player;
        playerTwo = "Computer";
    }
    public SinglePlayerMediumGame(){
        super("player","Computer");
        playerOne = "player";
        playerTwo = "Computer";
    }
    @Override
    public int play(Button imageButton, int number, Button buttons[]) throws InterruptedException {
        if (this.getPlayer()) {
            imageButton.setBackgroundResource(R.drawable.zero);
            imageButton.setEnabled(false);
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
            if(getPlayer1().getCount()+getPlayer2().getCount()<9){}{
                Random rand = new Random();
                int  n ;
                boolean found = false;
                for(int i = 1 ;i<10;i++){
                    if (!this.getPlayer1().contains(i)&&!this.getPlayer2().contains(i) ) {
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
                }
                /*for(int i = 1 ;i<10;i++){
                    if (!this.getPlayer1().contains(i)&&!this.getPlayer2().contains(i) ) {
                        Player tempPlayer = new Player("Temp Player");
                        LinkedList<Integer> tempList = this.getPlayer1().getList();
                        for(int p : tempList){
                            tempPlayer.add(p);
                        }
                        tempPlayer.add(i);
                        if (tempPlayer.wins()) {
                            this.getPlayer1().add(i);
                            this.changePlayer();
                            buttons[i].setBackgroundResource(R.drawable.cross);
                            buttons[i].setEnabled(false);

                            System.out.println("Winner " + getPlayer1().getName());
                            found = true;
                            return 1;
                            //break;
                        }
                        break;
                    }
                }*/
                while(!found) {
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
                System.out.println("Winner " + getPlayer1().getName());
                return 1;
            }else if(gameDrawn()){
                System.out.println("Game Drawn");
                return 0;
            }
            if(getPlayer1().getCount()+getPlayer2().getCount()<9){}{Random rand = new Random();
                int  n ;
                boolean found = false;
                /*for(int i = 1 ;i<10;i++){
                    if (!this.getPlayer1().contains(i)&&!this.getPlayer2().contains(i) ) {
                        Player tempPlayer = new Player("Temp Player");
                        LinkedList<Integer> tempList = this.getPlayer2().getList();
                        for(int p : tempList){
                            tempPlayer.add(p);
                        }
                        tempPlayer.add(i);
                        if (tempPlayer.wins()) {
                            this.getPlayer2().add(i);
                            this.changePlayer();
                            buttons[i].setBackgroundResource(R.drawable.zero);
                            buttons[i].setEnabled(false);
                            System.out.println("Winner " + getPlayer2().getName());
                            found = true;
                            return 2;
                            //break;
                        }
                        break;
                    }
                }*/
                for(int i = 1 ;i<10;i++){
                    if (!this.getPlayer1().contains(i)&&!this.getPlayer2().contains(i) ) {
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
                }
                while(!found) {
                    n = rand.nextInt(9) + 1;
                    if (!this.getPlayer1().contains(n)&&!this.getPlayer2().contains(n) ) {
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
                        found = true;
                    }
                }}
        }
        return 3;
    }
}
