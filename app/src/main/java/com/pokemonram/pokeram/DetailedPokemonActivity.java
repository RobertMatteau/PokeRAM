package com.pokemonram.pokeram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pokemonram.pokeram.Data.Pokemon;
import com.pokemonram.pokeram.Data.Stats;
import com.pokemonram.pokeram.Database.MyDatabase;

import java.io.InputStream;
import java.util.ArrayList;

public class DetailedPokemonActivity extends AppCompatActivity {


   private MyDatabase pokedex;

   //set up the on create functions
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_pokemon);

        pokedex = new MyDatabase(this);

       //read from the database
        pokedex.getReadableDatabase();
        ArrayList<Pokemon> display = pokedex.getPokemon();

       //intent set up
        Intent get = getIntent();
        long pokedexNumber = get.getLongExtra("dexNum", 0);
        int reference = ((int) pokedexNumber)-1;
        String pn = Long.toString(pokedexNumber);

        TextView numText = (TextView) findViewById(R.id.pokedex_number);

        numText.setText(pn);

        Pokemon pkmnView = display.get((reference));

       //set text
        String name = pkmnView.name;
        TextView nameText = (TextView) findViewById(R.id.pokemon_name);
        nameText.setText(name);

        String[] test = pokedex.getAbilities();

    }

   //metod for when the app starts up
    public void onStart(){
        super.onStart();

        pokedex.getReadableDatabase();
        ArrayList<Pokemon> display = pokedex.getPokemon();

        Intent get = getIntent();
        long pokedexNumber = get.getLongExtra("dexNum", 0);
        int reference = ((int) pokedexNumber)-1;
        String pn = Long.toString(pokedexNumber);

        TextView numText = (TextView) findViewById(R.id.pokedex_number);

        numText.setText(pn);
      //get the id for the pokemon
        Pokemon pkmnView = display.get((reference));

        String namepokemon = pkmnView.name;
        TextView nameText = (TextView) findViewById(R.id.pokemon_name);
        nameText.setText(namepokemon);

        //String[] test = pokedex.getAbilities();

        getStats(reference);
        getAbilities(reference);
        getTyping(reference);


        pokedex.getReadableDatabase();

        ArrayList<Pokemon> pokemon = pokedex.getPokemon();

       //pokemon names for internet resource images
        Pokemon pkmn = pokemon.get(reference-1);
        String name = pkmn.name;

        if(name.equals("deoxys")){
            name = "deoxys-normal";
        }
        if(name.equals("burmy")){
            name = "burmy-normal";
        }
        if(name.equals("wormadam")){
            name = "wormadam-plant";
        }
        if(name.equals("combee")){
            name = "combee-f";
        }
        if(name.equals("cherrim")){
            name = "cherrim-sunshine";
        }
        if(name.equals("shellos")){
            name = "shellos-west";
        }
        if(name.equals("gastrodon")){
            name = "gastrodon-west";
        }
        if(name.equals("hipopotas")){
            name = "hipopotas-f";
        }
        if(name.equals("hippowdon")){
            name = "hippowdon-f";
        }
        if(name.equals("rotom")){
            name = "rotom-normal";
        }
        if(name.equals("giratina")){
            name = "giratina-origin";
        }
        if(name.equals("shaymin")){
            name = "shaymin-land";
        }
        if(name.equals("arceus")){
            name = "arceus-normal";
        }
        if(name.equals("basculin")){
            name = "basculin-red";
        }
        if(name.equals("darmanitan")){
            name = "darmanitan-standard";
        }
        if(name.equals("deerling")){
            name = "deerling-spring";
        }
        if(name.equals("sawsbuck")){
            name = "sawsbuck-spring";
        }
        if(name.equals("frillish")){
            name = "frillish-f";
        }
        if(name.equals("jellicent")){
            name = "jellicent-f";
        }
        if(name.equals("tornadus")){
            name = "tornadus-incarnate";
        }
        if(name.equals("thundurus")){
            name = "thundurus-incarnate";
        }
        if(name.equals("landorous")){
            name = "landorous-incarnate";
        }
        if(name.equals("kyurem")){
            name = "kyurem-normal";
        }
        if(name.equals("keldeo")){
            name = "keldeo-ordinary";
        }
        if(name.equals("meloetta")){
            name = "meloetta-aria";
        }
        if(name.equals("vivillon")){
            name = "vivillon-meadow";
        }
        if(name.equals("flabebe")){
            name = "flabebe-red";
        }
        if(name.equals("floette")){
            name = "floette-red";
        }
        if(name.equals("florges")){
            name = "florges-red";
        }
        if(name.equals("furfrou")){
            name = "furfrou-natural";
        }
        if(name.equals("meowstic")){
            name = "meowstic-female";
        }
        if(name.equals("aegislash")){
            name = "aegislash-blade";
        }
        if(name.equals("pumpkaboo")){
            name = "pumpkaboo-super";
        }
        if(name.equals("gourgeist")){
            name = "gourgeist-super";
        }
        if(name.equals("xerneas")){
            name = "xerneas-active";
        }
        if(name.equals("hoopa")){
            name = "hoopa-confined";
        }

        String url = "https://img.pokemondb.net/sprites/x-y/normal/" + name + ".png";


        new DownloadImageTask((ImageView) findViewById(R.id.testImage)).execute(url);
        //move to DownloadImageTask

    }

   //get the stats of the pokemon
    public void getStats(int reference){
        pokedex = new MyDatabase(this);
        ArrayList<Stats> statsArray = pokedex.getPokemonStats();

        Stats pkmnStat = statsArray.get(reference);

        int health = pkmnStat.hpStat;
        String hp = Integer.toString(health);
        TextView hpText = (TextView) findViewById(R.id.hpView);
        hpText.setText(hp);

        int attack = pkmnStat.attackStat;
        String atk = Integer.toString(attack);
        TextView atkText = (TextView) findViewById(R.id.atkView);
        atkText.setText(atk);

        int defense = pkmnStat.defenseStat;
        String def = Integer.toString(defense);
        TextView defText = (TextView) findViewById(R.id.defView);
        defText.setText(def);

        int specialAttack = pkmnStat.specialAttackStat;
        String spAtk = Integer.toString(specialAttack);
        TextView spAtkText = (TextView) findViewById(R.id.spAtkView);
        spAtkText.setText(spAtk);

        int specialDefense = pkmnStat.specialDefenseStat;
        String spDef = Integer.toString(specialDefense);
        TextView spDefText = (TextView) findViewById(R.id.spDefView);
        spDefText.setText(spDef);

        int speed = pkmnStat.speedStat;
        String spd = Integer.toString(speed);
        TextView spdText = (TextView) findViewById(R.id.speedView);
        spdText.setText(spd);
    }

   //get the abilities from the database and print it out
    public void getAbilities(int reference){
        pokedex = new MyDatabase(this);
        String[] allAbilities = pokedex.getAbilities();

        String[] myAbilities = pokedex.findAbilities(allAbilities, reference);

        String ability1 = myAbilities[0];
        TextView ab1 = (TextView) findViewById(R.id.testView);
        ab1.setText(ability1);

        String ability2 = myAbilities[1];
        TextView ab2 = (TextView) findViewById(R.id.testView2);
        ab2.setText(ability2);

        String ability3 = myAbilities[2];
        TextView ab3 = (TextView) findViewById(R.id.testView3);
        ab3.setText(ability3);

    }
   //get pokemon type form the database and print it out
    public void getTyping(int reference){
        pokedex = new MyDatabase(this);
        String[] allTypes = pokedex.getTypes();

        String[] pkmnType = pokedex.findTyping(allTypes, reference);

       //get the types and print them out
        String type1 = pkmnType[0];
        TextView typeDisplay1 = (TextView) findViewById(R.id.typeView1);
        typeDisplay1.setText(type1);

        String type2 = pkmnType[1];
        TextView typeDisplay2 = (TextView) findViewById(R.id.typeView2);
        typeDisplay2.setText(type2);

    }

   //onclick transfer for activities
    public void onMovesClick(View v){
        Intent intent = new Intent(v.getContext(), MovesDetailActivity.class);

        TextView get = (TextView) findViewById(R.id.pokedex_number);
        String send = get.getText().toString();

        intent.putExtra("dexNum", send);
        startActivity(intent);
    }



    public void onFinishClick(View v){
        finish();
    }

   //class for downloading the images
    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage){
            this.bmImage = bmImage;
        }

       //get the bitmap images
        protected Bitmap doInBackground(String... urls){
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try{
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            }
           //exceptions if failed
            catch(Exception e){
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

       //after execution
        protected void onPostExecute(Bitmap result){
            bmImage.setImageBitmap(result);
        }
    }

}

