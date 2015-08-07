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
import android.widget.TextView;
import android.widget.Toast;

import com.example.keetmalin.tictactoe_1.MultiplayerMode_Activity.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;


public class GameArea_Activity extends ActionBarActivity {

    //This will turn true if multiplayer mode is selected
    boolean multiCheck = false;

    //this is to avoid the same device to write and read at the same time
    boolean writeCheck = true;

    DatabaseHandler dbHandler;

    Game game;
    int rBtnSelected;

    Button btnBack,btnRestart;
    private Button buttons[];
    //This will acccpet the connected thread and transfer and receive data
    ConnectedThread connectedThread;//= new ConnectedThread(MultiplayerMode_Activity.mmSocket);

    //display name and scores in the game area
    TextView textView1,textView2;
    String playerTwoName, playerOneName;
    public int k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //ConnectedThread connectedThread;
        //playerTwoName = "Computer";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_area_);

        dbHandler = new DatabaseHandler(this,null,null,1);

        textView1 = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);

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

        Intent temp = getIntent();//get intent passed from other activities
        rBtnSelected = temp.getIntExtra("SELECTION" , 1);
        playerOneName = temp.getStringExtra("PLAYER_ONE_NAME");

        //naming the player for database
        if (rBtnSelected!=5){
            if (playerOneName.equals("") ||playerOneName == null ){
                playerOneName= "Player 1";
            }
        }


        //checking whther multiplayer mode is selected.
        if (rBtnSelected==4){
            playerTwoName = temp.getStringExtra("PLAYER_TWO_NAME");
            if (playerTwoName.equals("") || playerOneName == null ){
                playerTwoName= "Player 2";
            }
            textView2.setText(playerTwoName);
            if(!dbHandler.playerExists(playerTwoName)){
                dbHandler.insertNewPlayer(playerTwoName);
            }
        }
        //checking whitch mode to play
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
                //btnRestart.setVisibility(View.GONE);
                multiCheck = true;
                connectedThread= new ConnectedThread(MultiplayerMode_Activity.mmSocket);
                connectedThread.start();
                playMultiplayerGame();
                break;
        }

        btnBack= (Button) findViewById(R.id.btnBack);
        btnRestart= (Button) findViewById(R.id.btnRestart);

        if (rBtnSelected!=5){
            if(!dbHandler.playerExists(this.game.getPlayer1().getName())){
                dbHandler.insertNewPlayer(this.game.getPlayer1().getName());
            }


            if ( playerTwoName == null || playerTwoName.equals("")  ){
                playerTwoName= "Computer";
            }


            if(!dbHandler.playerExists(this.game.getPlayer2().getName())){
                dbHandler.insertNewPlayer(this.game.getPlayer2().getName());
            }
            String[] results = dbHandler.DatabaseToString().split("\n");
        }




        Typeface font = Typeface.createFromAsset(getAssets() , "BAUHS93.TTF");
        btnBack.setTypeface(font);



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //byte[] bytes={1,0,1,1,1,1,1,0};
                if (rBtnSelected==5){
                    connectedThread.cancel();
                }
                finish();
                //connectedThread.write(bytes);
            }
        });
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rBtnSelected==5){

                }
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        //pl1 = (TextView) findViewById(R.id.player1);

        String allResults[] = dbHandler.DatabaseToString().split("\n");
        //pl1.setText("");
        textView1.setText("");
        textView2.setText("");

        if (rBtnSelected!=5){
            for(String record :allResults){
                if(record.contains(this.game.getPlayer1().getName())){//||record.contains(playerTwoName)){
                    textView1.setText(record);
                }
                if(record.contains(this.game.getPlayer2().getName())){//||record.contains(playerTwoName)){
                    textView2.setText(record);
                }
            }
        }

    }
    //conversion methods
    public int toInt(byte[] arr){
        ByteBuffer wrapped = ByteBuffer.wrap(arr);
        int num =wrapped.getShort();
        return num/4;
    }
    public byte[] toBuf(int arr){

        {
            byte[] result = new byte[4];

            result[0] = (byte) (arr >> 24);
            result[1] = (byte) (arr >> 16);
            result[2] = (byte) (arr >> 8);
            result[3] = (byte) (arr /*>> 0*/);

            return result;
        }
       // ByteBuffer dbuf = ByteBuffer.allocate(arr);
        //dbuf.putInt(arr*4);

        //return dbuf.array();
    }

    public void addListenerOnButton() {
        for (int i = 1; i < 10; i++) {
            buttons[i].setOnClickListener(new myclickeListener(buttons[i], i));
        }
    }
    boolean temp = false;
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
                //connectedThread.write(toBuf((short) number));
                //connectedThread.start();
                Object o=game.play(button,number,buttons);

                if (multiCheck){

                    connectedThread.write(toBuf(number));

                }

                //if (temp){
                //    toBuf((short) number)
                //}
                if (o instanceof Integer){
                    switch ((int)o){
                        case 0 :
                            Toast.makeText(getApplicationContext(),"Game Drawn" , Toast.LENGTH_LONG).show();
                            break;
                        case 1 :
                            if (rBtnSelected!=5){
                                dbHandler.updateScore(game.getPlayer1().getName());
                                //Toast.makeText(getApplicationContext(),game.playerOne +" won the game" , Toast.LENGTH_LONG).show();
                                //textView1.setText(dbHandler.DatabaseToString(game.playerOne));
                                String allResults[] = dbHandler.DatabaseToString().split("\n");

                                for(String record :allResults){
                                    if(record.contains(game.getPlayer1().getName())){//||record.contains(playerTwoName)){
                                        textView1.setText(record);
                                    }

                                }
                            }

                            break;
                        case 2 :
                            if (rBtnSelected!=5){
                                dbHandler.updateScore(game.getPlayer2().getName());
                                //Toast.makeText(getApplicationContext(),game.playerTwo +" won the game" , Toast.LENGTH_LONG).show();
                                //textView2.setText(dbHandler.DatabaseToString(game.playerTwo));\
                                String allResults2[] = dbHandler.DatabaseToString().split("\n");


                                for(String record :allResults2){
                                    if(record.contains(game.getPlayer2().getName())){//||record.contains(playerTwoName)){
                                        textView2.setText(record);
                                    }

                                }
                            }

                            break;
                        case 3 :
                            //writeCheck=true;
                            return;

                    }
                    for(int i = 1 ;i<10;i++){
                        buttons[i].setEnabled(false);
                    }

                    //Intent intent = getIntent();
                    //finish();
                    //startActivity(intent);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
    //create methods for each game
    public void playEasyGame(){
        game = new SinglePlayerEasyGame(playerOneName);
    }
    public void playMediumGame(){
        game = new SinglePlayerMediumGame(playerOneName);
    }
    public void playHardGame(){
        game = new SinglePlayerHardGame(playerOneName);
    }
    public void playTwoPlayerGame(){
        game = new TwoPlayerGame(playerOneName,playerTwoName);

    }
    private void playMultiplayerGame() {
            game = new TwoPlayerGame(playerOneName,playerTwoName);
        //synchronized (this){
            //connectedThread.start();
        //}


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
                        //System.out.println(msg.obj);
                        //Toast.makeText(getApplicationContext(), "CONNECT", Toast.LENGTH_LONG).show();
                        byte[] buffer = new byte[1024];
                        //int x = (int) msg.obj;
                        buffer = (byte[]) msg.obj;
                        int k =buffer[3];
                        System.out.println(k);
                        //System.out.println(msg.);
                        //k = msg.arg1;
                        //buttons[k].setOnClickListener(new myclickeListener(buttons[k], k));
                        if (writeCheck){
                            buttons[k].performClick();
                        }
                        writeCheck=true;
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

                        //System.out.println("read");
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
                writeCheck=false;
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

