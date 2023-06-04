
import java.io.Serializable;
  
public class User implements Serializable {
    public String name;
    public int playerWin;
    public int robotWin;
  
    public User (String name,int playerWin, int robotWin) {
      this.name = name;
      this.playerWin = playerWin;
      this.robotWin = robotWin;
    }
  
    public String toString() {
      return name + "\n\tPlayer Wins: " + playerWin + "\n\tRobot Wins: " + robotWin;
    }
  }