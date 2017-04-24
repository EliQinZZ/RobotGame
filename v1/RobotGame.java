/**
 * File Name: RobotGame.java
 * Description: This is the controller and view class of the robot game.
 * Author: Zhizhen (Eli) Qin
 */

import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import java.lang.Integer;

/**
 * Manipulate the robot behaviors based on the user input
 */
public class RobotGame extends Application
{
  private final double SCENE_WIDTH = 500;       // Width of the scene
  private final double SCENE_HEIGHT = 600;      // Height of the scene

  private final int BOARD_SIZE = 8;             //Size of the board

  // Color constants
  private final Color RECT_COLOR = Color.rgb(0x44, 0x27, 0x16);
  private final Color ROBOT_COLOR = Color.rgb(0xDC, 0xD9, 0xE2);
  private final Color ERR_RECT_COLOR = Color.rgb(255, 255, 255, 0.7);

  // Font family constant
  private final String FNT_FAMILY = "Times New Roman";

  // Font size constants
  private final int TITLE_FNT_SIZE = 35;
  private final int STATUS_FNT_SIZE = 23;
  private final int INSTR_FNT_SIZE = 16;
  private final int ERR_1_FNT_SIZE = 32;
  private final int ERR_2_FNT_SIZE = 20;

  // Size for unit square
  private final int UNIT_SIZE = 50;

  // Expected arguments
  private static final int EXPECTED_ARGS = 3;
  
  GridPane mainGrid;      // The main grid pane to layout components
  StackPane mainStack;    // The main stack pane to layer components
  Scene scene;            // Primary scene
  Text robotStatusText;   // Text label indication current robot status
  Rectangle errRect;      // Rectangle to hold error messages
  VBox errTexts;          // Error meesages

  // The board for robot to play on
  Rectangle[][] rects = new Rectangle[BOARD_SIZE][BOARD_SIZE];
  ImageView[][] robotImages = new ImageView[BOARD_SIZE][BOARD_SIZE];
  
  private Robot robot;                      // Our robot
  private static int initialX;              // Initial X location
  private static int initialY;              // Initial Y location
  private static Direction initialDirection;// Initial facing direction

  private boolean errDisplaying = false;    // Whether error messages are 
                                            // displaying


  /**
   * Start the GUI interface
   *
   * @param primaryStage the primary stage for GUI
   */
  @Override
  public void start(Stage primaryStage) {
    // Create the robot based on the user inputs
    robot = new Robot(initialX, initialY, initialDirection);
    
    // Set up stage
    primaryStage.setTitle("Robot Game");
    primaryStage.setMinWidth(SCENE_WIDTH);
    primaryStage.setMinHeight(SCENE_HEIGHT);
    primaryStage.setResizable(false);
    
    // Build the whole game board
    buildGameBoard();
    
    // Add the built scene onto the stage
    primaryStage.setScene(scene);
    primaryStage.show();

    // Handle keyboard events
    handleKeyEvent();
  }

  /**
   * Handle keyboard events
   */
  private void handleKeyEvent() {
    scene.setOnKeyPressed(e ->{
      // If error messages are displaying, hide them
      if(errDisplaying) {
        hideError();
        return;
      }
      
      // Perform actions based on different keys pressed
      switch(e.getCode()) {
      // Move the robot when M is pressed
      case M:
        boolean success = robot.move();

        // If the move is unsuccessful, display the error messages
        if(!success) {
          displayError();
        }

        // If the move is successful, update game board to reflect the change
        else {
          updateBoard();
        }
        break;
      
      // Turn the robot to face left upon L pressed
      case L:
        robot.turnLeft();
        updateBoard();
        break;

      // Turn the robot to face left upon R pressed
      case R:
        robot.turnRight();
        updateBoard();
        break;

      default:
        break;
      }
    });
  }
  
  /**
   * Display the error messages
   */
  private void displayError() {
    errRect.setVisible(true);
    errTexts.setVisible(true);
    errDisplaying = true;
  }

  /**
   * Hide the error messages
   */
  private void hideError() {
    errRect.setVisible(false);
    errTexts.setVisible(false);
    errDisplaying = false;  
  }


  /**
   * Build the game board upon first launch
   */
  private void buildGameBoard() {
    // Create main grid and main stack to hold components
    mainGrid = new GridPane();
    mainStack = new StackPane();

    // Add the grid onto the stack
    mainStack.getChildren().add(mainGrid);  

    // Style the main grid  
    mainGrid.setAlignment(Pos.CENTER);
    mainGrid.setPadding(new Insets(0));
    mainGrid.setStyle("-fx-background-color: rgb(236, 203, 168)");

    mainGrid.setHgap(0);
    mainGrid.setVgap(0);

    // Title text
    Text titleText = new Text("Robot Game");
    titleText.setFont(Font.font(FNT_FAMILY, FontWeight.BOLD, TITLE_FNT_SIZE));
    mainGrid.add(titleText, 0, 0, 8, 1);

    // Instruction texts
    Text instrTitle = new Text("Keyboard Operations:");
    Text instrM = new Text("M: Move forward.");
    Text instrL = new Text("L: Turn to face left.");
    Text instrR = new Text("R: Turn to face right.");

    instrTitle.setFont(Font.font(FNT_FAMILY, FontWeight.BLACK, 
                                 INSTR_FNT_SIZE));
    instrM.setFont(Font.font(FNT_FAMILY, FontWeight.BLACK, INSTR_FNT_SIZE));
    instrL.setFont(Font.font(FNT_FAMILY, FontWeight.BLACK, INSTR_FNT_SIZE));
    instrR.setFont(Font.font(FNT_FAMILY, FontWeight.BLACK, INSTR_FNT_SIZE));
    
    // Add the instructions onto the grid pane
    mainGrid.add(instrTitle, 0, 9, 4, 1);
    mainGrid.add(instrM, 0, 10, 4, 1);
    mainGrid.add(instrL, 0, 11, 4, 1);
    mainGrid.add(instrR, 0, 12, 4, 1);

    // Robot status texts
    robotStatusText = new Text("Robot: " + robot.toString());
    robotStatusText.setFont(Font.font(FNT_FAMILY, FontWeight.BOLD, 
                                      STATUS_FNT_SIZE));
    robotStatusText.setTextAlignment(TextAlignment.RIGHT);
    mainGrid.add(robotStatusText, 3, 9, 5, 4);

    // Construct error messages
    errRect = new Rectangle(SCENE_WIDTH, SCENE_HEIGHT);
    errTexts = new VBox();
    errTexts.setSpacing(30);
    Text errText1 = new Text("Cannot move beyound boundary!");
    Text errText2 = new Text("Press any key to continue");
    
    errText1.setFont(Font.font(FNT_FAMILY, FontWeight.BOLD, ERR_1_FNT_SIZE));
    errText2.setFont(Font.font(FNT_FAMILY, FontWeight.BOLD, ERR_2_FNT_SIZE));

    // Add error messages into the vBox
    errTexts.getChildren().addAll(errText1, errText2);
    errTexts.setAlignment(Pos.CENTER);

    errRect.setFill(ERR_RECT_COLOR);
    
    // Hide the error messages in the beginning
    errRect.setVisible(false);
    errTexts.setVisible(false);
    
    // Add error messages onto the stack
    mainStack.getChildren().addAll(errRect, errTexts);

    // Create the table for robot to live
    for(int j = 0; j < BOARD_SIZE; ++j) {
      for(int i = 0; i < BOARD_SIZE; ++i) {
        
        // Stack for a unit square
        StackPane unitStack = new StackPane();

        Rectangle rect = new Rectangle();
        
        rect.setHeight(UNIT_SIZE);
        rect.setWidth(UNIT_SIZE);
        rect.setFill(RECT_COLOR);
        rect.setStroke(Color.BLACK);

        ImageView robotImage = new ImageView(
          new Image("RobotIcon.png"));

        robotImage.setFitHeight(UNIT_SIZE);
        robotImage.setFitWidth(UNIT_SIZE);

        robotImage.setVisible(false);
        
        unitStack.getChildren().addAll(rect, robotImage);
        StackPane.setMargin(robotImage, new Insets(1));

        // Put the robot onto the table
        if(i == robot.getX() - 1 && j == 8 - robot.getY()) {
          rect.setFill(ROBOT_COLOR);
          robotImage.setVisible(true);
          setImageFacing(robotImage, robot.getDirection());
        }
        
        // Add the unit onto the main grid
        mainGrid.add(unitStack, i, j + 1);

        rects[i][j] = rect;
        robotImages[i][j] = robotImage;
        
      }
    }

    // Add the main stack onto the scene
    scene = new Scene(mainStack);
  }

  /**
   * Update the gameboard for every valid action
   */
  private void updateBoard() {
    // Update each cell of the board
    for(int j = 0; j < BOARD_SIZE; ++j) {
      for(int i = 0; i < BOARD_SIZE; ++i) {
        if(i == robot.getX() - 1 && j == 8 - robot.getY()) {
          rects[i][j].setFill(ROBOT_COLOR);
          robotImages[i][j].setVisible(true);
          setImageFacing(robotImages[i][j], robot.getDirection());
        }
        else {
          rects[i][j].setFill(RECT_COLOR);
          robotImages[i][j].setVisible(false);
        }
      }
    }

    // Update robot status text
    robotStatusText.setText("Robot: " + robot.toString());
  }
  
  /**
   * Set the rotation of robot image according to the facing of the robot
   *
   * @param robotImage the image of the robot
   * @param direction the direction that the robot is facing
   */
  private void setImageFacing(ImageView robotImage, Direction direction) {
    int rotateAngle = 0;
    switch(direction) {
      case NORTH:
        rotateAngle = 0;
        break;
      case EAST:
        rotateAngle = 90;
        break;
      case SOUTH:
        rotateAngle = 180;
        break;
      case WEST:
        rotateAngle = 270;
        break;
    }
    
    // Rotate the image based on the facing of the robot
    robotImage.setRotate(rotateAngle);

  }

  /**
   * Main function to process user input
   *
   * @param args user input arguments
   */
  public static void main(String[] args) {

    // Check the number of the arguments
    if(args.length < EXPECTED_ARGS) {
      System.err.println("Too few arguments");
      printUsage();
      System.exit(-1);
    }

    if(args.length > EXPECTED_ARGS) {
      System.err.println("Too much arguments");
      printUsage();
      System.exit(-1);
    }

    // Convert the arguments to numbers
    initialX = convertToInt(args[0]);
    initialY = convertToInt(args[1]);

    // Check the range of the numbers
    checkBounds(initialX);
    checkBounds(initialY);
    
    // Convert the argument to direction
    initialDirection = parseDirection(args[2]);
      
    Application.launch(args);
  }

  /**
   * Convert a string to an integer
   *
   * @param arg the string to be converted
   * @return the converted integer
   */
  private static int convertToInt(String arg) {
    int intValue = -1;
    try {
      intValue = Integer.parseInt(arg);
    }
    catch (NumberFormatException e) {
      for(int i = 0; i < arg.length(); ++i) {
        if(arg.charAt(i) < '0' || arg.charAt(i) > '9') {
          System.err.println("Input \"" + arg + "\" is not an integer");
          printUsage();
          System.exit(-1);
        }
      }
      System.err.println("Input \"" + arg + "\" is too big");
      printUsage();
      System.exit(-1);
    }

    return intValue;
  }

  /**
   * Check whether the number is in bounds
   *
   * @param value the number to be checked
   */
  private static void checkBounds(int value) {
    if(value < 1 || value > 8) {
      System.err.println("Input \"" + value + "\" is not in bound [1, 8]");
      printUsage();
      System.exit(-1);
    }
  }

  /**
   * Convert a string to a direction
   *
   * @param arg the string to be converted
   * @return the converted direction
   */
  private static Direction parseDirection(String sDirection) {
    if(sDirection.length() != 1) {
      System.err.println("Input \"" + sDirection + "\" is not a " +
        "valid direction");
      printUsage();
      System.exit(-1);
    }

    char cDirection = sDirection.charAt(0);

    switch(cDirection) {
      case 'N': return Direction.NORTH;
      case 'E': return Direction.EAST;
      case 'S': return Direction.SOUTH;
      case 'W': return Direction.WEST;
      default:
        System.err.println("Input \"" + cDirection + "\" is not a " +
          "valid direction");
        printUsage();
        System.exit(-1);
        break;
    }
    
    return null;
  }
  
  /**
   * Print usage when the user input is not correct
   */
  private static void printUsage() {
    System.err.println(
      "\nUsage: RobotGame originX originY originDirection\n" +
      "    originX -- an integer representing the X coordinate of robot's \n" +
      "               original position.\n" +
      "               Must be within the limits of [1-8]\n" +
      "    originY -- an integer representing the Y coordinate of robot's \n" +
      "               original position.\n" +
      "               Must be within the limits of [1-8]\n" +
      "    originDirection -- a character representing the original facing \n" +
      "                       of the robot.\n" +
      "                       Must be N, E, S or W (for North, East, South \n" +
      "                       and West, respectively).\n");
  }
}
