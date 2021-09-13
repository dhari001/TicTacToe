public class ComputerRandomPlayer extends Player {

  public boolean play(TicTacToe game) {
    int[] openSpots = game.emptyPositions();

    if (openSpots.length == 0) {
      return false;
    }

    int randomIndex = Utils.generator.nextInt(openSpots.length);
    int choice = openSpots[randomIndex];
    game.play(choice);
    return true;
  }

}