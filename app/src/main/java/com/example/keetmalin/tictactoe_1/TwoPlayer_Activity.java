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
import android.widget.EditText;
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
    EditText editText1 , editText2;
    int rBtnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_);
        btnMultiplayer = (Button) findViewById(R.id.btnMultiplayer);
        btnVsMode = (Button) findViewById(R.id.btnVsMode);
        btnBack= (Button) findViewById(R.id.btnBack);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        //String player1,player2;

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
                String player1=editText1.getText().toString();
                String player2=editText2.getText().toString();
                Intent gameIntent = new Intent(TwoPlayer_Activity.this, GameArea_Activity.class);
                gameIntent.putExtra("SELECTION" , rBtnId);
                gameIntent.putExtra("PLAYER_ONE_NAME",player1);
                gameIntent.putExtra("PLAYER_TWO_NAME",player2);
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



}
