package com.example.keetmalin.tictactoe_1;

import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class Options_Activity extends ActionBarActivity {

    Button btnBack;
    RadioButton rBtnOn,rBtnOff;
    TextView txtSound;
    RadioGroup rGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_);

        rBtnOff = (RadioButton) findViewById(R.id.rBtnOff);
        rBtnOn = (RadioButton) findViewById(R.id.rBtnOn);
        btnBack= (Button) findViewById(R.id.btnBack);
        txtSound = (TextView) findViewById(R.id.txtSound);
        rGroup = (RadioGroup) findViewById(R.id.rGroup);



        Typeface font = Typeface.createFromAsset(getAssets(), "BAUHS93.TTF");

        rBtnOff.setTypeface(font);
        rBtnOn.setTypeface(font);
        btnBack.setTypeface(font);
        txtSound.setTypeface(font);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.rBtnOff){
                    Toast.makeText(getApplicationContext(), "this is my Toast message!!! =)",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options_, menu);
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
