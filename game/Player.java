package game;
import java.util.ArrayList;

import game.cards.*;

public class Player {
    private String color;
    ArrayList<Card> playHand;
    private int points;

    public Player(String color) {
        this.color = color;
        this.playHand = new ArrayList<>();
        this.points = 5;
    }

    public String getColor() {
        return color;
    }

    public ArrayList<Card> getPlayHand() {
        return playHand;
    }

    public int getPoints() {
        return points;
    }

    public String setPoints(int points) {
        this.points += points;
        return "Player " + color + " now has " + this.points + " points.";
    }

    public ArrayList<Card> addCardToHand(Card card) {
        playHand.add(card);
        return playHand;
    }
}