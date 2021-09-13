import java.util.Random;
import java.util.Scanner;

public class Utils {
  public static final Random generator = new Random();
  public static final String NEW_LINE = System.getProperty("line.separator");

  public static final String readLine() {
    Scanner reader = new Scanner(System.in);
    return reader.nextLine();
  }

  public static final int readInt() {
    Scanner reader = new Scanner(System.in);
    return reader.nextInt();
  }

  /**
   * The method String repeat is availabe in Java 11+
   * Some students still have Java 8 (or less) so we
   * will implement this method directly for backwards
   * compatibility
   * @param str The string the repeat
   * @param numTimes How often to repeat
   */
  protected static String repeat(String str, int numTimes)
  {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < numTimes; i++) {
      sb.append(str);
    }
    return sb.toString();
  }

}
