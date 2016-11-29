package com.pokemonram.pokeram.Data;

/**
 * Created by 100522340 on 11/25/2016.
 */

import android.content.Context;

import java.util.List;

public class Pokemon{
    public int id;
    public String name;
    public String speciesNum;
    public int spriteId;

    public Pokemon(int id, String name, String speciesNum){
        this.id = id;
        this.name = name;
        this.speciesNum = speciesNum;
    }
    public int getSpriteId() {
        return spriteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}