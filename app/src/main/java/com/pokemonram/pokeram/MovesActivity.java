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
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

import com.pokemonram.pokeram.Data.Move;
import com.pokemonram.pokeram.Data.Pokemon;
import com.pokemonram.pokeram.Database.MyDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MovesActivity extends AppCompatActivity {

    private MyDatabase pokedex;
    ArrayAdapter<String> adapterMoves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moves);
    }

    protected void onStart(){
        super.onStart();

        listViewSetup();
    }

    public static ArrayList<Move> sortMove(ArrayList<Move> move) {
        ArrayList<Move> sortedMove = move;

        Collections.sort(sortedMove, new Comparator<Move>() {
            public int compare(Move m1, Move m2) {
                return m1.getMoveName().compareToIgnoreCase(m2.getMoveName());

            }
        });

        return sortedMove;
    }


    public void listViewSetup(){
        pokedex = new MyDatabase(this);

        ArrayList<Move> moveList = pokedex.getMoves();
        ArrayList<Move> sortedmoveList = sortMove(moveList);

        String[] movesearch = new String[600];
        for(int i=0; i < 600; i++){
            Move a = sortedmoveList.get(i);
            movesearch[i] = (a.moveName).toUpperCase();
        }



        ListView movesListView=(ListView)findViewById(R.id.movelist1);
        EditText inputsearchmove = (EditText)findViewById(R.id.edittext_movesallname);
        adapterMoves = new ArrayAdapter<String>(this,R.layout.move_listrow,R.id.name1,movesearch);





        MovesAdapter pkmnAdd = new MovesAdapter(
                this,
                sortedmoveList
        );

        movesListView.setAdapter(adapterMoves);

        movesListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(view.getContext(), DetailedPokemonActivity.class);

                long dexNum = id+1;
                intent.putExtra("dexNum", dexNum);

                startActivity(intent);
            }
        });

        inputsearchmove.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                MovesActivity.this.adapterMoves.getFilter().filter(cs);
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
        });;

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        pokedex.close();
    }

}

class MovesAdapter extends ArrayAdapter<Move>{

    private Context context;

    public MovesAdapter(Context context, ArrayList<Move> move){
        super(context, 0, move);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Move move = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.move_listrow, parent, false);
        }


        ((TextView)convertView.findViewById(R.id.name1)).setText(move.moveName.toUpperCase());
        //((TextView)convertView.findViewById(R.id.typing1)).setText(move.type);

        //((ImageView));
        //((ImageView));

        return convertView;
    }

}
