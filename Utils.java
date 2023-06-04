
import java.lang.Math;
import java.util.Scanner;
  
class Utils {
  private static Scanner scan = new Scanner (System.in);
  
  public static int randInt(int min, int max) {
    return (int)(Math.random() * (max - min + 1)) + min;
  }

  public static String input (String question) {
    System.out.print (question);
    return scan.nextLine();
  }

  public static int inputInt (String question) {
    System.out.print (question);
    while (true) {
      try {
        String input = scan.nextLine();
        return Integer.parseInt(input);
      } catch (Exception e) {
        System.out.print ("Please enter an integer: ");
      }
    }
  }

  public static double inputDouble (String question) {
    System.out.print (question);
    while (true) {
      try {
        String input = scan.nextLine();
        return Double.parseDouble(input);
      } catch (Exception e) {
        System.out.print ("Please enter a number: ");
      }
    }
  }

}