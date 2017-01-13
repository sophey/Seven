package com.example.demouser.seven;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameActivity extends AppCompatActivity {

    private ArrayList<String> player1Cards;
    private ArrayList<String> player2Cards;
    private Map<String, Integer> deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // initialize deck
        initDeck();

        // deal and display cards
        dealCards();
        displayCards();
    }

    private void initDeck() {
        deck = new HashMap<String, Integer>();
        deck.put("blue_0", 1);
        deck.put("blue_1", 2);
        deck.put("blue_2", 2);
        deck.put("blue_3", 2);
        deck.put("blue_4", 2);
        deck.put("blue_5", 2);
        deck.put("blue_6", 2);
        deck.put("blue_7", 2);
        deck.put("blue_8", 2);
        deck.put("blue_9", 2);
        deck.put("blue_draw2", 2);
        deck.put("blue_skip", 2);
        deck.put("blue_reverse", 2);
        deck.put("red_0", 1);
        deck.put("red_1", 2);
        deck.put("red_2", 2);
        deck.put("red_3", 2);
        deck.put("red_4", 2);
        deck.put("red_5", 2);
        deck.put("red_6", 2);
        deck.put("red_7", 2);
        deck.put("red_8", 2);
        deck.put("red_9", 2);
        deck.put("red_draw2", 2);
        deck.put("red_skip", 2);
        deck.put("red_reverse", 2);
        deck.put("yellow_0", 1);
        deck.put("yellow_1", 2);
        deck.put("yellow_2", 2);
        deck.put("yellow_3", 2);
        deck.put("yellow_4", 2);
        deck.put("yellow_5", 2);
        deck.put("yellow_6", 2);
        deck.put("yellow_7", 2);
        deck.put("yellow_8", 2);
        deck.put("yellow_9", 2);
        deck.put("yellow_draw2", 2);
        deck.put("yellow_skip", 2);
        deck.put("yellow_reverse", 2);
        deck.put("green_0", 1);
        deck.put("green_1", 2);
        deck.put("green_2", 2);
        deck.put("green_3", 2);
        deck.put("green_4", 2);
        deck.put("green_5", 2);
        deck.put("green_6", 2);
        deck.put("green_7", 2);
        deck.put("green_8", 2);
        deck.put("green_9", 2);
        deck.put("green_draw2", 2);
        deck.put("green_skip", 2);
        deck.put("green_reverse", 2);
        deck.put("wild", 4);
        deck.put("wild4", 4);
    }

    private void dealCards() {

    }

    private void displayCards() {
        // get the linear layout of p1's cards
        LinearLayout p1Cards = (LinearLayout) findViewById(R.id.player_1_cards);

        // remove everything in layout
        p1Cards.removeAllViews();

        // go through all of p1's cards and add them to the layout
        for (String card : player1Cards) {
            ImageButton ib = new ImageButton(this);
            int resId = getResources().getIdentifier(card, "drawable",
                    GameActivity.this.getPackageName());
            ib.setImageResource(resId);
        }
    }
}
