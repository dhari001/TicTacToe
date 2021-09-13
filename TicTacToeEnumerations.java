import java.util.LinkedList;

public class TicTacToeEnumerations {

  /**
   * The template game to analyze
   */
  TicTacToe template;

  /**
   * The list of lists of all generated games
   */
  LinkedList<LinkedList<TicTacToe>> allGames;


  /**
   * A constructor where you can specify the dimensions
   * of your game as rows x coluns grid, and a sizeToWin
   * to analyze.
   *
   * @param aNumRows the number of lines in the game
   * @param aNumColumns the number of columns in the game
   * @param aSizeToWin the number of cells that must be aligned to win.
   */
  public TicTacToeEnumerations(int aNumRows, int aNumColumns, int aSizeToWin) {
    template = new TicTacToe(aNumRows, aNumColumns, aSizeToWin);
  }

  public LinkedList<LinkedList<TicTacToe>> generateAllGames() {

    allGames = new LinkedList<LinkedList<TicTacToe>>();
    addToGames(template);

    LinkedList<TicTacToe> workingGames = new LinkedList<TicTacToe>();
    workingGames.add(template);

    while (!workingGames.isEmpty()) {
      TicTacToe game = workingGames.pop();
      for (int nextMove : game.emptyPositions()) {
        TicTacToe nextGame = game.cloneNextPlay(nextMove);
        if (addToGames(nextGame) && nextGame.gameState == GameState.PLAYING) {
          workingGames.add(nextGame);
        }
      }
    }

    return allGames;
  }

  public String toString() {
    return toString(false);
  }

  public String toString(boolean showAllGames) {
    if (allGames == null) {
      return "No games generated.";
    }

    StringBuilder s = new StringBuilder();

    int numGames = 0;
    int numXWin = 0;
    int numOWin = 0;
    int numDraw = 0;
    for (int i=0; i<allGames.size(); i++) {
      LinkedList<TicTacToe> games = allGames.get(i);
      int numStillPlaying = 0;
      StringBuilder sGames = new StringBuilder();
      for (TicTacToe g : games) {
        numGames += 1;
        switch (g.gameState) {
        case PLAYING:
          numStillPlaying += 1;
          break;
        case XWIN:
          numXWin += 1;
          break;
        case OWIN:
          numOWin += 1;
          break;
        case DRAW:
          numDraw += 1;
          break;
        }

        if (showAllGames) {
          sGames.append(g);
          sGames.append("\n\n");
        }

      }
      s.append("======= level "+ i +" =======: ");
      s.append(games.size() + " element(s) (");
      s.append(numStillPlaying + " still playing)\n");
      s.append(sGames.toString());
    }

    s.append("that's "+ numGames +" games\n");
    s.append(numXWin + " won by X\n");
    s.append(numOWin + " won by O\n");
    s.append(numDraw + " draw");
    return s.toString();
  }

  /**
   * Add the game to our all games list
   * make sure the position exists, and then add it
   */
  private boolean addToGames(TicTacToe game)
  {
    int index = game.numRounds;
    int size = game.numRounds + 1;
    while (allGames.size() < size) {
      allGames.add(new LinkedList<TicTacToe>());
    }

    LinkedList<TicTacToe> l = allGames.get(index);
    boolean isNewGame = !l.contains(game);
    if (isNewGame) {
      l.add(game);
    }
    return isNewGame;
  }

}