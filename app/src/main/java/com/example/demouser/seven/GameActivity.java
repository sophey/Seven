package com.example.demouser.seven;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class GameActivity extends AppCompatActivity {
    private ArrayList<String> player1Deck;
    private ArrayList<String> player2Deck;
    private String currentCard;
    private Map<String,Integer> deck;
    private boolean p1Turn;

    private boolean canPutCard( String cardChosen){
        String[] currentParts = currentCard.split("-");
        String[] chosenParts = cardChosen.split("-");
        String currentCardColor = currentParts[0];
        String currentCardNum = currentParts[1];
        String chosenColor = chosenParts[0];
        String chosenNum = chosenParts[1];
        // compare the current card to the card chosen
        if(currentCardColor.equals(chosenColor)){
            if(chosenNum.equals("skip") || chosenNum.equals("reverse")){
                // same turn
                // return true
                return true;
            }

            else if(chosenNum.equals("draw2")){

                // other player draws 2
                drawCard(2);
                changeTurn();
                return true;

            }
            else{
                changeTurn();
                return true;
            }

        }
        else if(cardChosen.equals("wild")){


            // give the user a window to choose color
            changeTurn();

            return true;
        }

        else if(cardChosen.equals("wild4")){

            // window to choose color
            //other player draw 4
            drawCard(4);
            changeTurn();
            return true;
        }
        else{


            // message pop up? "can't put that card down bro"
            return false;
        }

    }

    private void changeTurn(){
        if(p1Turn){
            p1Turn = false;
        }
        else{
            p1Turn = true;
        }
    }

    private void drawCard(int numCards){
        for(int i = numCards; i <= 0; i--){
           // getRandomCard();
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

//        ArrayList<String> player1Cards =
//
//        for `()
    }
}
