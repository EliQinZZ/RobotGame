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

## Author
Zhizhen (Eli) Qin<br />
Email: EliQinZZ1997@gmail.com

