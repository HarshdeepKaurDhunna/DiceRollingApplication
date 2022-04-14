package com.example.dicerollingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ListView diceListView;
    private ArrayList<String> dicesName;
    ArrayList<Integer> diceIcons;
    CustomAdapter customAdapter;
    Button rollDice;
    Button rollDiceTwice;
    TextView resultOne;
    TextView resultTwice;
    String selListVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dicesName = new ArrayList(Arrays.asList("Dice 4", "Dice 6", "Dice 8", "Dice 10", "Dice 12", "Dice 20"));
        diceIcons = new ArrayList(Arrays.asList(R.drawable.dice_4, R.drawable.dice_6, R.drawable.dice_8, R.drawable.dice_10, R.drawable.dice_12, R.drawable.dice_20 ));
        rollDice = findViewById(R.id.rollDice);
        rollDiceTwice = findViewById(R.id.rollDiceTwice);
        rollDice.setVisibility(View.GONE);
        rollDiceTwice.setVisibility(View.GONE);
        diceListView  =  findViewById(R.id.diceListView);
        resultOne =  findViewById(R.id.resultOne);
        resultTwice =  findViewById(R.id.resultTwice);

        customAdapter = new CustomAdapter(this,dicesName,diceIcons);
        diceListView.setAdapter(customAdapter);
        getSelectedListItem();
        callButtonClick();

    }


    public void getSelectedListItem(){
        diceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 selListVal = customAdapter.getItem(i).toString();
                 rollDice.setVisibility(View.VISIBLE );
                 rollDiceTwice.setVisibility(View.VISIBLE );
                 rollDice.setText("Roll " + selListVal);
                 rollDiceTwice.setText("Roll Twice " + selListVal);
                 resultOne.setText("");
                 resultTwice.setText("");
            }
        });
    }
   public void callButtonClick() {
        rollDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diceRandomVal = getUpsideVal();
                resultOne.setText("The current side up for " + selListVal + " is " + diceRandomVal);

            }
        });

        rollDiceTwice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diceRandomVal1 = getUpsideVal();
                String diceRandomVal2 = getUpsideVal();
                resultTwice.setText("The two results up for " + selListVal + " is " + diceRandomVal1 + " and " +diceRandomVal2);
            }
        });
    }
    public String getUpsideVal(){
        String[] maxVal = selListVal.split("\\s+");
        int diceMaxVal =  Integer.parseInt(maxVal[1]);
        String randomVal = String.valueOf((int)(Math.random() * diceMaxVal) + 1);
        return randomVal;
    }

}