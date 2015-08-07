package com.example.keetmalin.tictactoe_1;

import android.content.Intent;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class Difficulty_Activity extends ActionBarActivity {

    Button btnPlay, btnBack;
    RadioButton rBtnEasy , rBtnMedium , rBtnHard;
    RadioGroup rGroup;
    int rBtnId;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_);

        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnBack = (Button) findViewById(R.id.btnBack);
        rBtnEasy = (RadioButton) findViewById(R.id.rBtnEasy);
        rBtnMedium = (RadioButton) findViewById(R.id.rBtnMedium);
        rBtnHard = (RadioButton) findViewById(R.id.rBtnHard);
        rGroup = (RadioGroup) findViewById(R.id.rGroup);
        editText = (EditText) findViewById(R.id.editText);

        Typeface font = Typeface.createFromAsset(getAssets(), "BAUHS93.TTF");

        btnPlay.setTypeface(font);
        rBtnEasy.setTypeface(font);
        rBtnMedium.setTypeface(font);
        rBtnHard.setTypeface(font);
        btnBack.setTypeface(font);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int rBtnStatus = rGroup.getCheckedRadioButtonId();
                switch (rBtnStatus){
                    case R.id.rBtnEasy:
                        rBtnId = 1;
                        break;
                    case R.id.rBtnMedium:
                        rBtnId = 2;
                        break;
                    case R.id.rBtnHard:
                        rBtnId = 3;
                        break;
                }
                Intent gameIntent = new Intent(Difficulty_Activity.this, GameArea_Activity.class);
                String playerName = editText.getText().toString();
                gameIntent.putExtra("PLAYER_ONE_NAME",playerName);
                gameIntent.putExtra("SELECTION" , rBtnId);
                startActivity(gameIntent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_difficulty_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
