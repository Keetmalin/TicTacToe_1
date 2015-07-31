package com.example.keetmalin.tictactoe_1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MultiplayerMode_Activity extends ActionBarActivity {

    Button btnPlay ,btnBack;
    RadioButton rBtnWifi, rBtnBluetooth;
    RadioGroup rGroup;
    BluetoothAdapter mBluetoothAdapter;
    ArrayAdapter<String> listAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_mode_);

        rBtnBluetooth = (RadioButton) findViewById(R.id.rBtnBluetooth);
        rBtnWifi = (RadioButton) findViewById(R.id.rBtnWifi);
        rGroup = (RadioGroup) findViewById(R.id.rGroup);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnPlay = (Button) findViewById(R.id.btnPlay);

        Typeface font = Typeface.createFromAsset(getAssets(), "BAUHS93.TTF");

        btnPlay.setTypeface(font);
        rBtnBluetooth.setTypeface(font);
        rBtnWifi.setTypeface(font);
        btnBack.setTypeface(font);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        listAdapter = new ArrayAdapter<String>(this, R.layout.activity_multiplayer_mode_,0);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(listAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rBtnStatus = rGroup.getCheckedRadioButtonId();
                switch (rBtnStatus){
                    case R.id.rBtnBluetooth:

                        if (mBluetoothAdapter == null) {
                            Toast.makeText(getApplicationContext(), "The Device does not support Bluetooth",Toast.LENGTH_LONG).show();
                            break;

                        }else{
                            if (!mBluetoothAdapter.isEnabled()) {
                                int REQUEST_ENABLE_BT = 1;
                                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                            }
                            if (mBluetoothAdapter.isEnabled()) {
                                startActivity(new Intent(MultiplayerMode_Activity.this , ConnectToADevice_Activity.class));
                            }
                        }

                        break;

                }
                
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "Couldn't connect bluetooth in device",Toast.LENGTH_LONG).show();
        } else if (resultCode==RESULT_OK){


            /*Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            List<String> s = new ArrayList<String>();
            for(BluetoothDevice bt : pairedDevices)
                s.add(bt.getName());

            ArrayAdapter<String> mArrayAdapter =new  ArrayAdapter<String>(this, R.layout.activity_multiplayer_mode_, s);
            // If there are paired devices
            if (pairedDevices.size() > 0) {
                // Loop through paired devices
                for (BluetoothDevice device : pairedDevices) {
                    // Add the name and address to an array adapter to show in a ListView
                    mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            }
            Intent selectionIntent = new Intent(MultiplayerMode_Activity.this , ConnectToADevice_Activity.class);
            selectionIntent.putExtra("SELECTION" , (java.io.Serializable) mArrayAdapter);
            startActivity(selectionIntent);*/

            startActivity(new Intent(MultiplayerMode_Activity.this , ConnectToADevice_Activity.class));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multiplayer_mode_, menu);
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

