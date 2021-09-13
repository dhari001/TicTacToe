/**
 * An enum class that defines the values
 *  <b>INVALID</b>,
 *  <b>EMPTY</b>,
 *  <b>X</b>, and
 *  <b>O</b>.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public enum CellValue {
  INVALID("?"),
  EMPTY(" "),
  X("X"),
  O("O");

  private String display;

  private CellValue(String aDisplay) {
    display = aDisplay;
  }

  @Override
  public String toString() {
    return display;
  }

}