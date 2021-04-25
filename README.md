# Blackjack

Author: Santeri Hiltunen

Single-player, single-deck blackjack implementation in JavaFX.  

Complete game and payment logic except for splitting and non-optimal strategy rules such as insurance.  

Blackjack pays 3 to 2 and dealer hits on soft 17.

## Implementation

Main-method contains the JavaFX application entry point and sets the stage and scene.

BorderPane root node is created and a menubar is set at the top. Root node is passed to the created GameView-object.

GameView-constructor stores the starting money in a new GameState-object and creates a new BorderPane to display game view nodes.
GameState-object creates a new Deck-object, which consists of 52 playing cards, and then shuffles it. GameState initializes an empty array list
for player and dealer to hold their cards. Score keeping arrays are also created for both. 

When a card is dealt, a card object is removed from the deck and added to dealer's/player's list of current cards.
After a card has been dealt, or a result has been reached, the method showGameView() is again called to render
the cards, scores and other changed information on the screen.


A new GameState-object is created and the old variable is overwritten at the end of each round.
This discards the old deck and round-specific values, but keeps track of the player's money amount for future rounds.



