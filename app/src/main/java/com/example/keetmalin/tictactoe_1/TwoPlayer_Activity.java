package com.example.keetmalin.tictactoe_1;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


public class TwoPlayer_Activity extends ActionBarActivity {
    PopupWindow networkSelection;
    Button btnMultiplayer,btnVsMode , btnBack;
    LinearLayout layoutOfPop , thisLayout;
    FrameLayout.LayoutParams params;
    TextView text;
    int rBtnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_);
        btnMultiplayer = (Button) findViewById(R.id.btnMultiplayer);
        btnVsMode = (Button) findViewById(R.id.btnVsMode);
        btnBack= (Button) findViewById(R.id.btnBack);
        Typeface font = Typeface.createFromAsset(getAssets(), "BAUHS93.TTF");
        btnMultiplayer.setTypeface(font);
        btnVsMode.setTypeface(font);
        btnBack.setTypeface(font);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnMultiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TwoPlayer_Activity.this , MultiplayerMode_Activity.class ));
            }
        });
        btnVsMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rBtnId = 4;
                Intent gameIntent = new Intent(TwoPlayer_Activity.this, GameArea_Activity.class);
                gameIntent.putExtra("SELECTION" , rBtnId);
                startActivity(gameIntent);
            }
        });
        /*btnMultiplayer = (Button) findViewById(R.id.btnMultiplayer);
        networkSelection = new PopupWindow(this);
        layoutOfPop = new LinearLayout(this);
        thisLayout = new LinearLayout(this);
        text = new TextView(this);

        btnMultiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkSelection.showAtLocation(thisLayout, Gravity.BOTTOM, 10, 10);
                networkSelection.update(50,50,300,80);

            }

        });

        params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutOfPop.setOrientation(LinearLayout.VERTICAL);
        text.setText("Please select multiplayer mode");
        layoutOfPop.addView(text,params);
        networkSelection.setContentView(layoutOfPop);
        //setContentView(thisLayout);*/
        //setContentView(R.layout.activity_two_player_);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_two_player_, menu);
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
