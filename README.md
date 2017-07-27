# quandary-sim
Simulator for the board game [Quandary](https://boardgamegeek.com/boardgame/12319/quandary).

## About
I found a copy of the 1970 board game Quandary when I was on holiday. It's a game played on a grid of coloured squares.

![the playing board for Quandary](https://github.com/Kimbsy/quandary/blob/master/src/main/resources/IMG_20160917_155731.jpg?raw=true)

Players sit opposite one another and each control four pawns. The pawns are initially placed randomly (determined by a deck of numbered cards) along the numbered edges closest to the players.
Only one pawn can occupy a space at any one time.

Players take turns to move one of their pawns one space forward (either straight forward or diagonally), pawns can only move into a square if an opponent pawn has a square of the same colour in front of it.

The colours are:
* BLUE
* ORANGE
* GREY
* PINK
* YELLOW
* PURPLE
* GREEN
* WHITE

If a player cannot move any pawns (because there are no moves allowed by opposing pawns) they end their turn without moving a pawn. If neither player can move a pawn the game ends in a draw.

The first player to have a pawn reach a space on the opposite edge wins the game.
