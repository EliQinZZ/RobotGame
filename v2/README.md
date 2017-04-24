# Robot Path Finder 
This is a simple C program that can find the least instructions needed to move
the robot from one position to another, with facing specified, in constant time.

## Getting Started
Here are the informations you need to run the project.

### Prerequisites
```
GCC version 5.3.0+
```

### How to compile
Make sure you are in the RobotGame/v2 folder. Once you are in the folder, type
```
make
```
to compile the program.

## Run the program
Make sure you have successfully compiled the program and the class files are
generated. Then, in the project folder, type
```
./RobotPath <original_x_coord> <original_y_coord> <original_direction> 
<target_x_coord> <target_y_coord> <target_direction>
```
to calculate and display the least instructions needed to move the robot from
one position to another, with facing direction specified. Note that the x and 
y coordinates of both original and target positions must be in bound [1-8], and
that the directions can only be N, E, S or W, for North, East, South and West,
respectively.



## Play with the robot
Once you have successfully launched the program, you will see a bunch or results
displayed on the console. The messges tells you how many instructions are needed
to move the robot from one position to another, and displays all the sequences
of instructions that matches the calculated number.

## Example Normal Output
```
$ ./RobotPath 2 3 N 3 4 S
The minimum steps required to reach the target is 4.
Possible path(s) with minimum steps:
Action - 1: MRMR.
No more possible actions with minimum step 4.
```
```
$ ./RobotPath 2 3 N 3 2 N
The minimum steps required to reach the target is 6.
Possible path(s) with minimum steps:
Action - 1: RMRMLL.
Action - 2: RMRMRR.
Action - 3: LLMLML.
Action - 4: RRMLML.
No more possible actions with minimum step 6.
```

## Example Abnormal Output 
```
$ ./RobotPath 30Mobivity 3 N 3 2 N
Argument "30Mobivity" is not an integer

Usage: ./RobotPath originX originY originDirection targetX targetY
targetDirection

    originX -- an integer representing the X coordinate of robot's original
               position.
               Must be within the limits of [1-8]
    originY -- an integer representing the Y coordinate of robot's original
               position.
               Must be within the limits of [1-8]
    originDirection -- a character representing the original facing of the
                       robot.
                       Must be N, E, S or W (for North, East, South and
                       West, respectively.
    targetX -- an integer representing the X coordinate of robot's target
               position.
               Must be within the limits of [1-8]
    targetY -- an integer representing the Y coordinate of robot's target
               position.
               Must be within the limits of [1-8]
    targetDirection -- a character representing the target facing of the
                       robot.
                       Must be N, E, S or W (for North, East, South and
                       West, respectively.
```

## Author
Zhizhen (Eli) Qin<br />
Email: EliQinZZ1997@gmail.com

