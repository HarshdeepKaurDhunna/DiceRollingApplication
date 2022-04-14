package com.example.dicerollingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
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
    Button customDice;
    TextView resultOne;
    TextView resultTwice;
    TextView appLabel;
    EditText customDiceValue;
    LinearLayout staticLayout;
    String selListVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dicesName = new ArrayList(Arrays.asList("Dice 4", "Dice 6", "Dice 8", "Dice 10", "Dice 10 (100)", "Dice 12", "Dice 20"));
        diceIcons = new ArrayList(Arrays.asList(R.drawable.dice_4, R.drawable.dice_6, R.drawable.dice_8, R.drawable.dice_10, R.drawable.dice_100, R.drawable.dice_12, R.drawable.dice_20));
        rollDice = findViewById(R.id.rollDice);
        rollDiceTwice = findViewById(R.id.rollDiceTwice);
        diceListView = findViewById(R.id.diceListView);
        resultOne = findViewById(R.id.resultOne);
        resultTwice = findViewById(R.id.resultTwice);
        staticLayout= findViewById(R.id.staticLayout);
        customDiceValue = findViewById(R.id.customDiceValue);
        customDice = findViewById(R.id.customDice);
        appLabel = findViewById(R.id.appLabel);
        customAdapter = new CustomAdapter(this, dicesName, diceIcons);
        diceListView.setAdapter(customAdapter);
        staticLayout.setVisibility(View.GONE);
        rollDice.setVisibility(View.GONE);
        rollDiceTwice.setVisibility(View.GONE);

        diceListView.setVisibility(View.GONE);
        getSelectedListItem();
        callButtonClick();

    }


    public void getSelectedListItem() {
        diceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selListVal = customAdapter.getItem(i).toString();
                rollDice.setVisibility(View.VISIBLE);
                rollDiceTwice.setVisibility(View.VISIBLE);
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
                resultTwice.setText("The two results up for " + selListVal + " is " + diceRandomVal1 + " and " + diceRandomVal2);
            }
        });
        customDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice.setVisibility(View.VISIBLE);
                rollDiceTwice.setVisibility(View.VISIBLE);
            }
        });
    }

    public String getUpsideVal() {
        String[] maxVal = selListVal.split("\\s+");
        int diceMaxVal = Integer.parseInt(maxVal[1]);
        String randomVal = "";
        if (diceMaxVal == 10) {
            int d10Val = (int) (Math.random() * (10 - 0)) + 0;
            randomVal = maxVal.length == 3 ? String.valueOf(d10Val * 10) : String.valueOf(d10Val);
        } else {
            randomVal = String.valueOf((int) (Math.random() * diceMaxVal) + 1);
        }
        return randomVal;
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_custom:
                if (checked)
                    staticLayout.setVisibility(View.VISIBLE);
                    appLabel.setText("Please Enter The Numeric Value of Dice");
                    diceListView.setVisibility(View.GONE);
                    customDiceValue.setVisibility(View.VISIBLE);
                    customDice.setVisibility(View.VISIBLE);
                    break;
            case R.id.radio_static:
                if (checked)
                    staticLayout.setVisibility(View.VISIBLE);
                    diceListView.setVisibility(View.VISIBLE);
                    customDiceValue.setVisibility(View.GONE);
                    customDice.setVisibility(View.GONE);
                    appLabel.setText("Please select the Dice to Roll");
                    break;
        }
    }
}