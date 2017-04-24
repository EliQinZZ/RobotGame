#ifndef ROBOT_PATH_STRINGS_H
#define ROBOT_PATH_STRINGS_H

#define TOO_FEW_ARGS  "%s: too few arguments\n"
#define EXTRA_ARGS    "%s: too many arguments\n"
#define NOT_INTEGER   "Argument \"%s\" is not an integer\n"
#define NOT_IN_BOUND  "Number \"%d\" is not in bound [%d - %d]\n"
#define NOT_DIRECTION "Argument \"%s\" is not a valid direction\n"
#define MINIMUM_STEP  "The minimum steps required to reach the target is %d.\n"
#define DISPLAY_PATH  "Possible path(s) with minimum steps:\n"
#define PATH          "Action - %d: %s.\n"
#define NO_MORE       "No more possible actions with minimum step %d.\n"
#define USAGE "\nUsage: %s originX originY originDirection targetX targetY\n"\
"targetDirection\n\n"\
"    originX -- an integer representing the X coordinate of robot's original\n"\
"               position.\n"\
"               Must be within the limits of [1-8]\n"\
"    originY -- an integer representing the Y coordinate of robot's original\n"\
"               position.\n"\
"               Must be within the limits of [1-8]\n"\
"    originDirection -- a character representing the original facing of the\n"\
"                       robot.\n"\
"                       Must be N, E, S or W (for North, East, South and\n"\
"                       West, respectively.\n"\
"    targetX -- an integer representing the X coordinate of robot's target\n"\
"               position.\n"\
"               Must be within the limits of [1-8]\n"\
"    targetY -- an integer representing the Y coordinate of robot's target\n"\
"               position.\n"\
"               Must be within the limits of [1-8]\n"\
"    targetDirection -- a character representing the target facing of the\n"\
"                       robot.\n"\
"                       Must be N, E, S or W (for North, East, South and\n"\
"                       West, respectively.\n"


#endif // ROBOT_PATH_STRINGS_H
