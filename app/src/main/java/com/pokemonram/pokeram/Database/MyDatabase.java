package com.pokemonram.pokeram.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pokemonram.pokeram.Data.Ability;
import com.pokemonram.pokeram.Data.Move;
import com.pokemonram.pokeram.Data.Pokemon;
import com.pokemonram.pokeram.Data.Stats;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by 100522340 & 100487239 on 11/28/2016.
 */

//SQLiteAssetHelper allowed for us to implement our databse within our code and have access to it without having 
//to create a whole new one within the program.
public class MyDatabase extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "pokedex.db";
    private static final int DATABASE_VERSION = 1;

    //this call will initiallize the database.
    public MyDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //This method inserts every pokemon within the database as a pokemon object.
    public ArrayList<Pokemon> getPokemon(){
        SQLiteDatabase pokedex = this.getReadableDatabase();
        Cursor find = pokedex.query(
                "pokemon",
                null,
                null,
                null,
                null,
                null,
                null);

        ArrayList<Pokemon> pokemonList = new ArrayList<Pokemon>();

        find.moveToFirst();

        while(!(find.isAfterLast())){
            int id = Integer.parseInt(find.getString(find.getColumnIndex("id")));

            if(id > 10000){
                break;
            }

            String name = find.getString(find.getColumnIndex("identifier"));
            int speciesNumber = find.getInt(find.getColumnIndex("species_id"));
            String speciesNum = String.valueOf(speciesNumber);

            Pokemon pkmn = new Pokemon(id, name, speciesNum);

            pokemonList.add(pkmn);
            find.moveToNext();
        }
        find.close();
        pokedex.close();
        return pokemonList;
    }

    //A seperate interpretation of our getPokemon method. This will store the pokemon as strings within a string array
    //rather than keeping them all as objects
    public String[] listPokemonNames(ArrayList<Pokemon> pokemon){
        String[] pokemonList = new String[721];

        for(int i = 0; i <= 720; i++){
            Pokemon p = pokemon.get(i);
            pokemonList[i] = p.name;
        }

        return pokemonList;
    }

    //this method will fetch all the base stats for each individual pokemon, storing them within a Stats object.
    public ArrayList<Stats> getPokemonStats(){
        SQLiteDatabase pokedex = this.getReadableDatabase();
        int hpStat = 0;
        int attackStat = 0;
        int defenseStat = 0;
        int specialAttackStat = 0;
        int specialDefenseStat = 0;
        int speedStat;

        String[] fields = {"pokemon_id", "stat_id", "base_stat"};
        Cursor find = pokedex.query(
                "pokemon_stats",
                fields,
                null,
                null,
                null,
                null,
                null);

        ArrayList<Stats> pokemonStats = new ArrayList<Stats>();

        find.moveToFirst();

        //We implemented several if statements in order to properly place each stat within the right variable.
        while(!(find.isAfterLast())){
            int speciesID = Integer.parseInt(find.getString(find.getColumnIndex("pokemon_id")));
            int stat = Integer.parseInt(find.getString(find.getColumnIndex("stat_id")));

            if(stat == 1){
                hpStat = Integer.parseInt(find.getString(find.getColumnIndex("base_stat")));
            }
            if(stat == 2){
                attackStat = Integer.parseInt(find.getString(find.getColumnIndex("base_stat")));
            }
            if(stat == 3){
                defenseStat = Integer.parseInt(find.getString(find.getColumnIndex("base_stat")));
            }
            if(stat == 4){
                specialAttackStat = Integer.parseInt(find.getString(find.getColumnIndex("base_stat")));
            }
            if(stat == 5){
                specialDefenseStat = Integer.parseInt(find.getString(find.getColumnIndex("base_stat")));
            }
            if(stat == 6){
                speedStat = Integer.parseInt(find.getString(find.getColumnIndex("base_stat")));
                Stats stats = new Stats(speciesID, hpStat, attackStat, defenseStat, specialAttackStat, specialDefenseStat, speedStat);
                pokemonStats.add(stats);
            }

            find.moveToNext();
        }
        find.close();
        pokedex.close();
        return pokemonStats;
    }

   //This method will find every ability that a pokemon can aquire and place it within a string array.
    public String[] getAbilities(){
        SQLiteDatabase pokedex = this.getReadableDatabase();
        String[] pokemonAbilities = new String[192];
        String[] search = {"id","identifier"};

        Cursor find2 = pokedex.query(
                "abilities",
                search,
                null,
                null,
                null,
                null,
                null);

        find2.moveToFirst();

        while(!(find2.isAfterLast())){
            int id = Integer.parseInt(find2.getString(find2.getColumnIndex("id")));
            if( id > 200){
                break;
            }
            int i = Integer.parseInt(find2.getString(find2.getColumnIndex("id")));
            String j = find2.getString(find2.getColumnIndex("identifier"));

            pokemonAbilities[i] = j;
            find2.moveToNext();
        }

        find2.close();
        pokedex.close();
        return pokemonAbilities;
    }

    //this gets all abilities within the database, and inserted them into an array.
    public ArrayList<Ability> listAbilities(String[] abilities){
        ArrayList<Ability> abilityList = new ArrayList<>();

        for(int i = 1; i < 192; i++){
            Ability a = new Ability(abilities[i]);

            abilityList.add((i-1),a);
        }

        return abilityList;
    }

    //the ArrayList variant. This kind of implementation allows us to do several interesting things
    public String[] findAbilities(String[] pokemonAbilities, int id){
        SQLiteDatabase pokedex = this.getReadableDatabase();
        String[] specificAbilities = new String[3];
        int[] abilityIDs = {0,0,0};

        String[] search = {"pokemon_id", "ability_id"};
        String[] argument = {Integer.toString(id+1)};

        int index = 0;

        Cursor findIDs = pokedex.query(
                "pokemon_abilities",
                search,
                "pokemon_id = ?",
                argument,
                null,
                null,
                null);

        findIDs.moveToFirst();

        while(!(findIDs.isAfterLast())){
            abilityIDs[index] = Integer.parseInt(findIDs.getString(findIDs.getColumnIndex("ability_id")));
            index++;
            findIDs.moveToNext();
        }

        specificAbilities[0] = pokemonAbilities[abilityIDs[0]];
        specificAbilities[1] = pokemonAbilities[abilityIDs[1]];
        specificAbilities[2] = pokemonAbilities[abilityIDs[2]];

        findIDs.close();
        pokedex.close();

        return specificAbilities;
    }

    //This method will pull all known pokemon types from the database and insert them into a string array.
    public String[] getTypes() {
        SQLiteDatabase pokedex = this.getReadableDatabase();
        String[] types = new String[19];
        String[] search = {"id", "identifier"};

        Cursor find2 = pokedex.query(
                "types",
                search,
                null,
                null,
                null,
                null,
                null);

        find2.moveToFirst();
        types[0] = "";

        while (!(find2.isAfterLast())) {
            int id = Integer.parseInt(find2.getString(find2.getColumnIndex("id")));
            if (id > 18) {
                break;
            }
            int i = Integer.parseInt(find2.getString(find2.getColumnIndex("id")));
            String j = find2.getString(find2.getColumnIndex("identifier"));

            types[(i)] = j;
            find2.moveToNext();
        }

        find2.close();
        pokedex.close();
        return types;
    }

    //This method will match up a pokemon with its respective type(s)
    public String[] findTyping(String[] types, int reference) {
        SQLiteDatabase pokedex = this.getReadableDatabase();
        String[] specificType = new String[2];
        int[] typeIDs = {0, 0};

        String[] search = {"pokemon_id", "type_id"};
        String[] argument = {Integer.toString(reference + 1)};

        int index = 0;

        Cursor findIDs = pokedex.query(
                "pokemon_types",
                search,
                "pokemon_id = ?",
                argument,
                null,
                null,
                null);

        findIDs.moveToFirst();

        while (!(findIDs.isAfterLast())) {
            typeIDs[index] = Integer.parseInt(findIDs.getString(findIDs.getColumnIndex("type_id")));
            index++;
            findIDs.moveToNext();
        }

        specificType[0] = types[typeIDs[0]];
        specificType[1] = types[typeIDs[1]];

        findIDs.close();
        pokedex.close();

        return specificType;
    }

    //This method will compile all known pokemon techniques and their respective type and store them within an array list
    public ArrayList<Move> getMoves() {
        SQLiteDatabase pokedex = this.getReadableDatabase();
        ArrayList<Move> movepool = new ArrayList<>();
        String[] search = {"id", "identifier", "type_id"};

        Cursor find2 = pokedex.query(
                "moves",
                search,
                null,
                null,
                null,
                null,
                null);

        find2.moveToFirst();

        while (!(find2.isAfterLast())) {
            int id = Integer.parseInt(find2.getString(find2.getColumnIndex("id")));
            if (id > 621) {
                break;
            }
            String j = find2.getString(find2.getColumnIndex("identifier"));
            int type = Integer.parseInt(find2.getString(find2.getColumnIndex("type_id")));
            String typeInsert = "";

            if (type == 1) {
                typeInsert = "normal";
            }
            if (type == 2) {
                typeInsert = "fighting";
            }
            if (type == 3) {
                typeInsert = "flying";
            }
            if (type == 4) {
                typeInsert = "poison";
            }
            if (type == 5) {
                typeInsert = "ground";
            }
            if (type == 6) {
                typeInsert = "rock";
            }
            if (type == 7) {
                typeInsert = "bug";
            }
            if (type == 8) {
                typeInsert = "ghost";
            }
            if (type == 9) {
                typeInsert = "steel";
            }
            if (type == 10) {
                typeInsert = "fire";
            }
            if (type == 11) {
                typeInsert = "water";
            }
            if (type == 12) {
                typeInsert = "grass";
            }
            if (type == 13) {
                typeInsert = "electric";
            }
            if (type == 14) {
                typeInsert = "psychic";
            }
            if (type == 15) {
                typeInsert = "ice";
            }
            if (type == 16) {
                typeInsert = "dragon";
            }
            if (type == 17) {
                typeInsert = "dark";
            }
            if (type == 18) {
                typeInsert = "fairy";
            }

            Move move = new Move(j, typeInsert);
            movepool.add(move);
            find2.moveToNext();
        }

        find2.close();
        pokedex.close();
        return movepool;
    }

    //this method is a variant of the method before. it gives us more freedom with what we can do within the app
    public String[] getMoveList(ArrayList<Move> moves){
        String[] moveList = new String[621];

        for(int i = 0; i < 621; i++){
            Move m = moves.get(i);
            moveList[i] = m.moveName;
        }

        return moveList;
    }

    //This method will match a specific pokemon with its unique move-set 
    public ArrayList<Move> getMoveSet(ArrayList<Move> movepool, int reference){
        SQLiteDatabase pokedex = this.getReadableDatabase();
        ArrayList<Move> moveset = new ArrayList<>();
        String[] search = {"pokemon_id", "version_group_id" ,"move_id"};
        String[] argument = {Integer.toString(reference)};

        Cursor findMoves = pokedex.query(
                "pokemon_moves",
                search,
                "pokemon_id = ? AND version_group_id = 16",
                argument,
                null,
                null,
                null,
                null);

        findMoves.moveToFirst();

        while(!(findMoves.isAfterLast())){
            int moveID = Integer.parseInt(findMoves.getString(findMoves.getColumnIndex("move_id")));
            if(moveset.contains(movepool.get((moveID-1)))){
                findMoves.moveToNext();
            }
            else {
                moveset.add(movepool.get((moveID - 1)));
                findMoves.moveToNext();
            }
        }

        findMoves.close();
        pokedex.close();
        return moveset;
    }

}
