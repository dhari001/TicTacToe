public class EnumerationsMain {

  /**
   * Another <b>main</b> application.
   * This one will generate a list of games
   * and describe the outputs
   *
   * @param args command lines parameters
   */
  public static void main(String[] args) {

    int numRows = GameMain.validateInt(args, 0, 3);
    int numColumns = GameMain.validateInt(args, 1, 3);
    int wins = GameMain.validateInt(args, 2, 3);
    boolean showAllGames = GameMain.validateBoolean(args, 3, "false");

    if (args.length > 4){
        System.out.println("Too many arguments. Only the first 4 are used.");
    }

    TicTacToeEnumerations generator = new TicTacToeEnumerations(numRows, numColumns, wins);
    generator.generateAllGames();
    System.out.println(generator.toString(showAllGames));
  }

  /**
   * Extract an integer from the provided argument
   * it must be 2 or more.
   *
   * @param args The command lines parameters
   * @param index Which index to parse
   */
  private static int validateInt(String[] args, int index) {
    if (index >= 0 && index < args.length) {
      int num = Integer.parseInt(args[index]);
      if(num >= 2){
        return num;
      } else {
        System.out.println("Invalid argument, using default...");
      }
    }
    return 3;
  }

}