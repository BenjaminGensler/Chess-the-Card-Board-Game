package game.cards;
import game.pieces.*;
import game.Board;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Shop {
    // Arrays of available cards in the shop
    private List<Card> availableCards;

    public Shop() {
        this.availableCards = new ArrayList<>(Arrays.asList(
            new PawnUpgrade(),
            new PawnUpgrade()
        ));

        // Shuffle the availableCards list at the start
        Collections.shuffle(availableCards);
    }

    public Boolean displayShop() {
        if(availableCards.isEmpty()) {
            System.out.println("The shop is currently out of cards.");
            return false;
        }

        System.out.println("Welcome to the Shop! Here are the available cards:");
        int count = 1;
        for (Card card : availableCards) {
            if (card != null && count <= 3) {
                System.out.println(count + ". " + card.getType() + ": " + card.getDescription());
                System.out.println("Cost: " + card.getCost());
                count++;
            }
        }
        return true;
    }


    public Card buyCard(int choice, int playerPoints) {
         if(availableCards.isEmpty()) {
            System.out.println("The shop is currently out of cards.");
            return null;
        }
        if (choice < 1 || choice > 3) {
            System.out.println("Invalid choice. Please select a valid card number.");
            return null;
        }
        if(playerPoints < availableCards.get(choice - 1).getCost()) {
            System.out.println("You do not have enough points to purchase this card.");
            return null;
        }

        Card purchasedCard = availableCards.get(choice - 1);
        System.out.println("You purchased: " + purchasedCard.getType());
        // Remove the card from the available cards
        availableCards.remove(choice - 1);

        return purchasedCard;
    }

}