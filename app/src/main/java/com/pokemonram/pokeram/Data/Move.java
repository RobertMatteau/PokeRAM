package com.pokemonram.pokeram.Data;

/**
 * Created by 100522340 & 100487239 on 11/25/2016.
 */

//The object Move
//this object will hold the name of a technique and the typing of that move, both as a string
public class Move {
    public String moveName;
    public String type;



    public Move(String moveName, String type){
        this.moveName = moveName;
        this.type = type;
    }

    public String getMoveName() {
        return moveName;
    }

    public void setMoveName(String moveName) {
        this.moveName = moveName;
    }
}
