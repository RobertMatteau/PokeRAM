package com.pokemonram.pokeram;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.pokemonram.pokeram.Data.Ability;
import com.pokemonram.pokeram.Data.Move;
import com.pokemonram.pokeram.Database.MyDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AbilitiesActivity extends AppCompatActivity {

    private MyDatabase pokedex;
    ArrayAdapter<String> adapterability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abilities);
    }

    //when the app starts up
    protected void onStart(){
        super.onStart();

        listViewSetup();
    }

    //array list to sort the list view
    public static ArrayList<Ability> sortAbility(ArrayList<Ability> ability) {
        ArrayList<Ability> sortedAbility = ability;

        Collections.sort(sortedAbility, new Comparator<Ability>() {
            public int compare(Ability m1, Ability m2) {
                return m1.getAbilities().compareToIgnoreCase(m2.getAbilities());

            }
        });

        return sortedAbility;
    }


    //method to set up list view
    public void listViewSetup(){
        pokedex = new MyDatabase(this);
        String[] abilities = pokedex.getAbilities();

        ArrayList<Ability> abilityList = pokedex.listAbilities(abilities);
        ArrayList<Ability> sortedabilityList = sortAbility(abilityList);

        ListView abilitiesListView=(ListView)findViewById(R.id.abilityList);
        EditText inputsearch = (EditText)findViewById(R.id.edittext_abilitiesallname);


        //changed arraylist to array
        String[] abilitysearch = new String[191];
        for(int i=0; i < 191; i++){
            Ability a = sortedabilityList.get(i);
            abilitysearch[i] = a.abilities.toUpperCase();
        }


        //String[] abilitysearch = pokedex.getAbilities();
        adapterability = new ArrayAdapter<String>(this,R.layout.ability_listrow,R.id.nameability,abilitysearch);




        AbilityAdapter pkmnAdd = new AbilityAdapter(
                this,
                sortedabilityList
        );

        //set the adapter
        //abilitiesListView.setAdapter(pkmnAdd);
        abilitiesListView.setAdapter(adapterability);

        //set the on click listener
        abilitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //Intent intent = new Intent(view.getContext(), DetailedPokemonActivity.class);

                //long dexNum = id+1;
                //intent.putExtra("dexNum", dexNum);

                //startActivity(intent);
            }
        });


        //method to allow for auto searching
        inputsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                AbilitiesActivity.this.adapterability.getFilter().filter(cs);
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



    //method for when the activity closes
    @Override
    protected void onDestroy(){
        super.onDestroy();
        pokedex.close();
    }

}

//adapter class for getting the info to fill the list view rows
class AbilityAdapter extends ArrayAdapter<Ability> {

    private Context context;

    public AbilityAdapter(Context context, ArrayList<Ability> ability){
        super(context, 0, ability);
        this.context = context;
    }

    //
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Ability ability = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ability_listrow, parent, false);
        }


        //set the text view
        ((TextView)convertView.findViewById(R.id.nameability)).setText(ability.abilities.toUpperCase());

        //((ImageView));
        //((ImageView));

        return convertView;
    }

}

