package com.example.demouser.seven;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by demouser on 1/19/17.
 */

public class SevenGame {

    private String id;
    private String player1;
    private String player2;

    private String lastCard;
    private boolean player1Turn;
    private List<String> player1Cards;
    private List<String> player2Cards;
    private List<String> deck;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SevenGame() {
    }

    public SevenGame(String player1, String player2, boolean player1Turn) {
        this.id = parseEmail(player1) + "-" + parseEmail(player2);
        this.player1 = player1;
        this.player2 = player2;
        this.player1Turn = player1Turn;

        deck = GameActivity.getNewDeck();

        message = player1Turn ? player1 : player2;
        message += "'s turn!";

        // add new cards
        player1Cards = new ArrayList<>();
        player2Cards = new ArrayList<>();
        Random random = new Random();
        int r;
        for (int i = 0; i < GameActivity.NUM_CARDS; i++) {
            r = random.nextInt(deck.size());
            player1Cards.add(deck.remove(r));
            r = random.nextInt(deck.size());
            player2Cards.add(deck.remove(r));
        }

        String card;
        do {
            r = random.nextInt(deck.size());
            card = deck.remove(r);
        }
        while (!card.contains("_") || GameActivity.SPECIAL_CARDS.contains
                (card.substring(card.indexOf("_") + 1)));
        lastCard = card;
    }

    public static String parseEmail(String email) {
        return email.replace(".", "-")
                .replace("#", "-")
                .replace("$", "-")
                .replace("[", "-")
                .replace("]", "-");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getLastCard() {
        return lastCard;
    }

    public void setLastCard(String lastCard) {
        this.lastCard = lastCard;
    }

    public boolean isPlayer1Turn() {
        return player1Turn;
    }

    public void setPlayer1Turn(boolean player1Turn) {
        this.player1Turn = player1Turn;
    }

    public List<String> getPlayer1Cards() {
        return player1Cards;
    }

    public void setPlayer1Cards(List<String> player1Cards) {
        this.player1Cards = player1Cards;
    }

    public List<String> getPlayer2Cards() {
        return player2Cards;
    }

    public void setPlayer2Cards(List<String> player2Cards) {
        this.player2Cards = player2Cards;
    }

    public List<String> getDeck() {
        return deck;
    }

    public void setDeck(List<String> deck) {
        this.deck = deck;
    }
}
