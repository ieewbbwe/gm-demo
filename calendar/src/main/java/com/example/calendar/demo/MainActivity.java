package com.example.calendar.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calendar.R;

public class MainActivity extends AppCompatActivity {
    Button mbutton1;
    TextView mtext1;
    ListView mlistView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        mbutton1 = (Button) findViewById(R.id.button1);
        mtext1 = (TextView) findViewById(R.id.textview1);
        mlistView1 = (ListView) findViewById(R.id.list1);
        mbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast mtoast = Toast.makeText(getApplicationContext(), "dianjile", Toast.LENGTH_SHORT);
                mtoast.show();
            }
        });

        String data[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
        ArrayAdapter madapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
        mlistView1.setAdapter(madapter);
        mlistView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String result = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(MainActivity.this, "list_item" + result, Toast.LENGTH_SHORT).show();
            }
        });

        mbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mintent = new Intent(MainActivity.this, otherActivity.class);
                startActivity(mintent);
            }
        });

    }


}
