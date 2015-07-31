package com.example.keetmalin.tictactoe_1;

import android.widget.Button;
import android.widget.ImageButton;


public class SinglePlayerHardGame extends Game  {
    public SinglePlayerHardGame(String player){

        super(player,"Computer");
    }

    @Override
    public boolean play(Button button,int number,Button buttons[]) {
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
            }
            else if(gameDrawn()){
                System.out.println("Game Drawn");
                return true;
            }
            if(getPlayer1().getCount()+getPlayer2().getCount()<9) {

                            int n = getBestPosition(this.getPlayer1().getClone(this.getPlayer1()), this.getPlayer2().getClone(this.getPlayer2()));
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

            if(getPlayer1().getCount()+getPlayer2().getCount()<9) {

                int n = getBestPosition(this.getPlayer2().getClone(this.getPlayer2()), this.getPlayer1().getClone(this.getPlayer1()));
                this.getPlayer2().add(n);
                this.changePlayer();
                buttons[n].setBackgroundResource(R.drawable.zero);
                buttons[n].setEnabled(false);
                if (this.getPlayer2().wins()) {
                    System.out.println("Winner " + getPlayer1().getName());
                    return true;
                }else if(gameDrawn()){
                    System.out.println("Game Drawn");
                    return true;
                }
            }
        }
        return false;
    }

    public int getBestPosition(Player player1,Player player2){
        int array[] = new int[10];
        array[0] = -1;

        for(int i = 1 ;i<10;i++){
            array[i] = calculateArray(player1,player2,i);
        }
        int max = 0;
        int maxIndex = 0;
        for(int i = 1 ;i<10;i++){
            if(array[i]>max){
                max = array[i];
                maxIndex = i;
            }
        }return max;
    }
    public int calculateArray(Player player1,Player player2,int i){

            if (!player1.contains(i)&&!player2.contains(i) ) {
                player1.add(i);
                if(player1.wins()){return  10;}
                else if(gameDrawn()){return 0;}
                else{

                }
            }else{
                return -1;
            }


    return 0;
    }
}
