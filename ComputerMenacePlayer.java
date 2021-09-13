
import java.util.*;

public class ComputerMenacePlayer extends Player {

private LinkedList<LinkedList<TicTacToe> > allGames;
private LinkedList<LinkedList<MenaceGame> > winnableGames;
private LinkedList<MenaceGame> learningPath;

/**
 * A menace player needs to know the size of the game before starting
 * Only optimized for a 3x3 board
 *
 * @param aNumRows the number of lines in the game
 * @param aNumColumns the number of columns in the game
 * @param aSizeToWin the number of cells that must be aligned to win.
 */
public ComputerMenacePlayer(int aNumRows, int aNumColumns, int aSizeToWin) {
        super();
        TicTacToeEnumerations enums = new TicTacToeEnumerations(aNumRows, aNumColumns, aSizeToWin);

        allGames = enums.generateAllGames();
        int numLevels = allGames.size();

        winnableGames = new LinkedList<LinkedList<MenaceGame> >();
        for (int i = 0; i < numLevels; i++) {
                LinkedList<MenaceGame> levelwinnableGames = new LinkedList<MenaceGame>();
                winnableGames.add(levelwinnableGames);
                for (TicTacToe gameTTT : allGames.get(i)) {
                        levelwinnableGames.add(new MenaceGame(gameTTT));
                }
        }
}

/**
 * Plays on the board based on best odds of that current game at that time.
 *
 * @param game the TicTacToe game to play on.
 */
public boolean play(TicTacToe game) {
        if(game.numRounds == game.board.length) {
                return false;
        }

        for(MenaceGame menaceGame: winnableGames.get(game.numRounds)) {
                if(menaceGame.game.alignAndEquals(game)) {

                        if (isDebug) {
                                System.out.println("\nMenace player choosing based on: ");
                                System.out.println(menaceGame);
                                System.out.println("");
                        }

                        game.play(menaceGame.pickCurrentPosition());
                        learningPath.add(menaceGame);
                        return true;
                }
        }
        return false;
}


/**
 * Begins playing the game, inheriting startGame(boolean) from Player class.
 * Records a LinkedList<MenaceGame> for all played games thus far.
 *
 * @param game the TicTacToe game to play on.
 */
public void startGame(boolean isFirst){
        super.startGame(isFirst);
        learningPath = new LinkedList<MenaceGame>();
}


/**
 * The game is over.  Record the WIN, LOSE or DRAW.
 *
 * Compare my move (X or O) to the state of the game (XWIN, YWIN, DRAW, other)
 * and return the GameOutcome (did I WIN, did I LOSE, did I DRAW, or UNKNOWN)
 *
 * @param game The outcome of the game (XWIN, YWIN, DRAW, etc)
 * @return a GameOutcome showing if I WIN, LOSE or DRAW (or UNKNOWN for we cannot tell)
 */
public GameOutcome endGame(GameState game) {
        GameOutcome outcome = super.endGame(game);
        for( MenaceGame games: learningPath ) {
                games.setOutcome(outcome);
        }
        return outcome;
}

}