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

public class RobotGame extends Application
{
  private final double SCENE_WIDTH = 500;
  private final double SCENE_HEIGHT = 600;

  private final int BOARD_SIZE = 8;

  private final Color RECT_COLOR = Color.rgb(0x44, 0x27, 0x16);
  private final Color ROBOT_COLOR = Color.rgb(0xDC, 0xD9, 0xE2);
  private final Color ERR_RECT_COLOR = Color.rgb(255, 255, 255, 0.7);

  private final String FNT_FAMILY = "Times New Roman";

  private final int TITLE_FNT_SIZE = 35;
  private final int STATUS_FNT_SIZE = 23;
  private final int INSTR_FNT_SIZE = 16;
  private final int ERR_1_FNT_SIZE = 32;
  private final int ERR_2_FNT_SIZE = 20;
  
  GridPane mainGrid;
  StackPane mainStack;
  Scene scene;
  Text robotStatusText;
  Rectangle errRect;
  VBox errTexts;

  Rectangle[][] rects = new Rectangle[BOARD_SIZE][BOARD_SIZE];
  ImageView[][] robotImages = new ImageView[BOARD_SIZE][BOARD_SIZE];
  

  private Robot robot;
  private static int initialX;
  private static int initialY;
  private static Direction initialDirection;

  private boolean errDisplaying = false;


  @Override
  public void start(Stage primaryStage) {
    
    robot = new Robot(initialX, initialY, initialDirection);

    primaryStage.setTitle("Robot Game");

    primaryStage.setMinWidth(SCENE_WIDTH);
    primaryStage.setMinHeight(SCENE_HEIGHT);
    primaryStage.setResizable(false);

    mainGrid = new GridPane();
    mainStack = new StackPane();

    mainStack.getChildren().add(mainGrid);  

    mainGrid.setAlignment(Pos.CENTER);
    mainGrid.setPadding(new Insets(10));
    mainGrid.setStyle("-fx-background-color: rgb(236, 203, 168)");

    mainGrid.setHgap(0);
    mainGrid.setVgap(0);


    Text titleText = new Text("Robot Game");
    titleText.setFont(Font.font(FNT_FAMILY, FontWeight.BOLD, TITLE_FNT_SIZE));
    mainGrid.add(titleText, 0, 0, 8, 1);



    buildGameBoard();

    Text instrTitle = new Text("Keyboard Operations:");
    Text instrM = new Text("M: Move forward.");
    Text instrL = new Text("L: Turn to face left.");
    Text instrR = new Text("R: Turn to face right.");

    instrTitle.setFont(Font.font(FNT_FAMILY, FontWeight.BLACK, 
                                 INSTR_FNT_SIZE));
    instrM.setFont(Font.font(FNT_FAMILY, FontWeight.BLACK, INSTR_FNT_SIZE));
    instrL.setFont(Font.font(FNT_FAMILY, FontWeight.BLACK, INSTR_FNT_SIZE));
    instrR.setFont(Font.font(FNT_FAMILY, FontWeight.BLACK, INSTR_FNT_SIZE));

    robotStatusText = new Text("Robot: " + robot.toString());
    robotStatusText.setFont(Font.font(FNT_FAMILY, FontWeight.BOLD, 
                                      STATUS_FNT_SIZE));
    robotStatusText.setTextAlignment(TextAlignment.RIGHT);
    mainGrid.add(robotStatusText, 3, 9, 5, 4);

    scene = new Scene(mainStack);
    mainGrid.add(instrTitle, 0, 9, 4, 1);
    mainGrid.add(instrM, 0, 10, 4, 1);
    mainGrid.add(instrL, 0, 11, 4, 1);
    mainGrid.add(instrR, 0, 12, 4, 1);

    errRect = new Rectangle(SCENE_WIDTH, SCENE_HEIGHT);
    errTexts = new VBox();
    errTexts.setSpacing(30);
    Text errText1 = new Text("Cannot move beyound boundary!");
    Text errText2 = new Text("Press any key to continue");
    
    errText1.setFont(Font.font(FNT_FAMILY, FontWeight.BOLD, ERR_1_FNT_SIZE));
    errText2.setFont(Font.font(FNT_FAMILY, FontWeight.BOLD, ERR_2_FNT_SIZE));

    errTexts.getChildren().addAll(errText1, errText2);
    errTexts.setAlignment(Pos.CENTER);

    errRect.setFill(ERR_RECT_COLOR);

    errRect.setVisible(false);
    errTexts.setVisible(false);
    
    mainStack.getChildren().addAll(errRect, errTexts);




    primaryStage.setScene(scene);
    primaryStage.show();

    handleKeyEvent();
  }

  private void handleKeyEvent() {
    scene.setOnKeyPressed(e ->{
      if(errDisplaying) {
        hideError();
        return;
      }

      switch(e.getCode()) {
        case M:
          boolean success = robot.move();
          if(!success) {
            displayError();
          }
          else {
            updateBoard();
          }
          break;
        
        case L:
          robot.turnLeft();
          updateBoard();
          break;

        case R:
          robot.turnRight();
          updateBoard();
          break;

        default:
          break;
      }
    });
  }
  
  private void displayError() {
    errRect.setVisible(true);
    errTexts.setVisible(true);
    errDisplaying = true;
  }

  private void hideError() {
    errRect.setVisible(false);
    errTexts.setVisible(false);
    errDisplaying = false;  
  }


  private void buildGameBoard() {
    for(int j = 0; j < BOARD_SIZE; ++j) {
      for(int i = 0; i < BOARD_SIZE; ++i) {
        
        StackPane unitStack = new StackPane();

        Rectangle rect = new Rectangle();
        int bindAdd = 5;
        
        rect.setHeight(50);
        rect.setWidth(50);
        rect.setFill(RECT_COLOR);
        rect.setStroke(Color.BLACK);

        ImageView robotImage = new ImageView(
          new Image("RobotIcon.png"));

        robotImage.setFitHeight(50);
        robotImage.setFitWidth(50);

        robotImage.setVisible(false);
        
        unitStack.getChildren().addAll(rect, robotImage);
        StackPane.setMargin(robotImage, new Insets(3));


        if(i == robot.getX() - 1 && j == 8 - robot.getY()) {
          rect.setFill(ROBOT_COLOR);
          robotImage.setVisible(true);
          setImageFacing(robotImage, robot.getDirection());
        }

        mainGrid.add(unitStack, i, j + 1);

        rects[i][j] = rect;
        robotImages[i][j] = robotImage;
        
      }
    }
  }

  private void updateBoard() {
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
    robotStatusText.setText("Robot: " + robot.toString());
  }
  
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
    
    robotImage.setRotate(rotateAngle);

  }

                

  public static void main(String[] args) {
    if(args.length < 3) {
      System.err.println("Too few arguments");
      printUsage();
      System.exit(-1);
    }

    if(args.length > 3) {
      System.err.println("Too much arguments");
      printUsage();
      System.exit(-1);
    }

    initialX = convertToInt(args[0]);
    initialY = convertToInt(args[1]);

    checkBounds(initialX);
    checkBounds(initialY);

    initialDirection = parseDirection(args[2]);
      

    Application.launch(args);
  }

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

  private static void checkBounds(int value) {
    if(value < 1 || value > 8) {
      System.err.println("Input \"" + value + "\" is not in bound [1, 8]");
      printUsage();
      System.exit(-1);
    }
  }

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
      "                       of the robot.\n");
  }
}
