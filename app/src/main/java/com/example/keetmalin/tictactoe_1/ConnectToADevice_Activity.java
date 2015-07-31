package com.example.keetmalin.tictactoe_1;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ConnectToADevice_Activity extends ActionBarActivity {
    Button btnPlay ,btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_adevice_);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnPlay = (Button) findViewById(R.id.btnPlay);

        Typeface font = Typeface.createFromAsset(getAssets(), "BAUHS93.TTF");

        btnPlay.setTypeface(font);
        btnBack.setTypeface(font);

        Intent temp = getIntent();
        int rBtnSelected = temp.getIntExtra("SELECTION" , 1);

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
        getMenuInflater().inflate(R.menu.menu_connect_to_adevice_, menu);
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
