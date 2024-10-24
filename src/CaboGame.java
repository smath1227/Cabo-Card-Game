//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P05 CABO
// Course:   CS 300 Fall 2024
//
// Author:   Sid Mathur
// Email:    smathur24@wisc.edu
// Lecturer: Blerina Gkotse
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    Artur Sobol
// Partner Email:   arsobol@wisc.edu
// Partner Lecturer's Name: Hobbes LeGault
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   _X_ Write-up states that pair programming is allowed for this assignment.
//   _X_ We have both read and understand the course Pair Programming Policy.
//   _X_ We have registered our team prior to the team registration deadline.
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         N/A
// Online Sources:  N/A
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;

import processing.core.PApplet;

import javax.smartcardio.Card;

/**
 * The CaboGame class implements the main game logic for the card game CABO. It manages the deck,
 * discard pile, players, game state, and user interactions.
 */
public class CaboGame extends PApplet {

  /**
   * Enum representing the different action states in the game (e.g., swapping cards, peeking,
   * spying, switching). This allows us to easily restrict the possible values of a variable.
   */
  private enum ActionState {
    NONE, SWAPPING, PEEKING, SPYING, SWITCHING
  }


  private ActionState actionState = ActionState.NONE;

  // provided data fields for tracking the players' moves through the game
  private ArrayList<String> gameMessages = new ArrayList<>();
  private Deck deck;
  private Deck discard;
  private BaseCard drawnCard;
  private Player[] players;
  private int caboPlayer;
  private int currentPlayer;
  Button[] buttons = new Button[5];
  private boolean gameOver;
  private int selectedCardFromCurrentPlayer;

  /**
   * Launch the game window; PROVIDED. Note: the argument to PApplet.main() must match the name of
   * this class, or it won't run!
   *
   * @param args unused
   */
  public static void main(String[] args) {
    PApplet.main("CaboGame");
  }

  /**
   * Sets up the initial window size for the game; PROVIDED.
   */
  @Override
  public void settings() {
    size(1000, 800);
  }

  /**
   * Sets up the game environment, including the font, game state, and game elements.
   */
  @Override
  public void setup() {
    //Set processing
    textFont(createFont("Arial", 16));
    Deck.processing = this;
    BaseCard.processing = this;
    Button.processing = this;

    //creates deck and discard
    this.deck = new Deck(Deck.createDeck());
    this.discard = new Deck(new ArrayList<BaseCard>());
    this.selectedCardFromCurrentPlayer = -1;

    //creates and initializes players array
    players = new Player[] {new Player("Cyntra", 0, false), new Player("Avalon", 1, true),
        new Player("Balthor", 2, true), new Player("Ophira", 3, true)};

    //creates and initializes the button array
    buttons[0] = new Button("Draw from Deck", 50, 700, 150, 40);
    buttons[1] = new Button("Swap a Card", 220, 700, 150, 40);
    buttons[2] = new Button("Declare Cabo", 390, 700, 150, 40);
    buttons[3] = new Button("Use Action", 390 + 170, 700, 150, 40);
    buttons[4] = new Button("End Turn", 390 + 170 + 170, 700, 150, 40);


    currentPlayer = 0;
    caboPlayer = -1;
    setGameStatus("Turn for " + players[currentPlayer].getName());

    for (Player player : players) {
      for (int i = 0; i < players.length; i++) {
        player.addCardToHand(deck.drawCard());
      }
    }

    players[0].getHand().setFaceUp(0, true);
    players[0].getHand().setFaceUp(1, true);

    // Checkpoint #3
    System.out.println("Size after dealing: " + deck.size());
    for (Player player : players) {
      System.out.println(player.getName() + "'s hand value: " + player.getHand()
          .calcHand() + ", Card Count: " + player.getHand().size());
    }


    deckCheck();

    // TODO: set up deck and discard pile
    // TODO: set up players array and deal their cards
    // TODO: set up buttons and update their states for the beginning of the game
    // TODO: update the gameMessages log: "Turn for "+currentPlayer.name
  }

  /**
   * Console-only output for verifying the setup of the card objects and the deck containing them
   */
  public void deckCheck() {
    ArrayList<BaseCard> deck;
    deck = Deck.createDeck();
    System.out.println("Deck size: " + deck.size());

    int actionCardCount = 0;
    for (BaseCard card : deck) {
      if (card instanceof ActionCard) {
        actionCardCount++;
      }
    }

    // Checkpoint #1
    System.out.println("Action cards: " + actionCardCount);

    int heartsCount = 0, diamondsCount = 0, clubsCount = 0, spadesCount = 0;
    for (BaseCard card : deck) {
      switch (card.suit) {
        case "Hearts":
          heartsCount++;
          break;
        case "Diamonds":
          diamondsCount++;
          break;
        case "Clubs":
          clubsCount++;
          break;
        case "Spades":
          spadesCount++;
          break;
      }
    }
    System.out.println("Hearts: " + heartsCount);
    System.out.println("Diamonds: " + diamondsCount);
    System.out.println("Clubs: " + clubsCount);
    System.out.println("Spades: " + spadesCount);

    BaseCard kingOfDiamonds = null;
    for (BaseCard card : deck) {
      if (card.getRank() == -1 && card.suit.equals("Diamonds")) {
        kingOfDiamonds = card;
        break;
      }
    }

    if (kingOfDiamonds != null && kingOfDiamonds.getRank() == -1) {
      System.out.println("King of Diamonds found");
    } else {
      System.out.println("King of Diamonds not found");
    }

    updateButtonStates();
  }

  /**
   * Updates the state of the action buttons based on the current game state. Activates or
   * deactivates buttons depending on whether it's the start of a player's turn, a card has been
   * drawn, or the player is an AI.
   */
  public void updateButtonStates() {
    // TODO: if the current player is a computer, deactivate all buttons
    if (players[currentPlayer] instanceof AIPlayer) {
      for (Button b : buttons) {
        b.setActive(false);
      }
    }
    // TODO: otherwise, if no card has been drawn, activate accordingly (see writeup)
    if (drawnCard == null) {
      buttons[0].setActive(true);
      if (caboPlayer == -1) {
        buttons[2].setActive(true);
      } else {
        buttons[2].setActive(false);
      }
    } else {
      buttons[0].setActive(false);
      buttons[2].setActive(false);
      buttons[1].setActive(true);
      buttons[4].setActive(true);
    }

    if (drawnCard instanceof ActionCard) {
      buttons[3].setActive(true);
      buttons[3].setLabel(((ActionCard) drawnCard).getActionType());
    }
    // TODO: otherwise, if a card has been drawn, activate accordingly (see writeup)
  }

  /**
   * Renders the graphical user interface; also handles some game logic for the computer players.
   */
  @Override
  public void draw() {
    background(0, 128, 0);
    this.deck.draw(500, 80, false);
    this.discard.draw(600, 80, true);

    textSize(16);
    fill(255);
    text("Deck:", 520, 60);
    text("Discard Pile:", 644, 60);

    for (int i = 0; i < players.length; i++) {
      text(players[i].getName(), 50, 45 + 150 * i);
      players[i].getHand().draw(60 + 150 * i);
    }

    if (!(players[currentPlayer] instanceof AIPlayer)) {
      for (Button b : buttons) {
        b.draw();
      }
    }

    if (drawnCard != null) {
      drawnCard.draw(500, 500);
    }

    // TODO: draw the players' hands
    // TODO: draw the buttons
    // TODO: show the drawn card, if there is one

    // TODO: if the game is over, display the game over status
    // TODO: handle the computer players' turns
  }

  /**
   * Handles mouse press events during the game. It manages user interactions with buttons (that is,
   * drawing a card, declaring CABO, swapping cards, using action cards) and updates the game state
   * accordingly.
   */
  @Override
  public void mousePressed() {
    // TODO: if game is over or it's the computer's turn, do nothing
    if (gameOver || players[currentPlayer] instanceof AIPlayer) {
      return;
    }
    // TODO: handle button clicks
    if (buttons[0].isActive() || buttons[0].isMouseOver()) {
      drawFromDeck();
    } else if (buttons[1].isActive() || buttons[1].isMouseOver()) {
      actionState = ActionState.SWAPPING;
      setGameStatus("Click a card in your hand to swap it with the drawn card.");
    } else if (buttons[2].isActive() || buttons[2].isMouseOver()) {
      declareCabo();
    } else if (buttons[3].isActive() || buttons[3].isMouseOver()) {
      buttons[3].setLabel("Use Action");
      String actionType = ((ActionCard) drawnCard).getActionType();
      if (actionType.equals("peek")) {
        actionState = ActionState.PEEKING;
        setGameStatus("Click a card in your hand to peek at it.");
      } else if (actionType.equals("spy")) {
        actionState = ActionState.SPYING;
        setGameStatus("Click a card in another player's hand to spy on it.");
      } else if (actionType.equals("swap")) {
        actionState = ActionState.SWAPPING;
        setGameStatus(
            "Click a card from your hand, then a card from another " + "Kingdom's hand to switch.");
      }
    } else if (buttons[4].isActive() || buttons[4].isMouseOver()) {
      nextTurn();
    }
    // handle additional action states (TODO: complete these methods)
    switch (actionState) {
      case SWAPPING -> handleCardSwap();
      case PEEKING -> handlePeek();
      case SPYING -> handleSpy();
      case SWITCHING -> handleSwitch();
      default -> { /* No action to be taken */ }
    }
  }

  ///////////////////////////////////// BUTTON CLICK HANDLERS /////////////////////////////////////

  /**
   * Handles the action of drawing a card from the deck. If the deck is empty, the game ends.
   * Otherwise, the drawn card is displayed in the middle of the table. The game status and button
   * states are updated accordingly.
   */
  public void drawFromDeck() {
    // TODO: if the deck is empty, game over
    if (deck.isEmpty()) {
      gameOver = true;
      return;
    }
    // TODO: otherwise, draw the next card from the deck
    drawnCard = deck.drawCard();
    // TODO: update the gameMessages log: player.name+" drew a card."
    setGameStatus(players[currentPlayer].getName() + " drew a card");
    // TODO: update the button states
    updateButtonStates();
  }

  /**
   * Handles the action of declaring CABO. Updates the game status to show that the player has
   * declared CABO.
   */
  public void declareCabo() {
    // TODO: update the gameMessages log: player.name+" declares CABO!"
    setGameStatus(players[currentPlayer].getName() + " declares CABO!");
    // TODO: set the caboPlayer to the current player's index
    caboPlayer = currentPlayer;
    // TODO: end this player's turn
    nextTurn();
  }

  ///////////////////////////////////// ACTION STATE HANDLERS /////////////////////////////////////

  /**
   * This method runs when the human player has chosen to SWAP the drawn card with one from their
   * hand. Detect if the mouse is over a card from the currentPlayer's hand and, if it is, swap the
   * drawn card with that card.
   *
   * If the mouse is not currently over a card from the currentPlayer's hand, this method does
   * nothing.
   */
  public void handleCardSwap() {
    // TODO: find a card from the current player's hand that the mouse is currently over
    int index = players[currentPlayer].getHand().indexOfMouseOver();
    // TODO: swap that card with the drawnCard
    // TODO: add the swapped-out card from the player's hand to the discard pile
    discard.addCard(players[currentPlayer].getHand().swap(drawnCard, index));
    // TODO: update the gameMessages log: "Swapped the drawn card with card "+(index+1)+" in the hand."
    setGameStatus("Swapped the drawn card with card " + (index + 1) + " in the hand.");
    // TODO: set the drawnCard to null and the actionState to NONE
    drawnCard = null;
    actionState = ActionState.NONE;
    // TODO: set all buttons except End Turn to inactive
    buttons[0].setActive(false);
    buttons[1].setActive(false);
    buttons[2].setActive(false);
    buttons[3].setActive(false);
    buttons[4].setActive(true);
    // TODO: uncomment this code to erase all knowledge of the card at that index from the AI
    // (you may need to adjust its indentation and/or change some variables)
    
    AIPlayer AI;
    for (int j = 1; j < players.length; ++j) {
      AI = (AIPlayer) players[j];
      AI.setCardKnowledge(0, index, false);
    }
  }

  /**
   * Handles the action of peeking at one of your cards. The player selects a card from their own
   * hand, which is then revealed (set face-up).
   *
   * If the mouse is not currently over a card from the currentPlayer's hand, this method does
   * nothing.
   */
  public void handlePeek() {
    // TODO: find a card from the current player's hand that the mouse is currently over
    int index = players[currentPlayer].getHand().indexOfMouseOver();
    // TODO: set that card to be face-up
    players[currentPlayer].getHand().setFaceUp(index, true);
    // TODO: update the gameMessages log: "Revealed card "+(index+1)+" in the hand."
    setGameStatus("Revealed card "+ (index + 1) + " in the hand");
    // TODO: add the drawnCard to the discard, set drawnCard to null and actionState to NONE
    discard.addCard(drawnCard);
    drawnCard = null;
    actionState = ActionState.NONE;
    // TODO: set all buttons except End Turn to inactive
    buttons[0].setActive(false);
    buttons[1].setActive(false);
    buttons[2].setActive(false);
    buttons[3].setActive(false);
    buttons[4].setActive(true);
  }

  /**
   * Handles the spy action, allowing the current player to reveal one of another player's cards.
   * The current player selects a card from another player's hand, which is temporarily revealed.
   *
   * If the mouse is not currently over a card from another player's hand, this method does
   * nothing.
   */
  public void handleSpy() {
    // TODO: find a card from any player's hand that the mouse is currently over
    BaseCard spied = null;
    String name = null;
    for (Player p : players) {
      name = p.getName();
      for (int i = 0; i < p.getHand().size(); i++)  {
        if (p.getHand().indexOfMouseOver() != -1) {
          spied = p.getHand().cardList.get(i);
          break;
        }
      }
      if (spied != null) {
        break;
      }
    }
    // TODO: if it is not one of their own cards, set it to be face-up
    if (!players[currentPlayer].getHand().cardList.contains(spied) && spied != null) {
      spied.setFaceUp(true);
    }
    // TODO: update the gameMessages log: "Spied on "+player.name+"'s card.";
    setGameStatus("Spied on " + name+"'s card.");
    // TODO: add the drawnCard to the discard, set drawnCard to null and actionState to NONE
    discard.addCard(drawnCard);
    drawnCard = null;
    actionState = ActionState.NONE;
    // TODO: set all buttons except End Turn to inactive
    buttons[0].setActive(false);
    buttons[1].setActive(false);
    buttons[2].setActive(false);
    buttons[3].setActive(false);
    buttons[4].setActive(true);
  }


  /**
   * Handles the switch action, allowing the current player to switch one of their cards with a card
   * from another player's hand.
   *
   * This action is performed in 2 steps, in this order: (1) select a card from the current player's
   * hand (2) select a card from another player's hand
   *
   * If the mouse is not currently over a card, this method does nothing.
   */
  public void handleSwitch() {
    // TODO: add CaboGame instance variable to store the index of the card from the currentPlayer's hand
    // TODO: check if the player has selected a card from their own hand yet
    // TODO: if they haven't: determine which card in their own hand the mouse is over & store it
    // and do nothing else
    if (selectedCardFromCurrentPlayer != -1){
      int index = players[currentPlayer].getHand().indexOfMouseOver();
      selectedCardFromCurrentPlayer = index;
    } else {
      for (Player p : players) {
        if (!p.equals(players[currentPlayer])) {
          int index = p.getHand().indexOfMouseOver();
          if (index != -1) {
            p.getHand().switchCards(selectedCardFromCurrentPlayer, p.getHand(),index);
            setGameStatus("Switched a card with "+p.getLabel());
            break;
          }
        }
      }
      discard.addCard(drawnCard);
      drawnCard = null;
      buttons[0].setActive(false);
      buttons[1].setActive(false);
      buttons[2].setActive(false);
      buttons[3].setActive(false);
      buttons[4].setActive(true);
    }
    // TODO: if they have selected a card from their own hand already:
    // TODO: find a card from any OTHER player's hand that the mouse is currently over
    // TODO: swap the selected card with the card from the currentPlayer's hand
    // TODO: update the gameMessages log: "Switched a card with "+player.name
    // TODO: add the drawnCard to the discard, set drawnCard to null and actionState to NONE
    // TODO: set all buttons except End Turn to inactive

    // TODO: uncomment this code to update the knowledge of the swapped card for the other player
    // (you may need to adjust its indentation and variables)
    
      /*boolean knowledge = ((AIPlayer)players[i]).getCardKnowledge(i, otherPlayerCardIndex);
      ((AIPlayer)players[i]).setCardKnowledge(i, otherPlayerCardIndex,
          ((AIPlayer)players[i]).getCardKnowledge(currentPlayer, currentPlayerCardIndex));
      ((AIPlayer)players[i]).setCardKnowledge(currentPlayer, currentPlayerCardIndex, knowledge);//*/

    // TODO: reset the selected card instance variable to -1
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Advances the game to the next player's turn. Hides all players' cards, updates the current
   * player, checks for game-over conditions, resets action states, and updates the UI button states
   * for the new player's turn.
   */
  public void nextTurn() {
    // TODO: hide all players' cards
    for (Player p : players) {
      for (int i = 0; i < p.getHand().size(); i++)  {
        p.getHand().setFaceUp(i, false);
        }
    }
    // TODO: if there is still an active drawnCard, discard it and set drawnCard to null
    if (drawnCard != null) {
      discard.addCard(drawnCard);
      drawnCard = null;
    }
    // TODO: advance the current player to the next one in the list
    currentPlayer++;
    if (currentPlayer < players.length - 1) {
      currentPlayer++;
    }
    else {
      currentPlayer = 0;
    }
    // TODO: check if the new player is the one who declared CABO (and end the game if so)
    if (currentPlayer == caboPlayer) {
      gameOver = true;
      displayGameOver();
    }
    // TODO: update the gameMessages log: "Turn for "+player.name
    setGameStatus("Turn for " + players[currentPlayer].getName());
    // TODO: reset the action state to NONE
    actionState = ActionState.NONE;
    // TODO: update the button states
    buttons[0].setActive(false);
    buttons[1].setActive(false);
    buttons[2].setActive(false);
    buttons[3].setActive(false);
    buttons[4].setActive(true);
  }

  /**
   * Displays the game-over screen and reveals all players' cards. The method calculates each
   * player's score, identifies the winner, and displays a message about the game's result,
   * including cases where there is no winner.
   *
   * We've provided the code for the GUI parts, but the logic behind this method is still TODO
   */
  public void displayGameOver() {
    // Create a dimmed background overlay
    fill(0, 0, 0, 200);
    rect(0, 0, width, height);
    fill(255);
    textSize(32);
    textAlign(CENTER, CENTER);
    text("Game Over!", (float) width / 2, (float) height / 2 - 150);

    // TODO: reveal all players' cards

    // TODO: calculate and display each player's score
    int yPosition = height / 2 - 100;
    textSize(24);
    // TODO: uncomment to Display a player's score
    //text(player.getName() + "'s score: " + score, (float) width / 2, yPosition);
    yPosition += 30;

    // TODO: check if there is a tie or a specific CABO winner (lowest score wins)
    String winner = null;

    // TODO: display this message if there is no winner
    text("No Winner. The war starts.", (float) width / 2, yPosition + 30);

    // TODO: display this message if there is a winner
    text("Winner: " + winner, (float) width / 2, yPosition + 30);
  }

  /**
   * PROVIDED: Sets the current game status message and updates the message log. If the message log
   * exceeds a maximum number of messages, the oldest message is removed.
   *
   * @param message the message to set as the current game status.
   */
  private void setGameStatus(String message) {
    gameMessages.add(message);
    int MAX_MESSAGES = 15;
    if (gameMessages.size() > MAX_MESSAGES) {
      gameMessages.remove(0); // Remove the oldest message
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  // The 2 methods below this line are PROVIDED in their entirety to run the AIPlayer interactions 
  // with the CABO game. Uncomment them once you are ready to add AIPlayer actions to your game!
  /////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Performs the AI player's turn by drawing a card and deciding whether to swap, discard, or use 
   * an action card.
   * If the AI player draws a card that is better than their highest card, they swap it; otherwise, 
   * they discard it.
   * If the drawn card is an action card, the AI player performs the corresponding action.
   * If the AI player's hand value is low enough, they may declare CABO.
   */
  /*private void performAITurn() {
    AIPlayer aiPlayer = (AIPlayer) players[currentPlayer];
    String gameStatus = aiPlayer.getName() + " is taking their turn.";
    setGameStatus(gameStatus);

    // Draw a card from the deck
    drawnCard = deck.drawCard();
    if (drawnCard == null) {
      gameOver = true;
      return;
    }

    gameStatus = aiPlayer.getName() + " drew a card.";
    setGameStatus(gameStatus);

    // Determine if AI should swap or discard
    int drawnCardValue = drawnCard.getRank();
    int highestCardIndex = aiPlayer.getHighestIndex();
    if (highestCardIndex == -1) {
      highestCardIndex = 0;
    }
    int highestCardValue = aiPlayer.getHand().getRankAtIndex(highestCardIndex);

    // Swap if the drawn card has a lower value than the highest card in hand
    if (drawnCardValue < highestCardValue) {
      BaseCard cardInHand = aiPlayer.getHand().swap(drawnCard, highestCardIndex);
      aiPlayer.setCardKnowledge(aiPlayer.getLabel(), highestCardIndex, true);
      discard.addCard(cardInHand);
      gameStatus = aiPlayer.getName() + " swapped the drawn card with card " + (highestCardIndex + 1) + " in their hand.";
      setGameStatus(gameStatus);
    } else if (drawnCard instanceof ActionCard) {
      // Use the action card
      String actionType = ((ActionCard) drawnCard).getActionType();
      gameStatus = aiPlayer.getName() + " uses an action card: " + actionType;
      setGameStatus(gameStatus);
      performAIAction(aiPlayer, actionType);
      discard.addCard(drawnCard);
    } else {
      // Discard the drawn card
      discard.addCard(drawnCard);
      gameStatus = aiPlayer.getName() + " discarded the drawn card: " + drawnCard;
      setGameStatus(gameStatus);
    }

    // AI may declare Cabo if hand value is low enough
    int handValue = aiPlayer.calcHandBlind();
    if (handValue <= random(13, 21) && caboPlayer == -1) {
      declareCabo();
    }

    // Prepare for the next turn
    drawnCard = null;
    nextTurn();
  }//*/

  /**
   * Performs the specified action for the AI player based on the drawn action card.
   * Actions include peeking at their own cards, spying on another player's card, or switching cards with another player.
   *
   * @param aiPlayer   the AI player performing the action.
   * @param actionType the type of action to perform ("peek", "spy", or "switch").
   */
  /*private void performAIAction(AIPlayer aiPlayer, String actionType) {
    Player otherPlayer = players[0]; // Assuming Player 1 is the human player
    String gameStatus = "";
    switch (actionType) {
      case "peek" -> {
        // AI peeks at one of its own cards
        int unknownCardIndex = aiPlayer.getUnknownCardIndex();
        if (unknownCardIndex != -1) {
          aiPlayer.setCardKnowledge(aiPlayer.getLabel(), unknownCardIndex, true);
          gameStatus = aiPlayer.getName() + " peeked at their card " + (unknownCardIndex + 1);
          setGameStatus(gameStatus);
        }
      }
      case "spy" -> {
        // AI spies on one of the human player's cards
        int spyIndex = aiPlayer.getSpyIndex();
        if (spyIndex != -1) {
          aiPlayer.setCardKnowledge(0, spyIndex, true);
          gameStatus = aiPlayer.getName() + " spied on Player 1's card " + (spyIndex + 1);
          setGameStatus(gameStatus);
        }
      }
      case "switch" -> {
        // AI switches one of its cards with one of the human player's cards
        int aiCardIndex = aiPlayer.getHighestIndex();
        if (aiCardIndex == -1) {
          aiCardIndex = (int) random(aiPlayer.getHand().size());
        }
        int otherCardIndex = aiPlayer.getLowestIndex(otherPlayer);
        if (otherCardIndex == -1)
          otherCardIndex = (int) random(otherPlayer.getHand().size());

        // Swap the cards between AI and the human player
        aiPlayer.getHand().switchCards(aiCardIndex, otherPlayer.getHand(), otherCardIndex);
        boolean preCardKnowledge = aiPlayer.getCardKnowledge(aiPlayer.getLabel(), aiCardIndex);
        aiPlayer.setCardKnowledge(aiPlayer.getLabel(), aiCardIndex, aiPlayer.getCardKnowledge(0, otherCardIndex));
        aiPlayer.setCardKnowledge(0, otherCardIndex, preCardKnowledge);

        gameStatus = aiPlayer.getName() + " switched card " + (aiCardIndex + 1) + " with " + otherPlayer.getName() + "'s " + (otherCardIndex + 1) + ".";
        setGameStatus(gameStatus);
      }
    }
  }//*/

}
