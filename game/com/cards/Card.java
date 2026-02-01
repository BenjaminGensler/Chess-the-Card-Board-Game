package com.cards;
import com.Board;

public class Card {
    private String description;
    private String type;
    private int cost;

    public Card(String type, String description, int cost) {
        this.type = type;
        this.description = description;
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    // Function that plays the card (to be overridden by subclasses)
    public void playCard(Board board) {
        return;
    }
}