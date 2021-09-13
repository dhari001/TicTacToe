public class ComputerInOrderPlayer extends Player {

  public boolean play(TicTacToe game) {
    int nextPosition = 0;
    while (true) {
      nextPosition += 1;
      CellValue v = game.valueAt(nextPosition);
      if (v == CellValue.INVALID) {
        return false;
      } else if (v == CellValue.EMPTY) {
        break;
      }
    }
    game.play(nextPosition);
    return true;
  }

}