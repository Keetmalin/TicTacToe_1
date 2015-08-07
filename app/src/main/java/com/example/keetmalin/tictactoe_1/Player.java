package com.example.keetmalin.tictactoe_1;

import android.util.Log;

import java.util.LinkedList;

/**
 * Created by Pasindu Tennage on 7/17/2015.
 */
public class Player {
    private String name;
    private LinkedList<Integer> indexes;
    public Player(String name){
        indexes = new LinkedList<Integer>();
        this.name=name;
    }
    public static Player getClone(Player player){
        Player clonePlayer = new Player(player.getName());
        LinkedList<Integer> list = player.getList();
        for(int i : list){
            clonePlayer.add(i);
        }
        return clonePlayer;
    }
    public void remove(int n){
        if(indexes.contains(n)){
            indexes.remove(n);
        }

    }
    public boolean contains(int i){

        if(this.indexes.contains(i)){return true;}
        return false;
    }
    public LinkedList<Integer> getList(){
        return this.indexes;
    }
    public int getCount(){
        return this.indexes.size();
    }
    public void add(int i){
        this.indexes.add(i);
    }
    public boolean wins(){
        if(     (this.indexes.contains(1) && this.indexes.contains(2)&& this.indexes.contains(3) )||
                (this.indexes.contains(4) && this.indexes.contains(5)&& this.indexes.contains(6))||
                (this.indexes.contains(7) && this.indexes.contains(8)&& this.indexes.contains(9))||
                (this.indexes.contains(1) && this.indexes.contains(4)&& this.indexes.contains(7))||
                (this.indexes.contains(2) && this.indexes.contains(5)&& this.indexes.contains(8))||
                (this.indexes.contains(9) && this.indexes.contains(6)&& this.indexes.contains(3))||
                (this.indexes.contains(1) && this.indexes.contains(5)&& this.indexes.contains(9))||
                (this.indexes.contains(7) && this.indexes.contains(5)&& this.indexes.contains(3))){
            Log.d("Tag", this.getName() + " wins");

            return true;
        }
        return false;
    }
    public String getName(){
        return this.name;
    }
}