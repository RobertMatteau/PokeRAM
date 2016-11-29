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

import com.pokemonram.pokeram.Data.Move;
import com.pokemonram.pokeram.Data.Pokemon;
import com.pokemonram.pokeram.Database.MyDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MovesDetailActivity extends AppCompatActivity {


        private int reference;
        private MyDatabase pokedex;
        ArrayAdapter<String> adapterMovesDetail;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_moves_detail);

            pokedex = new MyDatabase(this);
            pokedex.getReadableDatabase();
            ArrayList<Pokemon> pok = pokedex.getPokemon();

            Intent get = getIntent();
            String ref = get.getStringExtra("dexNum");
            reference = Integer.parseInt(ref);

            TextView title = (TextView)findViewById(R.id.title_moves_detail);
            Pokemon pkname = pok.get((reference-1));
            String move_title = pkname.name.toUpperCase();
            title.setText(move_title + " Moves");




            //TextView numberView = (TextView) findViewById(R.id.number);
            //numberView.setText(reference);



        }

        protected void onStart(){
            super.onStart();

            listViewSetup();
        }

    public static ArrayList<Move> sortMoveDetail(ArrayList<Move> move) {
        ArrayList<Move> sortedMoveDetail = move;

        Collections.sort(sortedMoveDetail, new Comparator<Move>() {
            public int compare(Move m1, Move m2) {
                return m1.getMoveName().compareToIgnoreCase(m2.getMoveName());

            }
        });

        return sortedMoveDetail;
    }


    public void listViewSetup(){
        pokedex = new MyDatabase(this);

        ArrayList<Move> allMoves = pokedex.getMoves();
        ArrayList<Move> setMoves = pokedex.getMoveSet(allMoves, (reference));
        ArrayList<Move> sortedmoveDetailList = sortMoveDetail(setMoves);



        /*String[] movedetailsearch = new String[621];
        int i = 0;
        while(i < 621 ){
            Move a = sortedmoveDetailList.get(i);
            movedetailsearch[i] = (a.moveName).toUpperCase();


            if(movedetailsearch[i+1] == null)
            {
                break;
            }
            else{
                i++;
            }
            */






        //TextView getView = (TextView) findViewById(R.id.number);
        //int reference = Integer.parseInt(getView.getText().toString());

        ListView moveDetailListView=(ListView)findViewById(R.id.movelist);



        EditText inputsearch = (EditText)findViewById(R.id.edittext_movename);


        //String[] movedetailsearch = new String[200];
        //for(int i=0; i < 2--; i++){
        //    Move a = sortedabilityList.get(i);
        //    abilitysearch[i] = a.abilities.toUpperCase();
        //}


        //String[] abilitysearch = pokedex.getAbilities();
        //adapterMovesDetail = new ArrayAdapter<String>(this,R.layout.move_detaillistview,R.id.name,movedetailsearch);




        com.pokemonram.pokeram.MoveDetailAdapter moveAdd = new com.pokemonram.pokeram.MoveDetailAdapter(
                    this,
                    sortedmoveDetailList
            );

        moveDetailListView.setAdapter(moveAdd);

        moveDetailListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //Intent intent = new Intent(view.getContext(), DetailedPokemonActivity.class);

                //long dexNum = id+1;
                //intent.putExtra("dexNum", dexNum);

                //startActivity(intent);
            }
        });


        /*
        inputsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                MovesDetailActivity.this.adapterMovesDetail.getFilter().filter(cs);
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
        });;*/
        }


        public void onFinishClick(View v){
            finish();
        }
    }

    class MoveDetailAdapter extends ArrayAdapter<Move> {

        public MoveDetailAdapter(Context context, ArrayList<Move> movelist){
            super(context, 0, movelist);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Move move = getItem(position);


            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.move_detaillistview, parent, false);
            }

            ((TextView)convertView.findViewById(R.id.name)).setText(move.moveName.toUpperCase());
            ((TextView)convertView.findViewById(R.id.typing)).setText(move.type.toUpperCase());

            return convertView;
        }
    }


