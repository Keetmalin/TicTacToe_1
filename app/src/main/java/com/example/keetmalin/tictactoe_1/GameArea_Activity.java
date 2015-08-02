package com.example.keetmalin.tictactoe_1;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.keetmalin.tictactoe_1.MultiplayerMode_Activity.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class GameArea_Activity extends ActionBarActivity {
    Game game;
    Button btnBack,btnRestart;
    private Button buttons[];
    ConnectedThread connectedThread;//= new ConnectedThread(MultiplayerMode_Activity.mmSocket);

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
            case 4 : playTwoPlayerGame();
                break;
            case 5 :
                btnRestart.setVisibility(View.GONE);
                connectedThread= new ConnectedThread(MultiplayerMode_Activity.mmSocket);
                playMultiplayerGame();
                break;
        }
        btnBack= (Button) findViewById(R.id.btnBack);
        btnRestart= (Button) findViewById(R.id.btnRestart);


        Typeface font = Typeface.createFromAsset(getAssets() , "BAUHS93.TTF");
        btnBack.setTypeface(font);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] bytes={1,0,1,1,1,1,1,0};
                //finish();
                connectedThread.write(bytes);
            }
        });
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
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
    public void playTwoPlayerGame(){
        game = new TwoPlayerGame("player1","player2");

    }
    private void playMultiplayerGame() {

        synchronized (this){
            connectedThread.start();
        }


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

    public class ConnectedThread extends Thread {

        private static final int MESSAGE_READ = 0 ;
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        Handler mHandler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch(msg.what){
                    case MESSAGE_READ:
                        Toast.makeText(getApplicationContext(), "CONNECT", Toast.LENGTH_LONG).show();
                        System.out.println(msg.arg1);
                        break;
                }
            }
        };



        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    System.out.println("read");
                    // Send the obtained bytes to the UI activity

                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
                System.out.println("wrote");
            } catch (IOException e) { }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

}

