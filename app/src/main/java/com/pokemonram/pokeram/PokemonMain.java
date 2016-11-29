package com.pokemonram.pokeram;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//The main menu of our application
public class PokemonMain extends AppCompatActivity {

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_main);

        Button pokedex = (Button)this.findViewById(R.id.button_pokedex_main);
        Button moves = (Button)this.findViewById(R.id.button_moves_main);
        Button abilities = (Button)this.findViewById(R.id.button_abilities_main);

        //This allows for the buttons to create sound when pushed
        mp = MediaPlayer.create(this,R.raw.rotom);
    }

    //Starts the Pokedex Activity
    public void PokedexClick(View v){
        mp.start();
        Intent i = new Intent(this,PokedexActivity.class);
        startActivity(i);
    }

    //Starts the Moves Activity
    public void MovesClick(View v){
        mp.start();
        Intent i = new Intent(this,MovesActivity.class);
        startActivity(i);
    }

    //Starts the Abilities Activity
    public void AbilitiesClick(View v) {
        mp.start();
        Intent i = new Intent(this,AbilitiesActivity.class);
        startActivity(i);
    }



}
