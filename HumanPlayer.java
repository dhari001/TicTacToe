public class HumanPlayer extends Player {

  public boolean play(TicTacToe game) {

    if (game.gameState != GameState.PLAYING) {
      return false;
    }

    CellValue nextPlayer = game.nextPlayer();
    while (nextPlayer == game.nextPlayer()) {
      System.out.println(game.toString());
      System.out.println("");
      System.out.print(game.nextPlayer() + " to play: ");
      String message = game.play(Utils.readInt());
      if (message != null) {
        System.out.println(message + "\n");
      }
    }

    return true;
  }

}