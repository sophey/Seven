package com.example.demouser.seven;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private final static int NUM_CARDS = 7;
    private final static int TOTAL_CARDS = 108;
    private final static String SPECIAL_CARDS = "reverse draw2 skip wild wild4";

    private ArrayList<String> player1Cards;
    private ArrayList<String> player2Cards;
    private String currentCard;
    private Map<String, Integer> deck;
    private boolean p1Turn;
    private int numCards;

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

        ((ImageButton) findViewById(R.id.card_deck)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (p1Turn) {
                    player1Cards.add(getRandomCard());
                } else {
                    player2Cards.add(getRandomCard());
                }
                changeTurn();
                displayCards();
            }
        });
    }

    private void initVariables() {
        player1Cards = new ArrayList<>();
        player2Cards = new ArrayList<>();
        currentCard = "";
        p1Turn = true;
        numCards = TOTAL_CARDS;
    }

    /**
     * Plays the card if it can be played, and returns a whether it is played
     * or not
     *
     * @param cardChosen
     * @return
     */
    private boolean canPutCard(String cardChosen) {

        if (cardChosen.contains("wild")) {
            // show wild card screen
            Intent intent = new Intent(this, WildActivity.class);
            startActivity(intent);

            if (cardChosen.equals("wild4")) { // draw 4
                ArrayList<String> hand = p1Turn ? player2Cards : player1Cards;
                for (int i = 0; i < 4; i++) {
                    hand.add(getRandomCard());
                }
            }

            String color = getIntent().getStringExtra(WildActivity
                    .COLOR_CHANGE);

            ((TextView) findViewById(R.id.messageText)).setText(String.format
                    (getString(R.string.color_change), color));
            return true;
        }

        String[] currentParts = currentCard.split("_");
        String[] chosenParts = cardChosen.split("_");
        String currentCardColor = currentParts[0];
        String currentCardNum = currentParts[1];
        String chosenColor = chosenParts[0];
        String chosenNum = chosenParts[1];
        // compare the current card to the card chosen

        // same color means it can be played
        if (currentCardColor.equals(chosenColor)) {
            if (chosenNum.equals("skip") || chosenNum.equals("reverse")) {
                // same turn so do nothing
            } else if (chosenNum.equals("draw2")) {
                // have other player draw cards
                if (p1Turn) {
                    player2Cards.add(getRandomCard());
                    player2Cards.add(getRandomCard());
                } else {
                    player1Cards.add(getRandomCard());
                    player1Cards.add(getRandomCard());
                }
            } else { // it's a number, do nothing
            }
            return true;
        } else if (currentCardNum.equals(chosenNum)) { // same number
            return true;
        } else {
            return false;
        }
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
        String card = "";
        while (card.equals("")) {
            card = getRandomCard();
            if (card.contains("wild")) {
                card = "";
            } else {
                String[] arr = card.split("_");
                if (SPECIAL_CARDS.contains(arr[1])) {
                    card = "";
                }
            }
        }
        currentCard = card;
    }

    private String getRandomCard() {
        if (numCards == 0) { // add cards back in deck
            initDeck();
            numCards = TOTAL_CARDS;
            // get rid of visible cards
            for (String card : player1Cards) {
                deck.put(card, deck.get(card) - 1);
                numCards--;
            }
            for (String card : player1Cards) {
                deck.put(card, deck.get(card) - 1);
                numCards--;
            }
            deck.put(currentCard, deck.get(currentCard) - 1);
            numCards--;
        }

        int value = 0;
        String randomKey = "";

        while (value <= 0) {
            Random random = new Random();
            List<String> keys = new ArrayList<String>(deck.keySet());
            randomKey = keys.get(random.nextInt(keys.size()));
            value = deck.get(randomKey);
        }

        deck.put(randomKey, value - 1);
        numCards--;

        return randomKey;
    }

    private void displayCards() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout
                .LayoutParams(150, 240);

        // get the linear layout of p1's cards
        LinearLayout p1Cards = (LinearLayout) findViewById(R.id.player_1_cards);

        // remove everything in layout
        p1Cards.removeAllViews();

        // go through all of p1's cards and add them to the layout
        for (final String card : player1Cards) {
            ImageButton ib = new ImageButton(this);
            int resId = getResources().getIdentifier(card, "drawable",
                    GameActivity.this.getPackageName());
            ib.setImageResource(resId);
            ib.setScaleType(ImageView.ScaleType.FIT_XY);
            ib.setLayoutParams(layoutParams);
            ib.setBackgroundResource(0);
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

        layoutParams = new LinearLayout.LayoutParams(75, 120);

        // remove everything in layout
        p2Cards.removeAllViews();

        // go through all of p1's cards and add them to the layout
        for (int i = 0; i < player2Cards.size(); i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(R.drawable.card_back);
            iv.setLayoutParams(layoutParams);
            p2Cards.addView(iv);
        }

        int resId = getResources().getIdentifier(currentCard, "drawable",
                GameActivity.this.getPackageName());
        ((ImageView) findViewById(R.id.current_card)).setImageResource(resId);
    }

    private void playCard(String card) {
        // reset message every time a card is pressed
        ((TextView) findViewById(R.id.messageText)).setText("");

        if (canPutCard(card)) {
            if (p1Turn) {
                player1Cards.remove(player1Cards.indexOf(card));
            } else {
//                player2Cards.remove(player2Cards.indexOf(card));
            }
            changeTurn();
            // if the turn goes back
            if (card.contains("reverse") || card.contains("skip"))
                changeTurn();
            currentCard = card;
            displayCards();
        } else {
            ((TextView) findViewById(R.id.messageText)).setText(R.string
                    .no_placing_card);
        }
    }

}
