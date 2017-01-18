package com.example.demouser.seven;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WildActivity extends AppCompatActivity {

    public final static String COLOR_CHANGE = "COLOR_CHANGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wild);

        final Intent intent = new Intent(this, GameActivity.class);

        ((Button) findViewById(R.id.red_button)).setOnClickListener(new View
                .OnClickListener() {

            @Override
            public void onClick(View v) {
                intent.putExtra(COLOR_CHANGE, "RED");
                finish();
            }
        });

        ((Button) findViewById(R.id.blue_button)).setOnClickListener(new View
                .OnClickListener() {

            @Override
            public void onClick(View v) {
                intent.putExtra(COLOR_CHANGE, "BLUE");
                finish();
            }
        });

        ((Button) findViewById(R.id.yellow_button)).setOnClickListener(new View
                .OnClickListener() {

            @Override
            public void onClick(View v) {
                intent.putExtra(COLOR_CHANGE, "YELLOW");
                finish();
            }
        });

        ((Button) findViewById(R.id.green_button)).setOnClickListener(new View
                .OnClickListener() {

            @Override
            public void onClick(View v) {
                intent.putExtra(COLOR_CHANGE, "GREEN");
                finish();
            }
        });
    }
}
