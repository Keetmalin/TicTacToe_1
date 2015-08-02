package com.example.keetmalin.tictactoe_1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.support.v4.app.ShareCompat;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class MultiplayerMode_Activity extends ActionBarActivity {

    public static BluetoothSocket mmSocket;
    public static BluetoothSocket socket = null;


    Button btnPlay ,btnBack;
    BluetoothAdapter mBluetoothAdapter;

    ArrayList<BluetoothDevice> devices;
    Set<BluetoothDevice> devicesArray;
    ArrayList<String> bluetoothDevices;
    ArrayAdapter<String> listAdapter;
    ListView listView;


    IntentFilter filter;
    BroadcastReceiver receiver;
    static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_mode_);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnPlay = (Button) findViewById(R.id.btnPlay);

        Typeface font = Typeface.createFromAsset(getAssets(), "BAUHS93.TTF");

        btnPlay.setTypeface(font);
        btnBack.setTypeface(font);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        devices = new ArrayList<BluetoothDevice>();
        bluetoothDevices= new ArrayList<String>();
        listView = (ListView) findViewById(R.id.listView);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                if (mBluetoothAdapter.isDiscovering()){
                    mBluetoothAdapter.cancelDiscovery();
                }
                Object [] o=devicesArray.toArray();
                BluetoothDevice selected = devices.get(arg2);
                ConnectThread ct = new ConnectThread(selected);
                //at.stop();
                ct.start();
            }
            }
        );

        activateBluetooth();
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bluetoothDevices);
        listView.setAdapter(listAdapter);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDiscovery();
                Intent temp = getIntent();
                Context temp2 = getBaseContext();
                mReceiver.onReceive(temp2,temp );

            }
        });

    }

    private void getPairedDevices() {

        if (devicesArray != null && devicesArray.size() != 0 ){
            for (BluetoothDevice device:devicesArray){
                devices.add(device);
                bluetoothDevices.add(device.getName() + "  (paired)  ");
            }
        }
    }

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                if (!bluetoothDevices.contains(device.getName())){
                    devices.add(device);
                    bluetoothDevices.add(device.getName());
                    listView.setAdapter(listAdapter);
                }

            }
        }
    };


    private void activateBluetooth() {
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "The Device does not support Bluetooth",Toast.LENGTH_LONG).show();
        }else{
            if (!mBluetoothAdapter.isEnabled()) {
                bluetoothOn();
            }
            if (mBluetoothAdapter.isEnabled()){
                devicesArray = mBluetoothAdapter.getBondedDevices();
                AcceptThread at = new AcceptThread();
                at.start();
                getPairedDevices();
            }

            startDiscovery();

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

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "Couldn't connect bluetooth in device",Toast.LENGTH_LONG).show();
        } else if (resultCode==RESULT_OK){
            activateBluetooth();

        }
    }

    private class ConnectThread extends Thread {


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
            Intent gameIntent = new Intent(MultiplayerMode_Activity.this, GameArea_Activity.class);
            gameIntent.putExtra("SELECTION" , 5);
            startActivity(gameIntent);

        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }
    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("Tic Tac Toe", MY_UUID);
            } catch (IOException e) { }
            mmServerSocket = tmp;
        }

        public void run() {
            //BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned
            while (true) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                    manageConnectedSocket(socket);
                    try {
                        mmServerSocket.close();
                    } catch (IOException e) { }
                    break;
                }
            }
        }

        private void manageConnectedSocket(BluetoothSocket socket) {
            mmSocket=socket;
            Intent gameIntent = new Intent(MultiplayerMode_Activity.this, GameArea_Activity.class);
            gameIntent.putExtra("SELECTION" , 5);
            startActivity(gameIntent);
        }

        /** Will cancel the listening socket, and cause the thread to finish */
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) { }
        }
    }
}


