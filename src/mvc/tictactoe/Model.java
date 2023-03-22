package mvc.tictactoe;

import com.mrjaffesclass.apcs.messenger.*;

/**
 * The model represents the data that the app uses.
 * @author Roger Jaffe
 * @version 1.0
 */
public class Model implements MessageHandler {

  // Messaging system for the MVC
  private final Messenger mvcMessaging;

  // Model's data variables
  private boolean whoseMove;
  private boolean gameOver;
  private String[][] board;
  /**
   * Model constructor: Create the data representation of the program
   * @param messages Messaging class instantiated by the Controller for 
   *   local messages between Model, View, and controller
   */
  public Model(Messenger messages) {
    mvcMessaging = messages;
    this.board = new String[3][3];
  }
  
  /**
   * Initialize the model here and subscribe to any required messages
   */
  public void init() {
    this.newGame();
    this.mvcMessaging.subscribe("playerMove", this);
    this.mvcMessaging.subscribe("newGame", this);
    this.mvcMessaging.subscribe("resetBoard", this);
  }
  
  private void newGame() {
      for (int row=0; row<this.board.length; row++) {
          for (int col=0; col<this.board[0].length; col++) {
              this.board[row][col] = "";
          }
      }
      this.whoseMove = false;
      this.gameOver = false;
  }
  
  private String isWinner() {
      int count = 0;
      for (String[] board1 : this.board) {
          for (int col = 0; col<this.board[0].length; col++) {
              if (board1[col].equals("X")) {
                  count++;
              }
              if (board1[col].equals("O")) {
                  count--;
              }
              if (count == 3) {
                  return "X";
              }
              if (count == -3) {
                  return "O";
              }
              count = 0;
          }
      }
      
      for (int col=0; col<this.board[0].length; col++) {
          for (String[] board1 : this.board) {
              if (board1[col].equals("X")) {
                  count++;
              }
              if (board1[col].equals("O")) {
                  count--;
              }
              if (count == 3) {
                  return "X";
              }
              if (count == -3) {
                  return "O";
              }
              count = 0;
          }
      }
      
      if (!this.board[0][0].equals("")) {
          if (this.board[0][0].equals(this.board[1][1]) && this.board[1][1].equals(this.board[2][2])) {
              return this.board[0][0];
          }
      }
      
      if (!this.board[0][2].equals("")) {
          if (this.board[0][2].equals(this.board[1][1]) && this.board[1][1].equals(this.board[2][0])) {
              return this.board[2][0];
          }
      }
      
      for (String[] board1 : this.board) {
          for (int col = 0; col<this.board[0].length; col++) {
              if (!board1[col].equals("")) {
                  count++;
              }
          }
      }
      
      if (count == 9) {
          return "tie";
      }
  }
  
  @Override
  public void messageHandler(String messageName, Object messagePayload) {
     // Display the message to the console for debugging
    if (messagePayload != null) {
      System.out.println("MSG: received by model: "+messageName+" | "+messagePayload.toString());
    } else {
      System.out.println("MSG: received by model: "+messageName+" | No data sent");
    }
    
    // playerMove message handler
    if (messageName.equals("playerMove")) {
      // Get the position string and convert to row and col
      String position = (String)messagePayload;
      Integer row = Integer.valueOf(position.substring(0,1));
      Integer col = Integer.valueOf(position.substring(1,2));
      // If square is blank...
      if (this.board[row][col].equals("")) {
        // ... then set X or O depending on whose move it is
        if (this.whoseMove) {
          this.board[row][col] = "X";
          this.whoseMove = false;
        } else {
          this.board[row][col] = "O";
          this.whoseMove = true;
        }
        // Send the boardChange message along with the new board 
        this.mvcMessaging.notify("boardChange", this.board);
      }
      
    // newGame message handler
    } else if (messageName.equals("resetBoard")) {
      // Reset the app state
      this.newGame();
      // Send the boardChange message along with the new board 
      this.mvcMessaging.notify("boardChange", this.board);
    }

  }

  /**
   * Getter function for variable 1
   * @return Value of variable1
   */
  

  /**
   * Setter function for variable 1
   * @param v New value of variable1
   */
 
  
  /**
   * Getter function for variable 1
   * @return Value of variable2
   */
  
  
  /**
   * Setter function for variable 2
   * @param v New value of variable 2
   */
  

}
