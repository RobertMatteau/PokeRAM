package com.pokemonram.pokeram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pokemonram.pokeram.Data.Pokemon;
import com.pokemonram.pokeram.Database.MyDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;

//The activity that acesses the pokedex
public class PokedexActivity extends AppCompatActivity {

    private MyDatabase pokedex;
    ArrayAdapter<String> adapterpokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);
    }

    protected void onStart(){
        super.onStart();

        listViewSetup();//sets up the list at the start of the activity
    }

    //The method that creates the list
    public void listViewSetup(){
        pokedex = new MyDatabase(this);

        //gets the list opf pokemon
        ArrayList<Pokemon> pokemonList = pokedex.getPokemon();
        String[] pokemon = pokedex.listPokemonNames(pokemonList);

        //makes the pokemon and the pokedex number 
        ListView pokemonListView=(ListView)findViewById(R.id.listview_pokedex);
        EditText inputsearchpokemon = (EditText)findViewById(R.id.edittext_pkmnname);

        //
        String[] pokemonsearch = new String[721];
        for(int i=0; i < 721; i++){
            Pokemon a = pokemonList.get(i);
            pokemonsearch[i] = (a.name).toUpperCase();
        }
        
        //The adapter to load each pokemon into the list
        adapterpokemon = new ArrayAdapter<String>(this,R.layout.pokemon_listrow,R.id.textview_pokemonlistrow,pokemonsearch);

        pokemonListView.setAdapter(adapterpokemon);

        //allows each item in the list to be clickable
        pokemonListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(view.getContext(), DetailedPokemonActivity.class);

                long dexNum = id+1;
                intent.putExtra("dexNum", dexNum);

                //starts the pokemon viewing activity
                startActivity(intent);
            }
        });

        inputsearchpokemon.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                PokedexActivity.this.adapterpokemon.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        pokedex.close();
    }

}

//The adapter that allows for our pokemon to be inserted into the list
class PokedexAdapter extends ArrayAdapter<Pokemon>{

    private Context context;

    public PokedexAdapter(Context context, ArrayList<Pokemon> pokemon){
        super(context, 0, pokemon);
    }

    //Gives the list item the pokemon name 
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Pokemon pokemon = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pokemon_listrow, parent, false);
        }

        ((TextView)convertView.findViewById(R.id.textview_pokemonlistrow)).setText(pokemon.name.toUpperCase());
        
        return convertView;
    }

}
