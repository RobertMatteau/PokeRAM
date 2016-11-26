package com.pokemonram.pokeram.Data;

/**
 * Created by 100522340 on 11/25/2016.
 */

import android.content.Context;

import java.util.List;

public class Pokemon {

    public List<Ability> abilities;
    private int pokemonUniqueID;
    private int pokemonNationalID;
    private String name;

    private int attack;
    private int defence;
    private int hp;
    private int spAttack;
    private int spDefence;
    private int speed;

    private List<Type> types;


    public Pokemon(List<Ability> abilities, int pokemonUniqueID, int pokemonNationalID, String name,
                   int attack, int defence, int hp, int spAttack, int spDefence, int speed, List<Type> types) {

        this.pokemonUniqueID = pokemonUniqueID;
        this.pokemonNationalID = pokemonNationalID;
        this.name = name;
        this.attack = attack;
        this.defence = defence;
        this.hp = hp;
        this.spAttack = spAttack;
        this.spDefence = spDefence;
        this.speed = speed;
        this.types = types;
        this.abilities = abilities;



    }





}
