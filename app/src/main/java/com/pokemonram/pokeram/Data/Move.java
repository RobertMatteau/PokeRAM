package com.pokemonram.pokeram.Data;

/**
 * Created by 100522340 on 11/25/2016.
 */

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
