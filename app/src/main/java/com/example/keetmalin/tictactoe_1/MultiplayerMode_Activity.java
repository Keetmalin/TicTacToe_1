package com.example.keetmalin.tictactoe_1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class MultiplayerMode_Activity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    Button btnPlay ,btnBack;
    RadioButton rBtnBluetooth;
    BluetoothAdapter mBluetoothAdapter;
    ArrayAdapter<String> listAdapter;
    ListView listView;
    Set<BluetoothDevice> devicesArray;
    ArrayList<String> pairedDevices;
    IntentFilter filter;
    BroadcastReceiver receiver;
    static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_mode_);
        init();

        activateBluetooth();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void getPairedDevices() {
        devicesArray = mBluetoothAdapter.getBondedDevices();
        if (devicesArray.size() >0 ){
            for (BluetoothDevice device:devicesArray){
                pairedDevices.add(device.getName());

            }
        }
    }

    private void activateBluetooth() {
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "The Device does not support Bluetooth",Toast.LENGTH_LONG).show();
        }else{
            if (!mBluetoothAdapter.isEnabled()) {
                bluetoothOn();
            }
            getPairedDevices();
            startDiscovery();
            /*if (mBluetoothAdapter.isEnabled()) {
                startActivity(new Intent(MultiplayerMode_Activity.this , ConnectToADevice_Activity.class));
            }*/
        }
    }

    private void startDiscovery() {
        mBluetoothAdapter.cancelDiscovery();
        mBluetoothAdapter.startDiscovery();
    }

    private void bluetoothOn() {
        int REQUEST_ENABLE_BT = 1;
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    /*protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
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
            startActivity(selectionIntent);

            //startActivity(new Intent(MultiplayerMode_Activity.this , ConnectToADevice_Activity.class));
        }
    }*/

    /*
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
    }*/
    private void init(){
        rBtnBluetooth = (RadioButton) findViewById(R.id.rBtnBluetooth);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnPlay = (Button) findViewById(R.id.btnPlay);

        Typeface font = Typeface.createFromAsset(getAssets(), "BAUHS93.TTF");

        btnPlay.setTypeface(font);
        rBtnBluetooth.setTypeface(font);
        btnBack.setTypeface(font);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        listAdapter = new ArrayAdapter<String>(this, R.layout.activity_multiplayer_mode_,0);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(listAdapter);
        pairedDevices= new ArrayList<String>();

        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (BluetoothDevice.ACTION_FOUND.equals(action)){
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    String s    ="";
                    for (int a=0;a<pairedDevices.size() ; a++){
                        if (device.getName().equals(pairedDevices.get(a))){
                            //append
                            s = "   (Paired)  ";

                            break;
                        }
                    }
                    listAdapter.add(device.getName() +s + "\n" + device.getAddress());

                }
                else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){

                }
                else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){


                }
                else if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
                    if (mBluetoothAdapter.getState()== mBluetoothAdapter.STATE_OFF){
                        bluetoothOn();
                    }
                }
            }
        };
        registerReceiver(receiver,filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver(receiver,filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver,filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver,filter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        registerReceiver(receiver,filter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
        }

        if (listAdapter.getItem(position).contains("Paired")){
            Object [] o=devicesArray.toArray();
            BluetoothDevice selected = (BluetoothDevice) o[position];
            ConnectThread connect=new ConnectThread(selected);
        }
        else{
            Toast.makeText(getApplicationContext(), "device is not paired " , Toast.LENGTH_LONG).show();
        }
    }
    private class ConnectThread extends Thread {

        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) { }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }

            // Do work to manage the connection (in a separate thread)
            manageConnectedSocket(mmSocket);
        }

        private void manageConnectedSocket(BluetoothSocket mmSocket) {
        }

        /** Will cancel an in-progress connection, and close the socket */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }
}


