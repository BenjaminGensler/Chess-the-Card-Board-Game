package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.pieces.Bishop;
import com.pieces.King;
import com.pieces.Knight;
import com.pieces.Pawn;
import com.pieces.Piece;
import com.pieces.Queen;
import com.pieces.Rook;

import com.cards.*;

public class GameController {
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Map<String, Player> colorToPlayer;
    private String currentTurn; // "white" or "black"
    private List<Card> deck;
    private Shop shop;
    private Scanner scanner;
    private Boolean gameOver;

    public GameController() {
        // Create a Scanner object
        scanner = new Scanner(System.in);

        this.board = new Board();
        this.whitePlayer = new Player("white");
        this.blackPlayer = new Player("black");
        this.colorToPlayer = new HashMap<>();
        colorToPlayer.put("white", whitePlayer);
        colorToPlayer.put("black", blackPlayer);
        this.currentTurn = "white";
        this.gameOver = false;

        setupBoard();
        board.printBoard();

        setupDeck();

        startGame();
    }

    private void setupBoard() {
        // // Adds white/black Pawns
        // for(int i = 0; i < 8; i++) {
        //     board.placePiece(6, i, new Pawn("black"));
        //     board.placePiece(1, i, new Pawn("white"));
        // }

        // Adds Rooks
        board.placePiece(0, 0, new Rook("white"));
        board.placePiece(0, 7, new Rook("white"));
        board.placePiece(7, 0, new Rook("black"));
        board.placePiece(7, 7, new Rook("black"));

        // Add Knights
        board.placePiece(0, 1, new Knight("white"));
        board.placePiece(0, 6, new Knight("white"));
        board.placePiece(7, 1, new Knight("black"));
        board.placePiece(7, 6, new Knight("black"));

        // Add Bishops
        board.placePiece(0, 2, new Bishop("white"));
        board.placePiece(0, 5, new Bishop("white"));
        board.placePiece(7, 2, new Bishop("black"));
        board.placePiece(7, 5, new Bishop("black"));

        //Add Queens
        board.placePiece(0, 3, new Queen("white"));
        board.placePiece(7, 3, new Queen("black"));

        //Add Kings
        board.placePiece(0, 4, new King("white"));
        board.placePiece(7, 4, new King("black"));

        // Checkmate testing setup
        board.placePiece(0, 0, new King("white"));
        board.placePiece(1, 2, new Rook("black"));
        board.placePiece(2, 3, new Rook("black"));
        board.placePiece(7, 7, new King("black"));
        board.placePiece(6, 5, new Pawn("white"));
        board.placePiece(1, 4, new Pawn("black"));
        board.placePiece(3, 4, new Bishop("white"));
        board.placePiece(5, 4, new Rook("white"));
    }

    private void setupDeck() {
        deck = new ArrayList<>();
        deck.add(new PawnUpgrade());
        deck.add(new Skip());
        deck.add(new Kamikaze());
        // deck.add(new TeleportKing());
        deck.add(new BuildQueen());

        // For testing purposes
        whitePlayer.addCardToHand(deck.get(3));
    }

    public void startGame() {
        while (gameOver == false) {
            board.printBoard();
            Player currentPlayer = colorToPlayer.get(currentTurn);
            // Get move from currentPlayer (input handling)
            // Validate and make move
            // Switch turn
            currentTurn = currentTurn.equals("white") ? "black" : "white";

            while(true) {
                // System.out.println("Main Loop 1");
                // Prompt for and read a string
                System.out.println("Select an action:");
                System.out.println("1. Move piece");
                System.out.println("2. Play card");
                System.out.println("3. Purchase card");
                String userInput = scanner.nextLine();
    
                // Move a piece
                if(userInput.equals("1")) {
                    System.out.print("Enter your next move: ");
                    String userMove = scanner.nextLine();
    
                    // System.out.println("Main Loop 2");
    
                    //convert move to function correct input
                    int fromY = (int) userMove.charAt(0) - 'a';
                    int fromX = Character.getNumericValue(userMove.charAt(1)) - 1;
                    int toY = (int) userMove.charAt(2) - 'a';
                    int toX = Character.getNumericValue(userMove.charAt(3)) - 1;
    
                    // System.out.println("Main Loop 3");
    
                    Piece movingPiece = board.getPiece(fromX, fromY);
                    if (!movingPiece.getColor().equals(board.currentPlayer)) {
                        System.out.println("It is " + board.currentPlayer + "'s turn.");
                        continue;
                    }
    
                    String previousColor = board.currentPlayer;
    
                    // Simulate a move
                    board.movePiece(fromX, fromY, toX, toY);
    
                    if(previousColor != board.currentPlayer) {
                        // Add points for capturing pieces
                        Player capturingPlayer = previousColor.equals("white") ? blackPlayer : whitePlayer;
                        Piece capturedPiece = board.getPiece(toX, toY);
                        capturingPlayer.setPoints(capturedPiece.getPoints());
    
                        System.out.println("Player " + capturingPlayer.getColor() + " captured a " + capturedPiece.getType() + " and earned " + capturedPiece.getPoints() + " points!");
                        System.out.println("Player " + capturingPlayer.getColor() + " now has " + capturingPlayer.getPoints() + " points.");
    
                        Player losingPlayer = previousColor.equals("white") ? blackPlayer : whitePlayer;
                        losingPlayer.setPoints(1);
    
                        System.out.println("Player " + losingPlayer.getColor() + " lost a piece and lost 1 point.");
                        System.out.println("Player " + losingPlayer.getColor() + " now has " + losingPlayer.getPoints() + " points.");
                    }

                    // Check if game is over
                    isGameOver(fromX, fromY, toX, toY);
                }
    
                // Play a card
                else if(userInput.equals("2")) {
                    // Check if the current player has any cards
                    if(currentPlayer.getPlayHand().isEmpty()) {
                        System.out.println("You have no cards to play.");
                        continue;
                    }
    
                    System.out.println(board.currentPlayer + "'s Hand:");
                    for (int i = 0; i < currentPlayer.getPlayHand().size(); i++) {
                        Card card = currentPlayer.getPlayHand().get(i);
                        System.out.println((i+1) + ". " + card.getType());
                        System.out.println(card.getDescription());
                    }
    
                    Scanner cardScanner = new Scanner(System.in);
                    System.out.print("Select a card to play: ");
                    int cardInput = cardScanner.nextInt();
    
                    if(cardInput < 1 || cardInput > currentPlayer.getPlayHand().size()) {
                        System.out.println("Invalid card selection.");
                        continue;
                    }
    
                    // Get the selected card
                    Card selectedCard = currentPlayer.getPlayHand().get(cardInput - 1);
    
                    System.out.println("You played: " + selectedCard.getType());
    
                    // Play the selected cards playCard() function
                    selectedCard.playCard(board);
    
                    // Delete the card from the players hand
                    currentPlayer.getPlayHand().remove(cardInput - 1);
                }
    
                // Purchase a card
                else if(userInput.equals("3")) {
                    Boolean shopAvailable =  shop.displayShop();
    
                    if(shopAvailable) {
                        Scanner shopScanner = new Scanner(System.in);
                        System.out.print("Select a card to purchase (1-3): ");
                        int shopInput = shopScanner.nextInt();
    
                        Card purchasedCard = shop.buyCard(shopInput, currentPlayer.getPoints());
                        if (purchasedCard != null) {
                            currentPlayer.addCardToHand(purchasedCard);
                            // Deduct points from the player
                            currentPlayer.setPoints(-purchasedCard.getCost());
                        }
                    }
                }
                // Invalid input
                else {
                    System.out.println("Invalid action. Please select 1, 2, or 3.");
                    continue;
                }
    
                // System.out.println("Main Loop 4");
    
                board.printBoard();
    
                System.out.println("Current Player: " + board.currentPlayer);
            }
        }
        System.out.println("Game over!");
    }

    private boolean isGameOver(int fromX, int fromY, int toX, int toY) {
        Player currentPlayer = colorToPlayer.get(currentTurn);

        //check if king is in check after move
        if(board.isKingInCheck(board.getPiece(toX, toY).getColor())) {
                    
            // returns pieces back to original position if king is in check
            System.out.println("You can't move there, King is in check!");
            board.placePiece(fromX, fromY, board.getPiece(toX, toY));
            board.placePiece(toX, toY, null);
            return false;
        }

        // Check for checkmate
        if(board.isCheckmate(board.getPiece(toX, toY).getColor().equals("white") ? "black" : "white")) {
            board.printBoard();
            
            // check if player in checkmate has a Teleport King card
            board.currentPlayer = board.getPiece(toX, toY).getColor().equals("white") ? "black" : "white";
            System.out.println(board.currentPlayer + " is in checkmate! Do you want to use your teleport King card?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            String choice = scanner.nextLine();
            if(choice.equals("1")) {

                int cardInput = 0;

                for(Card card : currentPlayer.getPlayHand()) {
                    if(card.getType().equals("Teleport King")) {
                        cardInput = currentPlayer.getPlayHand().indexOf(card) + 1;
                        break;
                    }
                }
                // get the card from the players hand
                Card selectedCard = currentPlayer.getPlayHand().get(cardInput - 1);
 
                System.out.println("You played: " + selectedCard.getType());

                // Play the teleport king card
                selectedCard.playCard(board);

                // Delete the card from the players hand
                currentPlayer.getPlayHand().remove(cardInput - 1);

            }
            if(!choice.equals("2")) {
                System.out.println("Invalid choice. Proceeding without using the card.");
                return true;
            }

            System.out.println("Checkmate! " + (board.getPiece(toX, toY).getColor().equals("white") ? "White" : "Black") + " wins!");
            System.exit(0);
            return true;
        }

        // Switch player
        board.switchPlayer();
        return false;
    }
}