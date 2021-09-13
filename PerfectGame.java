public class PerfectGame {

TicTacToe game;
GameOutcome overallOutcome;
int overallRemaining;
GameOutcome[] moveOutcomes;
int[] moveRemainings;

public PerfectGame(TicTacToe aGame) {
        game = aGame;
        moveOutcomes = new GameOutcome[game.board.length];
        moveRemainings = new int[game.board.length];
        resetOutcome();
        setOutcome(game.gameState);
}

public void setOutcome(GameState state) {
        overallRemaining = 0;
        switch(state) {
        case XWIN:
                overallOutcome = game.nextPlayer() == CellValue.O ? GameOutcome.LOSE : GameOutcome.WIN;
                break;
        case OWIN:
                overallOutcome = game.nextPlayer() == CellValue.X ? GameOutcome.LOSE : GameOutcome.WIN;
                break;
        case DRAW:
                overallOutcome = GameOutcome.DRAW;
                break;
        default:
                overallOutcome = GameOutcome.UNKNOWN;
        }
}

public void setOutcome(PerfectGame nextMove, int position) {

        GameOutcome currentOutcome;
        int currentRemaining = nextMove.overallRemaining + 1;
        switch (nextMove.overallOutcome) {
        case WIN:
                currentOutcome = GameOutcome.LOSE;
                break;
        case LOSE:
                currentOutcome = GameOutcome.WIN;
                break;
        case DRAW:
                currentOutcome = GameOutcome.DRAW;
                break;
        default:
                throw new IllegalStateException("Unable to set the outcome on " + nextMove.overallOutcome + " for position " + position);
        }

        boolean shouldUpdate;
        if (currentOutcome == GameOutcome.WIN) {
                shouldUpdate = true;
        } else if (currentOutcome == GameOutcome.DRAW && overallOutcome != GameOutcome.WIN) {
                shouldUpdate = true;
        } else if (currentOutcome == GameOutcome.LOSE && overallOutcome == GameOutcome.UNKNOWN) {
                shouldUpdate = true;
        } else {
                shouldUpdate = false;
        }

        int index = position - 1;

        moveOutcomes[index] = currentOutcome;
        moveRemainings[index] = currentRemaining;
        if (shouldUpdate) {
                if (overallRemaining == 0 || currentRemaining < overallRemaining) {
                        overallRemaining = currentRemaining;
                }
                overallOutcome = currentOutcome;
        }
}

public String toString() {
        StringBuilder b = new StringBuilder();
        int maxRowsIndex = game.numRows - 1;
        int maxColumnsIndex = game.numColumns - 1;

        String lineSeparator = Utils.repeat("---", game.numColumns) + Utils.repeat("-", game.numColumns - 1);

        b.append("OVERALL: " + overallOutcome + "("+ overallRemaining +")\n");
        for (int i = 0; i < game.numRows; i++) {
                for (int j = 0; j < game.numColumns; j++) {
                        int index = i*game.numColumns + j;

                        b.append(" ");
                        b.append(toString(index));
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

private String toString(int index) {
        int lookupIndex = game.boardIndexes[index];
        switch (moveOutcomes[lookupIndex]) {
        case WIN:
                return "W " + moveRemainings[lookupIndex];
        case LOSE:
                return "L " + moveRemainings[lookupIndex];
        case DRAW:
                return "D " + moveRemainings[lookupIndex];
        default:
                return game.board[lookupIndex] + "";
        }
}

private void resetOutcome() {
        for (int i = 0; i < moveOutcomes.length; i++) {
                moveOutcomes[i] = GameOutcome.UNKNOWN;
                moveRemainings[i] = 0;
        }
}
}