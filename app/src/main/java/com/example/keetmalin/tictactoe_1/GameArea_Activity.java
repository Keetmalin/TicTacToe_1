package com.example.keetmalin.tictactoe_1;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class GameArea_Activity extends ActionBarActivity {
    Game game;
    Button btnBack;
    private Button buttons[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_area_);

        buttons = new Button[10];//to make the coding easy array has 10 members
        buttons[1] = (Button) findViewById(R.id.one);
        buttons[2] = (Button) findViewById(R.id.two);
        buttons[3] = (Button) findViewById(R.id.three);
        buttons[4] = (Button) findViewById(R.id.four);
        buttons[5] = (Button) findViewById(R.id.five);
        buttons[6] = (Button) findViewById(R.id.six);
        buttons[7] = (Button) findViewById(R.id.seven);
        buttons[8] = (Button) findViewById(R.id.eight);
        buttons[9] = (Button) findViewById(R.id.nine);
        addListenerOnButton();

        Intent temp = getIntent();
        int rBtnSelected = temp.getIntExtra("SELECTION" , 1);
        switch (rBtnSelected){
            case 1 : playEasyGame();
                break;
            case 2 : playMediumGame();
                break;
            case 3 : playHardGame();
                break;
            case 4 : playMultiplayerGame();
                break;
        }
        btnBack= (Button) findViewById(R.id.btnBack);

        Typeface font = Typeface.createFromAsset(getAssets() , "BAUHS93.TTF");
        btnBack.setTypeface(font);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void addListenerOnButton() {
        for (int i = 1; i < 10; i++) {
            buttons[i].setOnClickListener(new myclickeListener(buttons[i], i));
        }
    }
    class myclickeListener implements View.OnClickListener {
        Button button = null;
        int number;

        public myclickeListener(Button button, int number) {
            this.button = button;
            this.number = number;
        }

        @Override

        public void onClick(View arg0) {
            try {
                if (game.play(button,number,buttons)){
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
    public void playEasyGame(){
        game = new SinglePlayerEasyGame("player1");
    }
    public void playMediumGame(){
        game = new SinglePlayerMediumGame("player1");
    }
    public void playHardGame(){
        game = new SinglePlayerHardGame("player1");
    }
    public void playMultiplayerGame(){
        game = new MultiplayerGame("player1","player2");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_area_, menu);
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

