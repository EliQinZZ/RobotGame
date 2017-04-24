/**
 * File Name: RobotPath.h
 * Description: This is the header file of the main driver
 * Author: Zhizhen (Eli) Qin
 */
#ifndef ROBOT_PATH_H
#define ROBOT_PATH_H

#include <stdlib.h>
#include <stdio.h>
#include <limits.h>
#include <string.h>
#include <errno.h>

#define EXPECTED_ARGS 6   // Expected # of arguments
#define ORI_X_INDEX   1   // Index of original x in argv
#define ORI_Y_INDEX   2   // Index of original y in argv
#define ORI_DIR_INDEX 3   // Index of original direction in argv
#define TAR_X_INDEX   4   // Index of target x in argv
#define TAR_Y_INDEX   5   // Index of target y in argv
#define TAR_DIR_INDEX 6   // Index of target direction in argv
#define BASE          10  // Base for number converting
#define LOW_BOUND     1   // Low bound of location
#define HIGH_BOUND    8   // High bound of location

// Struct direction to store the direction facing
struct direction {
  short h;
  short v;
};

// Struct status to store the status of the robot
struct status {
  short x;
  short y;
  struct direction dir;
};

// Struct path to store all the paths found
struct path {
  short num_of_paths;
  char** paths;
};

/*
 * Function name: calculate_shortest_path
 * Description: Calculate the shortest path between one status and another
 * Parameters:
 *    - begin: the beginning status
 *    - dest:  the destination status
 * Side Effects: None.
 * Error Conditions: None.
 * Return value: the shortest path between two status
 */
short calculate_shortest_path(struct status begin, struct status dest);

/*
 * Function name: calculate_dir_dis
 * Description: Calculate # of instructions needed to change from one direction
 *              to another
 * Parameters:
 *    - from: the beginning direction
 *    - to:   the destination direction
 * Side Effects: None.
 * Error Conditions: None.
 * Return value: # of instructions needed to change from one direction to 
 *               another.
 */
short calculate_dir_dis(struct direction from, struct direction to);

/*
 * Function name: construct_shortest_path
 * Description: Construct the shortest path between one status to another
 * Parameters:
 *    - begin: the beginning status
 *    - dest:  the destination status
 * Side Effects: None.
 * Error Conditions: None.
 * Return value: A struct path storing the path information.
 */
struct path construct_shortest_path(struct status begin, struct status dest);

/*
 * Function name: perform_turning
 * Description: Perform turning action from one direction to another, and record
 *              the actions into path
 * Parameters:
 *    - path: the path to be populated
 *    - from: the beginning direction
 *    - to:   the destination direction
 * Side Effects: None.
 * Error Conditions: None.
 * Return value: None.
 */
void perform_turning(struct path* path, 
                     struct direction from, struct direction to);

/*
 * Function name: perform_moving
 * Description: Perform moving action for a specified number of times.
 * Parameters:
 *    - path:         the path to be populated
 *    - num_steps:    # of move instructions needed
 * Side Effects: None.
 * Error Conditions: None.
 * Return value: None.
 */
void perform_moving(struct path* path, int num_steps);

/*
 * Function name: merge_paths
 * Description: Merge two path structs
 * Parameters:
 *    - path_1: the first path struct
 *    - path_2: the second path struct
 * Side Effects: None.
 * Error Conditions: None.
 * Return value: A struct path storing the merged information of two paths.
 */
struct path merge_paths(struct path path_1, struct path path_2);

/*
 * Function name: parse_number
 * Description: Convert a string to a number
 * Parameters:
 *    - result: the result to be populated
 *    - arg:    the string to be converted
 *    - prog:   the name of the program
 * Side Effects: None.
 * Error Conditions:
 *    - args is not an integer
 *    - the integer stored in args is too big to be converted
 *    - the converted integer is out of bound [1-8]
 * Return value: 
 *    - 1 indication success
 *    - 0 indication failure
 */
int parse_number(short* result, char* arg, char* prog);

/*
 * Function name: parse_dir
 * Description: Convert a string to a direction
 * Parameters:
 *    - result: the result to be populated
 *    - arg:    the string to be converted
 *    - prog:   the name of the program
 * Side Effects: None.
 * Error Conditions:
 *    - arg doesn't store a valid direction
 * Return value: 
 *    - 1 indication success
 *    - 0 indication failure
 */
int parse_dir(struct direction* dir, char* arg, char* prog);

/*
 * Function name: print_usage
 * Description: Print the usage of the program
 * Parameters:
 *    - prog:   the name of the program
 * Side Effects: the usage of the program is printed
 * Error Conditions: None.
 * Return value: None.
 */
void print_usage(char* prog);

/*
 * Function name: print_result
 * Description: Print the result of the program
 * Parameters:
 *    - prog:   the name of the program
 * Side Effects: the result of the program is printed
 * Error Conditions: None.
 * Return value: None.
 */
void print_result(short min_steps, struct path result_path);

#endif // ROBOT_PATH_H
