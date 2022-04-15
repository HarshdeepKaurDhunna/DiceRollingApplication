
/**
 * @author Name: Harshdeep Kaur Dhunna
 *         Student number: A00246003
 *         Program description: JAV-1001 - 11329 - App Development for Android -
 *         202201 - 001
 */
package com.example.dicerollingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String customVal = "customValKey";
    ArrayList<Integer> diceIcons;
    CustomAdapter customAdapter;
    Button rollDice;
    Button rollDiceTwice;
    Button customDice;
    Button backMain;
    TextView resultOne;
    TextView resultTwice;
    TextView appLabel;
    EditText customDiceValue;
    LinearLayout staticLayout;
    LinearLayout mainLayout;
    String selListVal = "";
    int diceMaxVal = 0;
    Spinner spinner;
    SharedPreferences sharedpreferences;
    private ListView diceListView;
    private ArrayList<String> dicesName;
    ArrayList<String> customDiceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize list
        dicesName = new ArrayList(Arrays.asList("Dice 4", "Dice 6", "Dice 8", "Dice 10", "Dice 10 (100)", "Dice 12", "Dice 20"));
        diceIcons = new ArrayList(Arrays.asList(R.drawable.dice_4, R.drawable.dice_6, R.drawable.dice_8, R.drawable.dice_10, R.drawable.dice_100, R.drawable.dice_12, R.drawable.dice_20));
        customDiceList = new ArrayList(Arrays.asList("4","6","8","10","12","20"));
        //Initialize the widgets
        rollDice = findViewById(R.id.rollDice);
        rollDiceTwice = findViewById(R.id.rollDiceTwice);
        diceListView = findViewById(R.id.diceListView);
        resultOne = findViewById(R.id.resultOne);
        resultTwice = findViewById(R.id.resultTwice);
        staticLayout = findViewById(R.id.staticLayout);
        customDiceValue = findViewById(R.id.customDiceValue);
        customDice = findViewById(R.id.customDice);
        appLabel = findViewById(R.id.appLabel);
        mainLayout = findViewById(R.id.mainLayout);
        backMain = findViewById(R.id.backMain);
        spinner = findViewById(R.id.spinner);;
        customAdapter = new CustomAdapter(this, dicesName, diceIcons);
        diceListView.setAdapter(customAdapter);

        //Method to hide the @staticLayout
        hideSecondLayout();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        getSelectedListItem();
        callButtonClick();
        setListnerSelect();
    }

    /**
     * Method to get the selected dice from the list
     */
    public void getSelectedListItem() {
        diceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selListVal = customAdapter.getItem(i).toString();
                showRollButton();
                String[] maxVal = selListVal.split("\\s+");
                diceMaxVal = Integer.parseInt(maxVal[1]);
            }
        });
    }

    /**
     * Button call backs methods
     */
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
                if(customDiceValue.getText().toString().isEmpty() || customDiceValue.getText().toString() == null){
                    Toast.makeText(getApplicationContext(),
                            "Enter Custom Value",
                            Toast.LENGTH_LONG);
                }else{

                    if(customDiceValue.getText().toString().matches("\\d+")) {
                        if(customDiceValue.getText().toString()=="0"){
                            Toast.makeText(MainActivity.this, "Please Enter Number Greater Than zero ", Toast.LENGTH_SHORT).show();
                        }
                        else if(customDiceList.toString().contains(customDiceValue.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Number Already Exists ", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            selListVal = customDiceValue.getText().toString();
                            diceMaxVal = Integer.parseInt(selListVal);
                            customDiceList.add(selListVal);
                            Collections.sort(customDiceList);
                            customDiceValue.setText("");
                        }
                    }

                }

            }
        });
        backMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSecondLayout();
                mainLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setListnerSelect(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, customDiceList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                selListVal = parent.getItemAtPosition(i).toString();
                showRollButton();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(customVal, selListVal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });
    }

    /**
     * Roll the dice and get the upside from random number
     */
    public String getUpsideVal() {
        String[] maxVal = selListVal.split("\\s+");
        String randomVal = "";
        if (diceMaxVal == 10) {
            int d10Val = (int) (Math.random() * (10 - 0)) + 0;
            randomVal = maxVal.length == 3 ? String.valueOf(d10Val * 10) : String.valueOf(d10Val);
        } else {
            randomVal = String.valueOf((int) (Math.random() * diceMaxVal) + 1);
        }
        return randomVal;
    }

    /**
     * get the desired option for custom play or static on select
     */
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_custom:
                if (checked)
                    staticLayout.setVisibility(View.VISIBLE);
                appLabel.setText("Please Enter Numeric Value");
                diceListView.setVisibility(View.GONE);
                customDiceValue.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                customDice.setVisibility(View.VISIBLE);
                mainLayout.setVisibility(View.GONE);
                backMain.setVisibility(View.VISIBLE);
                resultOne.setText("");
                resultTwice.setText("");
                customDiceValue.setText(null);
                break;
            case R.id.radio_static:
                if (checked)
                    staticLayout.setVisibility(View.VISIBLE);
                diceListView.setVisibility(View.VISIBLE);
                customDiceValue.setVisibility(View.GONE);
                spinner.setVisibility(View.GONE);
                customDice.setVisibility(View.GONE);
                appLabel.setText("Please select the Dice to Roll");
                mainLayout.setVisibility(View.GONE);
                backMain.setVisibility(View.VISIBLE);
                resultOne.setText("");
                resultTwice.setText("");
                customDiceValue.setText(null);
                break;
        }
    }

    /**
     * Show buttons to roll dice
     */
    public void showRollButton() {
        rollDice.setVisibility(View.VISIBLE);
        rollDiceTwice.setVisibility(View.VISIBLE);
        rollDice.setText("Roll " + selListVal);
        rollDiceTwice.setText("Roll Twice " + selListVal);
        resultOne.setText("");
        resultTwice.setText("");
    }

    /**
     * Hide the widgets of second layout
     */
    public void hideSecondLayout() {
        staticLayout.setVisibility(View.GONE);
        rollDice.setVisibility(View.GONE);
        rollDiceTwice.setVisibility(View.GONE);
        backMain.setVisibility(View.GONE);
        diceListView.setVisibility(View.GONE);
        selListVal = "";
        customDiceValue.setText(null);
        resultOne.setText("");
        resultTwice.setText("");
    }
}