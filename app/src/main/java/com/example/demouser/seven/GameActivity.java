package com.example.demouser.seven;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    public final static int NUM_CARDS = 7;
    public final static String SPECIAL_CARDS = "reverse draw2 skip wild wild4";
    private final static int TOTAL_CARDS = 108;
    private static final String TAG = "GAMEACTIVITY";
    private final String ANONYMOUS = "ANONYMOUS";

    private String mUserEmail;
    private GoogleApiClient mGoogleApiClient;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabase;

    private SevenGame game;
    private PlayerState state;

    private Random random = new Random();

    public static List<String> getNewDeck() {
        List<String> deck = new ArrayList<>();
        deck.add("blue_0");
        deck.add("red_0");
        deck.add("yellow_0");
        deck.add("green_0");

        // add duplicates
        for (int i = 0; i < 2; i++) {
            deck.add("blue_1");
            deck.add("blue_2");
            deck.add("blue_3");
            deck.add("blue_4");
            deck.add("blue_5");
            deck.add("blue_6");
            deck.add("blue_7");
            deck.add("blue_8");
            deck.add("blue_9");
            deck.add("blue_draw2");
            deck.add("blue_skip");
            deck.add("blue_reverse");
            deck.add("red_1");
            deck.add("red_2");
            deck.add("red_3");
            deck.add("red_4");
            deck.add("red_5");
            deck.add("red_6");
            deck.add("red_7");
            deck.add("red_8");
            deck.add("red_9");
            deck.add("red_draw2");
            deck.add("red_skip");
            deck.add("red_reverse");
            deck.add("yellow_1");
            deck.add("yellow_2");
            deck.add("yellow_3");
            deck.add("yellow_4");
            deck.add("yellow_5");
            deck.add("yellow_6");
            deck.add("yellow_7");
            deck.add("yellow_8");
            deck.add("yellow_9");
            deck.add("yellow_draw2");
            deck.add("yellow_skip");
            deck.add("yellow_reverse");
            deck.add("green_1");
            deck.add("green_2");
            deck.add("green_3");
            deck.add("green_4");
            deck.add("green_5");
            deck.add("green_6");
            deck.add("green_7");
            deck.add("green_8");
            deck.add("green_9");
            deck.add("green_draw2");
            deck.add("green_skip");
            deck.add("green_reverse");
            deck.add("wild");
            deck.add("wild");
            deck.add("wild4");
            deck.add("wild4");
        }
        return deck;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUserEmail = SevenGame.parseEmail(mFirebaseUser.getEmail());
            configureDatabase();
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /*
                OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        setContentView(R.layout.activity_game);
//
//        // deal and display cards
//        displayCards();

        ((ImageButton) findViewById(R.id.card_deck)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isYourTurn()) {
                    if (game.isPlayer1Turn()) {
                        game.getPlayer1Cards().add(getRandomCard());
                    } else {
                        game.getPlayer2Cards().add(getRandomCard());
                    }
                    changeTurn();
                    displayCards();
                    mFirebaseDatabase.child("games").child(game.getId())
                            .setValue
                            (game);
                }
            }
        });
    }

    private void configureDatabase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();

        mFirebaseDatabase.child("players").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PlayerState newState = dataSnapshot.getValue(PlayerState.class);
                // if both players are ready, start the game
                if (!newState.getEmail().equals(state.getEmail()) &&
                        newState.getStatus() == PlayerStatus.READY &&
                        state.getStatus() == PlayerStatus.READY) {
                    if (state.getEmail().compareTo(newState.getEmail()) < 0)
                        startGame(newState);
                    else {
                        startGameSecondary(newState.getEmail() + "-" + state
                                .getEmail());
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        state = new PlayerState(mUserEmail);
        mFirebaseDatabase.child("players").child(state.getId()).setValue(state);
    }

    public void startGame(final PlayerState newState) {
        game = new SevenGame(state.getEmail(), newState.getEmail(), random
                .nextBoolean());
        mFirebaseDatabase.child("games").child(game.getId()).setValue(game);

        state.setStatus(PlayerStatus.IN_GAME);
        newState.setStatus(PlayerStatus.IN_GAME);

        mFirebaseDatabase.child("players").child(state.getId()).setValue
                (state);

        mFirebaseDatabase.child("players").child(newState.getId()).setValue
                (newState);

        displayCards();

        mFirebaseDatabase.child("games").child(game.getId())
                .addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue(SevenGame.class) != null)
                            game = dataSnapshot.getValue(SevenGame.class);
                        displayCards();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    public void startGameSecondary(String id) {
        mFirebaseDatabase.child("games").child(id)
                .addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue(SevenGame.class) != null)
                            game = dataSnapshot.getValue(SevenGame.class);
                        displayCards();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private String getRandomCard() {
        List<String> deck = game.getDeck();
        if (deck.size() == 0) { // add cards back in deck
            deck = getNewDeck();
            // get rid of visible cards
            for (String card : game.getPlayer1Cards()) {
                deck.remove(deck.indexOf(card));
            }
            for (String card : game.getPlayer2Cards()) {
                deck.remove(deck.indexOf(card));
            }
            deck.remove(deck.indexOf(game.getLastCard()));
        }

        return deck.remove(random.nextInt(deck.size()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String color = data.getStringExtra(WildActivity
                        .COLOR_CHANGE);

                String message = String.format(getString(R.string
                        .color_change), color);
                game.setMessage(message);

                game.setLastCard(color);

                mFirebaseDatabase.child("games").child(game.getId()).setValue
                        (game);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    /**
     * Plays the card if it can be played, and returns a whether it is played
     * or not
     *
     * @param cardChosen
     * @return
     */
    private boolean canPutCard(String cardChosen) {
        String currentCard = game.getLastCard();
        boolean p1Turn = game.isPlayer1Turn();
        List<String> player1Cards = game.getPlayer1Cards();
        List<String> player2Cards = game.getPlayer2Cards();

        if (cardChosen.contains("wild")) {
            // show wild card screen
            Intent intent = new Intent(this, WildActivity.class);
            startActivityForResult(intent, 1);
//            startActivity(intent);

            if (cardChosen.equals("wild4")) { // draw 4
                List<String> hand = p1Turn ? player2Cards : player1Cards;
                for (int i = 0; i < 4; i++) {
                    hand.add(getRandomCard());
                }
            }

            return true;
        }

        String[] currentParts = currentCard.split("_");
        String[] chosenParts = cardChosen.split("_");
        String currentCardColor = currentParts[0];
        String currentCardNum = currentParts.length > 1 ? currentParts[1] : "";
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
            if (chosenNum.equals("draw2")) {
                // have other player draw cards
                if (p1Turn) {
                    player2Cards.add(getRandomCard());
                    player2Cards.add(getRandomCard());
                } else {
                    player1Cards.add(getRandomCard());
                    player1Cards.add(getRandomCard());
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private void changeTurn() {
        game.setPlayer1Turn(!game.isPlayer1Turn());
        String message = game.isPlayer1Turn() ? game.getPlayer1() : game
                .getPlayer2();
        message += "'s turn!";
        game.setMessage(message);
    }

    private void displayCards() {
        if (checkWin()) {
            return;
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout
                .LayoutParams(150, 240);

        // get the linear layout of p1's cards
        LinearLayout p1Cards = (LinearLayout) findViewById(R.id.player_1_cards);

        // remove everything in layout
        p1Cards.removeAllViews();

        // display message
        ((TextView) findViewById(R.id.messageText)).setText(game.getMessage());

        // go through all of p1's cards and add them to the layout
        List<String> player1Cards = isPlayer1() ? game.getPlayer1Cards() :
                game.getPlayer2Cards();
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
        List<String> player2Cards = isPlayer1() ? game.getPlayer2Cards() :
                game.getPlayer1Cards();
        for (int i = 0; i < player2Cards.size(); i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(R.drawable.card_back);
            iv.setLayoutParams(layoutParams);
            p2Cards.addView(iv);
        }

        String currentCard = game.getLastCard();
        if ("yellow red blue green".contains(currentCard))
            return;
        int resId = getResources().getIdentifier(currentCard, "drawable",
                GameActivity.this.getPackageName());
        ((ImageView) findViewById(R.id.current_card)).setImageResource(resId);
    }

    private boolean isPlayer1() {
        return game.getPlayer1().equals(mUserEmail);
    }

    private boolean isYourTurn() {
        return game.isPlayer1Turn() ? game.getPlayer1().equals(mUserEmail) :
                game.getPlayer2().equals(mUserEmail);
    }

    private boolean checkWin() {
        if (game.getPlayer1Cards() == null || game.getPlayer1Cards().size()
                == 0) {
            if (isPlayer1()) {
                Intent intent = new Intent(this, WinActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, LoseActivity.class);
                startActivity(intent);
            }
            return true;
        } else if (game.getPlayer2Cards() == null || game.getPlayer2Cards()
                .size() == 0) {
            if (!isPlayer1()) {
                Intent intent = new Intent(this, WinActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, LoseActivity.class);
                startActivity(intent);
            }
            return true;
        }
        return false;
    }

    private void playCard(String card) {
        // reset message every time a card is pressed
        ((TextView) findViewById(R.id.messageText)).setText("");

        if (isYourTurn() && canPutCard(card)) {
            if (game.isPlayer1Turn()) {
                game.getPlayer1Cards().remove(game.getPlayer1Cards().indexOf
                        (card));
            } else {
                game.getPlayer2Cards().remove(game.getPlayer2Cards().indexOf
                        (card));
            }

            changeTurn();
            // if the turn goes back
            if (card.contains("reverse") || card.contains("skip") || card
                    .contains("wild4"))
                changeTurn();
            game.setLastCard(card);
            displayCards();
        } else {
            ((TextView) findViewById(R.id.messageText)).setText(R.string
                    .no_placing_card);
        }
        mFirebaseDatabase.child("games").child(game.getId()).setValue(game);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including
        // Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast
                .LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                mFirebaseDatabase.child("players").child(state.getId())
                        .removeValue();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
