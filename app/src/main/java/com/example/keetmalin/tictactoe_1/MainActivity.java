package com.example.keetmalin.tictactoe_1;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    Button btnOnePlayer,btnTwoPlayer,btnOptions, btnHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        btnOnePlayer = (Button) findViewById(R.id.btnOnePlayer);
        btnTwoPlayer = (Button) findViewById(R.id.btnTwoPlayer);
        btnOptions = (Button) findViewById(R.id.btnOptions);
        btnHelp = (Button) findViewById(R.id.btnHelp);
        Typeface font = Typeface.createFromAsset(getAssets(), "BAUHS93.TTF");
        btnOnePlayer.setTypeface(font);
        btnTwoPlayer.setTypeface(font);
        btnOptions.setTypeface(font);
        btnHelp.setTypeface(font);

        btnOnePlayer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Difficulty_Activity.class));
            }
        });

        btnTwoPlayer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TwoPlayer_Activity.class));
            }
        });
        btnOptions.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Options_Activity.class));
            }
        });
        btnHelp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Help_Activity.class));
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
