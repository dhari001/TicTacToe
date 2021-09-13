
public class GameMain {

/**
 * <b>main</b> of the application. Creates the instance of  TicTacToe
 * and starts the game. If two parameters numRows  and numColumns
 * are passed, they are used. If the paramters numRows, numColumns
 * and win are passed, they are used.
 * Otherwise, a default value is used. Defaults values (3) are also
 * used if the paramters are too small (less than 2).
 * Here, we assume that the command numRows arguments are indeed integers
 *
 * @param args command numRows parameters
 */
public static void main(String[] args) {

        int numRows = validateInt(args, 0, 3);
        int numColumns = validateInt(args, 1, 3);
        int wins = validateInt(args, 2, 3);
        int numTraining = validateInt(args, 3, 500);
        boolean isDebug = validateBoolean(args, 4, "false");

        if (args.length > 5) {
                System.out.println("Too many arguments. Only the first 5 are used.");
        }

        Player menace = new ComputerMenacePlayer(numRows, numColumns, wins);
        Player menace2 = new ComputerMenacePlayer(numRows, numColumns, wins);
        Player random = new ComputerRandomPlayer();
        Player perfect = new ComputerPerfectPlayer(numRows, numColumns, wins);
        Player human = new HumanPlayer();

        TicTacToe game;
        Player[] players = new Player[2];
        boolean keepPlaying = true;
        int numGames = 0;

        while (keepPlaying) {
                System.out.println("(1) Menace against a human player");
                System.out.println("(2) Train Menace against perfect player");
                System.out.println("(3) Train Menace against random player");
                System.out.println("(4) Train Menace against another menace");
                System.out.println("(5) Delete (both) Menace training sets");
                System.out.println("(6) Human to play perfect player");
                System.out.println("(7) Perfect player to play human");
                System.out.println("(8) Human against a menace player");
                System.out.println("(Q)uit");

                String choice = Utils.readLine();

                switch (choice) {
                case "1":
                        players[0] = menace;
                        players[1] = human;
                        numGames = 1;
                        break;
                case "2":
                        players[0] = menace;
                        players[1] = perfect;
                        numGames = numTraining;
                        break;
                case "3":
                        players[0] = menace;
                        players[1] = random;
                        numGames = numTraining;
                        break;
                case "4":
                        players[0] = menace;
                        players[1] = menace2;
                        numGames = numTraining;
                        break;
                case "5":
                        menace = new ComputerMenacePlayer(numRows, numColumns, wins);
                        menace2 = new ComputerMenacePlayer(numRows, numColumns, wins);
                        numGames = 0;
                        break;
                case "6":
                        players[0] = human;
                        players[1] = perfect;
                        numGames = 1;
                        break;
                case "7":
                        players[0] = perfect;
                        players[1] = human;
                        numGames = 1;
                        break;
                case "8":
                        players[0] = human;
                        players[1] = menace;
                        numGames = 1;
                        break;
                case "Q":
                case "q":
                        numGames = 0;
                        keepPlaying = false;
                        break;
                default:
                        numGames = 0;
                }

                if (numGames > 0) {
                        players[0].name = "Player 1";
                        players[1].name = "Player 2";
                        players[0].isDebug = isDebug;
                        players[1].isDebug = isDebug;

                        if (numGames > 1) {
                                System.out.println("About to train with " + numGames + " games.");
                        }
                        for (int i = 0; i < numGames; i++) {
                                game = new TicTacToe(numRows, numColumns, wins);
                                playGame(game, players[0], players[1]);
                                if (numGames == 1 || isDebug) {
                                        System.out.println("");
                                        System.out.println("Result: " + game.gameState);
                                        System.out.println(game);
                                        System.out.println("");
                                }
                        }

                        System.out.println(players[0]);
                        System.out.println("");
                        System.out.println(players[1]);
                        System.out.println("");
                } else {
                        System.out.println("Bye.");
                }

        }
}

/**
 * Play a game between two players
 * Useful for testing games between automatic players
 */
public static void playGame(TicTacToe game, Player p1, Player p2) {
        p1.startGame(true);
        p2.startGame(false);

        boolean isP1sTurn = true;
        while(game.gameState == GameState.PLAYING) {
                Player p = isP1sTurn ? p1 : p2;
                p.play(game);
                isP1sTurn = !isP1sTurn;
        }

        p1.endGame(game.gameState);
        p2.endGame(game.gameState);
}

/**
 * Extract an integer from the provided argument
 * it must be 2 or more.
 *
 * @param args The command numRows parameters
 * @param index Which index to parse
 */
public static int validateInt(String[] args, int index, int defaultIfNull) {
        if (index >= 0 && index < args.length) {
                int num = Integer.parseInt(args[index]);
                if (num >= 1) {
                        return num;
                } else {
                        System.out.println("Invalid argument, using default...");
                }
        }
        return defaultIfNull;
}

/**
 * Extract a boolean player from the arguments
 * "true" for true
 * "false" for false
 *
 * @param args The command lines parameters
 * @param index Which index to parse
 * @param defaultIfMissing Default value if no input at that index
 * @return String The value at that position of the default if not available
 */
public static boolean validateBoolean(String[] args, int index, String defaultIfMissing) {
        String bool;
        if (index >= 0 && index < args.length) {
                bool = args[index];
        } else {
                bool = defaultIfMissing;
        }

        switch (bool) {
        case "true":
                return true;
        case "false":
        default:
                return false;
        }
}
}