package com.example.demouser.seven;

import android.app.Activity;
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

        final Intent intent = new Intent();

        ((Button) findViewById(R.id.red_button)).setOnClickListener(new View
                .OnClickListener() {

            @Override
            public void onClick(View v) {
                intent.putExtra(COLOR_CHANGE, "red");
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        ((Button) findViewById(R.id.blue_button)).setOnClickListener(new View
                .OnClickListener() {

            @Override
            public void onClick(View v) {
                intent.putExtra(COLOR_CHANGE, "blue");
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        ((Button) findViewById(R.id.yellow_button)).setOnClickListener(new View
                .OnClickListener() {

            @Override
            public void onClick(View v) {
                intent.putExtra(COLOR_CHANGE, "yellow");
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        ((Button) findViewById(R.id.green_button)).setOnClickListener(new View
                .OnClickListener() {

            @Override
            public void onClick(View v) {
                intent.putExtra(COLOR_CHANGE, "green");
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
