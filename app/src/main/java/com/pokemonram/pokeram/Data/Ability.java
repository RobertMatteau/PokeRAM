package com.pokemonram.pokeram.Data;

/**
 * Created by 100522340 on 11/25/2016.
 */

public class Ability {

    private String name;
    private String description;

    public Ability(String name, String description) {

        this.name = name;
        this.description = description;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
