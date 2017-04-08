# quandary-sim
Simulator for the board game [Quandary](https://boardgamegeek.com/boardgame/12319/quandary).

## About
I found a copy of the 1970 board game Quandary when I was on holiday. It's a game played on a grid of coloured squares.

![the playing board for Quandary][board]

Players sit opposite one another and each control four pawns. The pawns are initially placed randomly (determined by a deck of numbered cards) along the numbered edges closest to the players.
Only one pawn can occupy a space at any one time.

Players take turns to move one of their pawns one space forward (either straight forward or diagonally), pawns cannot move into a square if an opponent pawn has a square of the same colour in front of it.

The colours are:
* BLUE
* ORANGE
* GREY
* PINK
* YELLOW
* PURPLE
* GREEN
* WHITE

If a player cannot move any pawns (because all possible moves have been blocked by other pawns) they end their turn without moving a pawn.

The first player to have a pawn reach a space on the opposite egde wins the game.

[board]: https://github.com/Kimbsy/quandary-sim/blob/master/src/main/resources/IMG_20160917_155731.jpg?raw=true "This was the copy I played." 
