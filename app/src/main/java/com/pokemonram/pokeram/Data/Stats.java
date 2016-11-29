package com.pokemonram.pokeram.Data;

/**
 * Created by 100522340 & 100487239 on 11/28/2016.
 */

//The object stat that holds the national dec number of a specific pokemon and an integer of ever base stat within the game
public class Stats {
    public int speciesNum;
    public int hpStat;
    public int attackStat;
    public int defenseStat;
    public int specialAttackStat;
    public int specialDefenseStat;
    public int speedStat;

    public Stats(int speciesNum, int hpStat, int attackStat, int defenseStat, int specialAttackStat, int specialDefenseStat, int speedStat){
        this.speciesNum = speciesNum;
        this.hpStat = hpStat;
        this.attackStat = attackStat;
        this.defenseStat = defenseStat;
        this.specialAttackStat = specialAttackStat;
        this.specialDefenseStat = specialDefenseStat;
        this.speedStat = speedStat;
    }
}

