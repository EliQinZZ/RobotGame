#include <stdlib.h>
#include <stdio.h>
#include <limits.h>
#include <string.h>
#include <errno.h>

#define EXPECTED_ARGS 6
#define ORI_X_INDEX 	1
#define ORI_Y_INDEX 	2
#define ORI_DIR_INDEX 3
#define TAR_X_INDEX 	4
#define TAR_Y_INDEX 	5
#define TAR_DIR_INDEX 6
#define BASE					10
#define LOW_BOUND			1
#define HIGH_BOUND		8


struct direction {
	short h;
	short v;
};

struct status {
	short x;
	short y;
	struct direction dir;
};

struct path {
	short num_of_paths;
	char** paths;
};

short calculate_shortest_path(struct status begin, struct status dest);
short calculate_dir_dis(struct direction from, struct direction to);
struct path construct_shortest_path(struct status begin, struct status dest);
void perform_turning(struct path* path, 
										 struct direction from, struct direction to);
void perform_moving(struct path* path, int num_steps);
struct path merge_paths(struct path path_1, struct path path_2);
int parse_number(short* result, char* arg, char* prog);
int parse_dir(struct direction* dir, char* arg, char* prog);
void print_usage(char* prog);
void print_result(short min_steps, struct path result_path);
