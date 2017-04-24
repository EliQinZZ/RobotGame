# Robot Game GUI
This program uses javafx to build a graphics user interface for users to play
around with the robot.

## Getting Started
Here are the informations you need to run the project.

### Prerequisites
```
java 1.8.0+
javaFX 8
```

### How to compile
Make sure you are in the RobotGame/v1 folder. Once you are in the folder, type
```
javac RobotGame.java
```
to compile the program.

## Run the program
Make sure you have successfully compiled the program and the class files are
generated. Then, in the project folder, type
```
java RobotGame <original_x_coord> <original_y_coord> <original_direction>
```
to start the program with the robot start on the specified location with
specified facing. Note that the x and y coordinates must be in bound [1-8], and
that the direction can only be N, E, S or W, for North, East, South and West,
respectively.

## Play with the robot
Once you have successfully launched the program, you can use kayboard to 
manupulate the robot. There are three available instructions:
```
M: Move forward;
L: Turn to face left;
R: Turn to face right.
```
Note that once you try to move beyond the boundary, the program will complain 
with a error message. Don't be afraid, becuase you can eliminate the error
message by pressing any key on your keyboard.

## Example Abnormal Output
```
$ java RobotGame 
Too few arguments

Usage: RobotGame originX originY originDirection
    originX -- an integer representing the X coordinate of robot's 
               original position.
               Must be within the limits of [1-8]
    originY -- an integer representing the Y coordinate of robot's 
               original position.
               Must be within the limits of [1-8]
    originDirection -- a character representing the original facing 
                       of the robot.
                       Must be N, E, S or W (for North, East, South 
                       and West, respectively).
```
```
$ java RobotGame 3 3 Mobivity
Input "Mobivity" is not a valid direction

Usage: RobotGame originX originY originDirection
    originX -- an integer representing the X coordinate of robot's 
               original position.
               Must be within the limits of [1-8]
    originY -- an integer representing the Y coordinate of robot's 
               original position.
               Must be within the limits of [1-8]
    originDirection -- a character representing the original facing 
                       of the robot.
                       Must be N, E, S or W (for North, East, South 
                       and West, respectively).
```

## Author
Zhizhen (Eli) Qin<br />
Email: EliQinZZ1997@gmail.com
