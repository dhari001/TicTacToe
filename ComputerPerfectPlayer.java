import java.util.*;

public class ComputerPerfectPlayer extends Player {

private LinkedList<LinkedList<TicTacToe> > games;
private LinkedList<LinkedList<PerfectGame> > winnable;
private ComputerRandomPlayer backup;

/**
 * A perfect player needs to know the size of the game before starting
 *
 * @param aNumRows the number of lines in the game
 * @param aNumColumns the number of columns in the game
 * @param aSizeToWin the number of cells that must be aligned to win.
 */
public ComputerPerfectPlayer(int aNumRows, int aNumColumns, int aSizeToWin) {

        TicTacToeEnumerations enums = new TicTacToeEnumerations(aNumRows, aNumColumns, aSizeToWin);
        games = enums.generateAllGames();
        int numLevels = games.size();

        // Populate the list of lists with Perfect Games
        winnable = new LinkedList<LinkedList<PerfectGame> >();
        for (int i = 0; i < numLevels; i++) {
                LinkedList<PerfectGame> levelWinnable = new LinkedList<PerfectGame>();
                winnable.add(levelWinnable);
                for (TicTacToe game : games.get(i)) {
                        levelWinnable.add(new PerfectGame(game));
                }
        }

        // Traverse (backwards) and set the game outcomes
        for (int level = numLevels - 1; level >= 0; level-- ) {
                LinkedList<PerfectGame> levelWinnable = winnable.get(level);
                for (PerfectGame perfect : winnable.get(level)) {
                        if (perfect.overallOutcome == GameOutcome.UNKNOWN) {
                                for (int position : perfect.game.emptyPositions()) {
                                        TicTacToe comparable = perfect.game.cloneNextPlay(position);
                                        for (PerfectGame template : winnable.get(level + 1)) {
                                                if (template.game.equals(comparable)) {
                                                        perfect.setOutcome(template, position);
                                                        break;
                                                }
                                        }
                                }
                        }
                }
        }

}

public boolean play(TicTacToe game) {
        for (PerfectGame perfectGame : winnable.get(game.numRounds)) {
                if (perfectGame.game.alignAndEquals(game)) {

                        if (isDebug) {
                                System.out.println("Perfect player choosing based on: ");
                                System.out.println(perfectGame);
                                System.out.println("");
                        }

                        int[] openSpots = perfectGame.game.emptyPositions();
                        if (openSpots.length == 0) {
                                return false;
                        }

                        int bestPosition = 0;
                        int bestNumMoves = openSpots.length;
                        GameOutcome bestOutcome = GameOutcome.UNKNOWN;

                        for (int position : openSpots) {
                                int index = position - 1;
                                int transformIndex = perfectGame.game.boardIndexes[index];
                                int numMoves = perfectGame.moveRemainings[transformIndex];
                                GameOutcome outcome = perfectGame.moveOutcomes[transformIndex];

                                boolean isBetterPosition = false;

                                // If we have no idea about the outcome then ignore it
                                if (outcome.isBetter(bestOutcome)) {
                                        isBetterPosition = true;

                                        // If we are going to lose, then picking the longest time to lose if best
                                } else if (outcome == GameOutcome.LOSE && bestOutcome == GameOutcome.LOSE) {
                                        isBetterPosition = numMoves > bestNumMoves;

                                        // If the number of moves is fewer than ou best number of moves
                                        // so far, then take it.  We either stop a on coming LOSS
                                        // or finding a better solution
                                } else if (numMoves < bestNumMoves && outcome != GameOutcome.LOSE && outcome.asGoodOrBetter(bestOutcome)) {
                                        isBetterPosition = true;

                                }

                                if (isBetterPosition) {
                                        bestPosition = position;
                                        bestNumMoves = numMoves;
                                        bestOutcome = outcome;
                                }
                        }

                        if (bestPosition > 0) {
                                game.play(bestPosition);
                                return true;
                        }
                }
        }

        // Nothing found, so play randomly
        return backup.play(game);
}

}