
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


//to do 
// add hashmaps and serializations

public class TicTacToe {

    static ArrayList<Integer> playerPositions = new ArrayList<Integer>();
    static ArrayList<Integer> robotPositions = new ArrayList<Integer>();


    public static void main(String[] args) {
        Users users = Users.restore();
      if (users == null)
        users = new Users();
  
      System.out.println ("Current players:\n" + users);
      while (true) {
        String name = Utils.input ("Enter a new or existing user to play (or q to quit): ").trim().toLowerCase();
        if (name.equals("q")) {
            System.out.println(users);
            break;
        }
        if (name.length() == 0) {
          System.out.println("Sorry try again.");
          continue;  //if word doesn't have any characters, the continue makes you retry again from the top. 
        }
        
      

      
      System.out.println ("Updated users:\n" + users);

        char[][] gameBoard = {{' ', '|', ' ', '|', ' '}, 
                                {'-', '+', '-', '+', '-'}, 
                                {' ', '|', ' ', '|', ' '}, 
                                {'-', '+', '-', '+', '-'}, 
                                {' ', '|', ' ', '|', ' '}};
        printGameBoard(gameBoard);

        Scanner scan = new Scanner(System.in);
        System.out.println("Hello, " + name + " welcome to 3x3 tictactoe. You will be playing X against a robot.");

        while(true){ 
            //used the try/catch here
            int playerPos = Utils.inputInt("Where will you place your move? (1-9)");
                if (playerPos <= 0) {
                    playerPos = Utils.inputInt("I said pick from 1-9. Try again.");
                }
           
            

            //prevents problem where you or robot picks alr taken position
            while(playerPositions.contains(playerPos) || robotPositions.contains(playerPos)) {
                System.out.println("Position taken. Enter a correct position");
                playerPos = scan.nextInt();
            }

            placePiece(gameBoard, playerPos, "Player");
            //have to check winner after i play too because in tie scenarios, i need to also be able to play my final move
            //bc my winning method sometimes return just "", filter it so it only has win/lost/tie
            String result = checkWinner();
            if(result.length() > 0) {
                System.out.println(result);
                printGameBoard(gameBoard);
                break;
            }

            //creates a random number 1-9 inclusive 
            Random rand = new Random();
            int robotPos = rand.nextInt(9) + 1; 
            while(playerPositions.contains(robotPos) || robotPositions.contains(robotPos)) {
                robotPos = rand.nextInt(9) + 1; 
            }
            placePiece(gameBoard, robotPos, "Robot");

            printGameBoard(gameBoard);
            result = checkWinner();
            int playerWin = 0;
            int robotWin = 0;
            if(result.length() > 0) { 
                System.out.println(result);
                if (result.equals("Congrats you won against a robot lmao"))
                    playerWin++;

                if (result.equals("RIP, you're ass"))
                    robotWin++;

                printGameBoard(gameBoard);
                if (users.usersHashmap.get(name) == null) { // If no existing user, create one
                    User user = new User(name, playerWin, robotWin);
                    users.usersHashmap.put(name, user);
                  } else {                                    // Else just update their age and grade
                    users.usersHashmap.get(name).playerWin = playerWin;
                    users.usersHashmap.get(name).robotWin = robotWin;
                  }
                  users.save();
                  break;
            }
        }
    }
}

    //printing out board 
    public static void printGameBoard(char[][] gameBoard) {
        for(int r = 0; r < gameBoard.length; r++) {
            for(int c = 0; c < gameBoard[0].length; c++) {
                System.out.print(gameBoard[r][c]);
            }
            System.out.println();
        }
    }

    public static void placePiece(char[][] gameBoard, int pos, String user) {
        char symbol = ' ';

        if (user.equals("Player")) {
            symbol = 'X';
            playerPositions.add(pos);
        }

        else if(user.equals("Robot")) {
            symbol = 'O';
            robotPositions.add(pos);
        }

        switch(pos) {
            case 1: 
                gameBoard[0][0] = symbol;
                break;
            case 2: 
                gameBoard[0][2] = symbol;
                break;
            case 3: 
                gameBoard[0][4] = symbol;
                break;
            case 4: 
                gameBoard[2][0] = symbol;
                break;
            case 5: 
                gameBoard[2][2] = symbol;
                break;
            case 6: 
                gameBoard[2][4] = symbol;
                break;
            case 7: 
                gameBoard[4][0] = symbol;
                break;
            case 8: 
                gameBoard[4][2] = symbol;
                break;
            case 9: 
                gameBoard[4][4] = symbol;
                break;
        }
    }

public static String checkWinner() {
    //so this basically creates an array, and if the code sees that the 
    //corresponding numbers are in the 2d array board, 
    //then we now they won with the number combo
    List topRow = Arrays.asList(1, 2, 3);
    List midRow = Arrays.asList(4, 5, 6);
    List botRow = Arrays.asList(7, 8, 9);
    List leftCol = Arrays.asList(1, 4, 7);
    List midCol = Arrays.asList(2, 5, 8);
    List botCol = Arrays.asList(3, 6, 9);
    List cross1 = Arrays.asList(1, 5, 9);
    List cross2 = Arrays.asList(2, 5, 7);
    
    //add all these winning conditions into one long array (list-type) called winning 
    List<List> winning = new ArrayList<List>(); 
    winning.add(topRow);
    winning.add(midRow);
    winning.add(botRow);
    winning.add(leftCol);
    winning.add(midCol);
    winning.add(botCol);
    winning.add(cross1);
    winning.add(cross2);
    int playerWin = 0;
    int robotWin = 0;
    //loop through the winning condiiton array
    //if within the playerPositions array (which stores all the moves the player has played)
    //contains ALL the numbers in one of the winning condition arrays
    //then you win!!
    for(List l: winning) {
        if (playerPositions.containsAll(l)) 
            return "Congrats you won against a robot lmao";
        else if(robotPositions.containsAll(l))
            return "RIP, you're ass";

        //otherwise, if all robot moves + player moves = 9, then that means board is full and no combo was made
        //ultimately leading to a draw
        else if(playerPositions.size() + robotPositions.size() == 9)
            return "TIE :(";
    }
    return "";
}



}