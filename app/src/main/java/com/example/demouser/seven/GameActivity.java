package com.example.demouser.seven;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private final static int NUM_CARDS = 7;

    private ArrayList<String> player1Cards;
    private ArrayList<String> player2Cards;
    private String currentCard;
    private Map<String, Integer> deck;
    private boolean p1Turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // initialize deck
        initVariables();
        initDeck();

        // deal and display cards
        dealCards();
        displayCards();
    }

    private void initVariables() {
        player1Cards = new ArrayList<>();
        player2Cards = new ArrayList<>();
        currentCard = "";
    }

    private boolean canPutCard(String cardChosen) {
        String[] currentParts = currentCard.split("-");
        String[] chosenParts = cardChosen.split("-");
        String currentCardColor = currentParts[0];
        String currentCardNum = currentParts[1];
        String chosenColor = chosenParts[0];
        String chosenNum = chosenParts[1];
        // compare the current card to the card chosen
        if (currentCardColor.equals(chosenColor)) {
            if (chosenNum.equals("skip") || chosenNum.equals("reverse")) {
                // same turn
                // return true
                return true;
            } else if (chosenNum.equals("draw2")) {

            } else {
                changeTurn();
                return true;
            }
        } else if (cardChosen.equals("wild")) {
            // give the user a window to choose color
        } else {

        }
        return false;
    }

    private void changeTurn() {
        p1Turn = !p1Turn;
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
        for (int i = 0; i < NUM_CARDS; i++) {
            player1Cards.add(getRandomCard());
            player2Cards.add(getRandomCard());
        }
    }

    private String getRandomCard() {
        int value = 0;
        String randomKey = "";

        while (value <= 0) {
            Random random = new Random();
            List<String> keys = new ArrayList<String>(deck.keySet());
            randomKey = keys.get(random.nextInt(keys.size()));
            value = deck.get(randomKey);
        }

        deck.put(randomKey, value - 1);

        return randomKey;
    }

    private void displayCards() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 160);


        // get the linear layout of p1's cards
        LinearLayout p1Cards = (LinearLayout) findViewById(R.id.player_1_cards);

        // remove everything in layout
        p1Cards.removeAllViews();

        // go through all of p1's cards and add them to the layout
        for (final String card : player1Cards) {
            ImageButton ib = new ImageButton(this);
            int resId = getResources().getIdentifier(card, "drawable",
                    GameActivity.this.getPackageName());
            ib.setScaleType(ImageView.ScaleType.FIT_XY);
            ib.setImageResource(resId);
            ib.setLayoutParams(layoutParams);
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playCard(card);
                }
            });
            p1Cards.addView(ib);
        }

        // get the linear layout of p1's cards
        LinearLayout p2Cards = (LinearLayout) findViewById(R.id.player_2_cards);

        // remove everything in layout
        p2Cards.removeAllViews();

        // go through all of p1's cards and add them to the layout
        for (String card : player2Cards) {
            ImageView ib = new ImageView(this);
            ib.setImageResource(R.drawable.card_back);
            ib.setLayoutParams(layoutParams);
            ib.setLayoutParams(layoutParams);
            p2Cards.addView(ib);
        }
    }

    private void playCard(String card) {
        if (canPutCard(card)) {
            if (p1Turn) {
                player1Cards.remove(player1Cards.indexOf(card));
                currentCard = card;
                changeTurn();
            } else {
                player2Cards.remove(player2Cards.indexOf(card));
                currentCard = card;
                changeTurn();
            }
        } else {
            // display message
        }
    }
}
