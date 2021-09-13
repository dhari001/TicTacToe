public class MenaceGame {

TicTacToe game;
int[] boardOdds;
int totalOdds;
int currentPosition;

/**
 * A menace game keeps an instance of a TicTacToe game
 * instead of extending it. We can chat about why
 * during our Monday meetups.
 */

/* default constructor?
   public MenaceGame(){
        game = new TicTacToe(3, 3, 3);
        resetOdds();
   }
 */

public MenaceGame(TicTacToe aGame) {
        game = aGame;
        resetOdds();
}


/**
 * The game is over.
 * If you won, then add three beads to the current position's odds.
 * If you tied, only add 1 bead
 * If you lost, then remove a bead.
 *
 * Note: You can never have 0 beads in a game
 *       and do not forget to correctly update your totalOdds
 * @param outcome The outcome of the game.
 */
public void setOutcome(GameOutcome outcome) {

        switch (outcome) {
        case WIN:
                boardOdds[currentPosition-1] += 3;
                totalOdds += 3;
                break;
        case LOSE:
                if (totalOdds == 1) {
                        game.cloneUndoPlay(game.lastPlayedPosition);
                } else {
                        boardOdds[currentPosition-1] -= 1;
                        totalOdds -= 1;
                }
                break;
        case DRAW:
                boardOdds[currentPosition-1] += 1;
                totalOdds += 1;
                break;
        default:
                break;
        }
}


/**
 * Roll the dice, and set the current position
 * If no positions are available, then return 0
 * (which is an invalid position)
 *
 * @return The current positionThe random number rolled.
 */
public int pickCurrentPosition() {
        if (fullBoard()) {
                return 0;
        }

        currentPosition = calculatePosition(rollDice());
        return currentPosition;
}

/**
 * Generate a random number.
 *
 * Consider the following 3x3 board.
 *
 *    | X |
 * -----------
 *  O |   |
 * -----------
 *    |   |
 *
 * If we had the following beads (I left the Xs and Os off)
 *
 *  5 |  | 6
 * -----------
 *    | 1 | 1
 * -----------
 *  3 | 4 | 8
 *
 * Then our total odds are 28 (5+6+1+1+3+4+8) and we
 * want our random number generator to generate numbers
 * between 1 and 28.
 *
 * @return The random number rolled.
 */
public int rollDice() {
        if (fullBoard()) {
                return 0;
        }
        return Utils.generator.nextInt(totalOdds)+1;
}

/**
 * Based on the diceRoll, calculate which position
 * on the board should be played based on the current odds (beads)
 * in each available cell.
 *
 * On a 3x3 board.
 *
 *    | X |
 * -----------
 *  O |   |
 * -----------
 *    |   |
 *
 * If we had the following beads (I left the Xs and Os off)
 *
 *  5 |  | 6
 * -----------
 *    | 1 | 1
 * -----------
 *  3 | 4 | 8
 *
 * Here are some expected outputs based on sample diceRolls
 *
 * diceRoll 3 => returns 1
 * diceRoll 11 => returns 3
 * diceRoll 12 => returns 5
 * diceRoll 14 => return 7
 * 28
 *
 * @return int which position on the board should we choose
 */
public int calculatePosition(int diceRoll) {

        if (fullBoard()) {
                return 0;
        }


        for (int indexOfOdds = 0; indexOfOdds < boardOdds.length; indexOfOdds++) {
                diceRoll -= boardOdds[indexOfOdds];
                if (diceRoll <= 0) {
                        return (indexOfOdds + 1);
                }
        }
        return 0;
}

private boolean fullBoard(){
        return (game.numRounds == game.board.length);
}

/**
 * Reset the odds back to an un-trained set based on
 * Menace algorithm.
 */
public void resetOdds() {
        boardOdds = new int[] {8,8,4,4,2,2,1,1,1};
        currentPosition = 0;
        updateOdds();
}

private void updateOdds(){
        totalOdds = 0;

        for (int i = 0; i < game.board.length; i++) {
                CellValue cell = game.board[game.boardIndexes[i]];
                if (cell != CellValue.EMPTY) {
                        boardOdds[game.boardIndexes[i]] = 0;
                }
                totalOdds += boardOdds[game.boardIndexes[i]];
        }
}


public String toString(){
        StringBuilder b = new StringBuilder();
        int maxRowsIndex = game.numRows - 1;
        int maxColumnsIndex = game.numColumns - 1;

        String lineSeparator = Utils.repeat("---", game.numColumns) + Utils.repeat("-", game.numColumns - 1);

        //b.append("Menace rolled: " + dice + " on board:\n");
        b.append("POSITION: " + String.valueOf(currentPosition)  + " (Odds " + totalOdds +  ")\n");

        for (int i = 0; i < game.numRows; i++) {
                for (int j = 0; j < game.numColumns; j++) {
                        int index = i*game.numColumns + j;

                        b.append(" ");
                        //practice using Ternary operator '?'
                        String valueToAppend = (game.board[game.boardIndexes[index]].toString() == " ") ? String.valueOf(boardOdds[game.boardIndexes[index]]) : game.board[game.boardIndexes[index]].toString();
                        b.append(valueToAppend);
                        b.append(" ");

                        if (j < maxColumnsIndex) {
                                b.append("|");
                        }
                }

                // Line separator after each row, except the last
                if (i < maxRowsIndex) {
                        b.append("\n");
                        b.append(lineSeparator);
                        b.append("\n");
                }
        }

        return b.toString();
}

}