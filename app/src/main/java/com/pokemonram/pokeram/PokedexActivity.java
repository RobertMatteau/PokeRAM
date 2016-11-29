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

        listViewSetup();
    }

    public void listViewSetup(){
        pokedex = new MyDatabase(this);

        ArrayList<Pokemon> pokemonList = pokedex.getPokemon();
        String[] pokemon = pokedex.listPokemonNames(pokemonList);



        ListView pokemonListView=(ListView)findViewById(R.id.listview_pokedex);
        EditText inputsearchpokemon = (EditText)findViewById(R.id.edittext_pkmnname);


        String[] pokemonsearch = new String[721];
        for(int i=0; i < 721; i++){
            Pokemon a = pokemonList.get(i);
            pokemonsearch[i] = (a.name).toUpperCase();
        }




        //String[] abilitysearch = pokedex.getAbilities();
        adapterpokemon = new ArrayAdapter<String>(this,R.layout.pokemon_listrow,R.id.textview_pokemonlistrow,pokemonsearch);



        pokemonListView.setAdapter(adapterpokemon);


        pokemonListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(view.getContext(), DetailedPokemonActivity.class);

                long dexNum = id+1;
                intent.putExtra("dexNum", dexNum);

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

class PokedexAdapter extends ArrayAdapter<Pokemon>{

    private Context context;

    public PokedexAdapter(Context context, ArrayList<Pokemon> pokemon){
        super(context, 0, pokemon);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Pokemon pokemon = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pokemon_listrow, parent, false);
        }

        ((TextView)convertView.findViewById(R.id.textview_pokemonlistrow)).setText(pokemon.name.toUpperCase());
        //((TextView)convertView.findViewById(R.id.textview_pokemonidlistrow)).setText(pokemon.speciesNum);

        //((ImageView));
        //((ImageView));

        return convertView;
    }

}
