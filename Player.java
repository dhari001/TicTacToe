public abstract class Player {

private static final int DEFAULT_WINDOW_SIZE = 50;

private int numberOfWins;
private int numberOfLosses;
private int numberOfDraws;
private int numberOfGames;
private int windowSize;

public String name;
public boolean isDebug;

protected CellValue myMove;

private GameOutcome[] history;
private int historyIndex;

public Player() {
        this(DEFAULT_WINDOW_SIZE);
}

public Player(int aWindowSize) {
        windowSize = aWindowSize;

        name = "This player";
        isDebug = false;

        numberOfWins = 0;
        numberOfLosses = 0;
        numberOfDraws = 0;
        numberOfGames = 0;

        myMove = CellValue.EMPTY;
        history = new GameOutcome[windowSize];
        historyIndex = -1;
}

abstract boolean play(TicTacToe game);

/**
 * Set my letter to X if I am playing first,
 * or O if I am not playing first (i.e. playing second)
 * @param isFirst Am I the first to play?
 */
public void startGame(boolean isFirst) {
        myMove = isFirst ? CellValue.X : CellValue.O;
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
        GameOutcome outcome = calculateOutcome(game);
        numberOfGames += 1;
        switch(outcome) {
        case WIN:
                numberOfWins += 1;
                break;
        case LOSE:
                numberOfLosses += 1;
                break;
        case DRAW:
                numberOfDraws += 1;
                break;
        }

        historyIndex = (historyIndex + 1) % windowSize;
        history[historyIndex] = outcome;

        return outcome;
}

private GameOutcome calculateOutcome(GameState game) {
        switch(game) {
        case XWIN:
                return myMove == CellValue.X ? GameOutcome.WIN : GameOutcome.LOSE;
        case OWIN:
                return myMove == CellValue.O ? GameOutcome.WIN : GameOutcome.LOSE;
        case DRAW:
                return GameOutcome.DRAW;
        default:
                return GameOutcome.UNKNOWN;
        }
}

public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(name + " has won " + numberOfWins + " games, ");
        sb.append("lost " + numberOfLosses + " games, ");
        sb.append("and " + numberOfDraws + " were draws.");

        if(numberOfGames > windowSize) {
                int w = 0;
                int l = 0;
                int d = 0;
                for(GameOutcome h : history) {
                        switch(h) {
                        case WIN:
                                w++;
                                break;
                        case LOSE:
                                l++;
                                break;
                        case DRAW:
                                d++;
                                break;
                        }
                }
                sb.append(" Over the last " + windowSize + " games, ");
                sb.append("this player has won " + w + ", ");
                sb.append("lost " + l + ", ");
                sb.append("and tied " + d + ".");
        }
        return sb.toString();
}
}