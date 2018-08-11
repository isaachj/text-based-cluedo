# text-based-cluedo
An assignment for SWEN225 at Victoria University

# Assignment Specs
You are to implement a prototype of program allowing people to play a version of the board game
Cluedo on a desktop computer. 

# The Rules
Every character, weapon and room is represented by a card in the game. Before the game starts, one
character, one weapon, and one room card are blindly selected at random. This selection represents
the murder circumstances, i.e., the “solution” that players need to figure out during game play. The
respective cards go into an envelope to be hidden from view.

The remaining weapon, room and character cards are then combined into one stack, shuffled and then
dealt facedown evenly to players. Some players may end up with more cards than others.
3 Players then take it in turns to roll two dice and move their character token a corresponding number
(sum of the dice values) of squares. Diagonal movement is not allowed and no space may be used
twice during one turn. When a player enters a room, they do not need to use any remaining moves
they have left. They may then hypothesise about the murder circumstances by making a suggestion
which comprises the room they are in, a character and a weapon. If the character and weapon named
in the accusation are not in that room yet, they are now moved into the room.

When a suggestions is made, each player, in a clockwise fashion, starting from the current player,
attempts to refute the suggestion. A suggestion is refuted by producing a card that matches one of
the named murder circumstances (as such a card cannot be in the solution envelope). A refutation
card is only shown to the player that made the suggestion. If a player has multiple refutation cards,
it is their choice which one they pick. If no player can produce a refutation, the named murder
circumstances are a potential solution candidate that may or may not be used to make an accusation
later on (by any player).

An accusation comprises a character, a weapon, and a room (which can be any room, not just the
one the player making the accusation may be in). If the accusation made by a player exactly matches
the actual murder circumstances (only the accusing player is allowed to see the solution) the player
wins, otherwise the player is excluded from making further suggestions or accusations. This means
the player will continue to refute suggestions by others but cannot win the game anymore.
