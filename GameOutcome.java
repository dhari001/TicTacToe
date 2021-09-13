/**
 * An enum class that defines the values
 *  <b>WIN</b>,
 *  <b>LOSE</b>,
 *  <b>DRAW</b>, or
 *  <b>UNKNOWN</b>
 */

public enum GameOutcome {
  WIN,
  DRAW,
  LOSE,
  UNKNOWN;

  public boolean isBetter(GameOutcome other) {
    return this.compareTo(other) < 0;
  }

  public boolean asGoodOrBetter(GameOutcome other) {
    return this.compareTo(other) <= 0;
  }
}