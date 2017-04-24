#include "RobotPath.h"
#include "RobotPathStrings.h"

int main(int argc, char * argv[]) {

	argc--;

	if(argc != EXPECTED_ARGS) {
		if(argc < EXPECTED_ARGS) {
			fprintf(stderr, TOO_FEW_ARGS, argv[0]);
			print_usage(argv[0]);
		}
		else {
			fprintf(stderr, EXTRA_ARGS, argv[0]);
			print_usage(argv[0]);
		}
		return EXIT_FAILURE;
	}

	struct status begin, dest;

	if(!parse_number(&begin.x, argv[ORI_X_INDEX], argv[0])) return EXIT_FAILURE;
	if(!parse_number(&begin.y, argv[ORI_Y_INDEX], argv[0])) return EXIT_FAILURE;
	if(!parse_dir(&begin.dir, argv[ORI_DIR_INDEX], argv[0])) return EXIT_FAILURE;
	if(!parse_number(&dest.x, argv[TAR_X_INDEX], argv[0])) return EXIT_FAILURE;
	if(!parse_number(&dest.y, argv[TAR_Y_INDEX], argv[0])) return EXIT_FAILURE;
	if(!parse_dir(&dest.dir, argv[TAR_DIR_INDEX], argv[0])) return EXIT_FAILURE;

	short min_steps = calculate_shortest_path(begin, dest);

	struct path result_path = construct_shortest_path(begin, dest);
	
	print_result(min_steps, result_path);

}

int parse_number(short* result, char* arg, char* prog) {
	char* endptr;
	errno = 0;

	*result = strtol(arg, &endptr, BASE);

	if(*endptr) {
		fprintf(stderr, NOT_INTEGER, arg);
		print_usage(prog);
		return 0;
	}

	if(errno != 0) {
		perror(arg);
		print_usage(prog);
		return 0;
	}

	if(*result < LOW_BOUND || *result > HIGH_BOUND) {
		fprintf(stderr, NOT_IN_BOUND, *result, LOW_BOUND, HIGH_BOUND);
		print_usage(prog);
		return 0;
	}
	
	return 1;
}

int parse_dir(struct direction* dir, char* arg, char* prog) {
	if(strlen(arg) != 1) {
		fprintf(stderr, NOT_DIRECTION, arg);
		print_usage(prog);
		return 0;
	}

	switch(arg[0]) {
		case 'N':
			dir->h = 0;
			dir->v = 1;
			break;
		case 'E':
			dir->h = 1;
			dir->v = 0;
			break;
		case 'S':
			dir->h = 0;
			dir->v = -1;
			break;
		case 'W':
			dir->h = -1;
			dir->v = 0;
			break;
		default:
			fprintf(stderr, NOT_DIRECTION, arg);
			print_usage(prog);
			return 0;
	}

	return 1;

}

void print_usage(char* prog) {
	fprintf(stderr, USAGE, prog);
}

short calculate_shortest_path(struct status begin, struct status dest) {
	short delta_x = abs(begin.x - dest.x);
	short delta_y = abs(begin.y - dest.y);
	
	short dir_dis = SHRT_MAX;

	short distance = delta_x + delta_y;

	if(delta_x > 0 || delta_y > 0) {
		distance++;
	}
	
	struct direction relative_dir;

	relative_dir.h = (dest.x == begin.x) ? 
									 0 : ((dest.x - begin.x) / delta_x);
	relative_dir.v = (dest.y == begin.y) ?
									 0 : ((dest.y - begin.y) / delta_y);


	if(relative_dir.h != 0 && relative_dir.v != 0) {
		struct direction dir_1, dir_2;

		dir_1.h = relative_dir.h;
		dir_1.v = 0;

		dir_2.h = 0;
		dir_2.v = relative_dir.v;

		short dir_dis_1, dir_dis_2;

		dir_dis_1 = calculate_dir_dis(begin.dir, dir_1) + 
								calculate_dir_dis(dir_2, dest.dir);

		dir_dis_2 = calculate_dir_dis(begin.dir, dir_2) + 
								calculate_dir_dis(dir_1, dest.dir);
		
		dir_dis = dir_dis_1 < dir_dis_2 ? dir_dis_1 : dir_dis_2;
	}

	else if(relative_dir.h == 0 && relative_dir.v == 0) {
		dir_dis = calculate_dir_dis(begin.dir, dest.dir);
	}
	
	else {
		dir_dis = calculate_dir_dis(begin.dir, relative_dir) +
							calculate_dir_dis(relative_dir, dest.dir);
	}

	return distance + dir_dis;
}

short calculate_dir_dis(struct direction from, struct direction to) {
	if(from.h == to.h && from.v == to.v) {
		return 0;
	}

	if(from.h == 0 && to.h == 0 ||
		 from.v == 0 && to.v == 0) {

		return 2;
	}

	return 1;
}

struct path construct_shortest_path(struct status begin, struct status dest) {
	struct path result_path;
	struct direction relative_dir;
	result_path.num_of_paths = 0;

	relative_dir.h = (dest.x == begin.x) ? 
									 0 : ((dest.x - begin.x) / abs(begin.x - dest.x));
	relative_dir.v = (dest.y == begin.y) ?
									 0 : ((dest.y - begin.y) / abs(begin.y - dest.y));

	if(relative_dir.h != 0 && relative_dir.v != 0) {
		struct direction dir_1, dir_2;

		dir_1.h = relative_dir.h;
		dir_1.v = 0;

		dir_2.h = 0;
		dir_2.v = relative_dir.v;

		short dir_dis_1, dir_dis_2;

		dir_dis_1 = calculate_dir_dis(begin.dir, dir_1) + 
								calculate_dir_dis(dir_2, dest.dir);

		dir_dis_2 = calculate_dir_dis(begin.dir, dir_2) + 
								calculate_dir_dis(dir_1, dest.dir);
		
		struct path path_1, path_2;

		path_1.num_of_paths = path_2.num_of_paths = 0;

		perform_turning(&path_1, begin.dir, dir_1);
		perform_moving(&path_1, abs(dest.x - begin.x));
		perform_turning(&path_1, dir_1, dir_2);
		perform_moving(&path_1, abs(dest.y - begin.y));
		perform_turning(&path_1, dir_2, dest.dir);

		perform_turning(&path_2, begin.dir, dir_2);
		perform_moving(&path_2, abs(dest.y - begin.y));
		perform_turning(&path_2, dir_2, dir_1);
		perform_moving(&path_2, abs(dest.x - begin.x));
		perform_turning(&path_2, dir_1, dest.dir);

		if(dir_dis_1 == dir_dis_2) {
			result_path = merge_paths(path_1, path_2);
		}
		else {
			result_path = dir_dis_1 < dir_dis_2 ? path_1 : path_2;
		}
	}

	else if(relative_dir.h == 0 && relative_dir.v == 0) {
		perform_turning(&result_path, begin.dir, dest.dir);
	}

	else {
		perform_turning(&result_path, begin.dir, relative_dir);
		perform_moving(&result_path, abs(dest.x - begin.x + dest.y - begin.y));
		perform_turning(&result_path, relative_dir, dest.dir);
	}

		

	return result_path;
}

void perform_turning(struct path* path, 
										 struct direction from, struct direction to) {
	if(from.v == to.v && from.h == to.h) {
		return;
	}

	if(from.v == 0 && to.v == 0 || from.h == 0 && to.h == 0) {
		if(path->num_of_paths == 0) {
			path->num_of_paths = 2;
			char** temp = malloc(path->num_of_paths * sizeof(char*));
			if(temp != 0) {
				path->paths = temp;
			}
			path->paths[0] = malloc(3);
			path->paths[1] = malloc(3);

			strcpy(path->paths[0], "LL");
			strcpy(path->paths[1], "RR");
		}
		else {
			int ori_num = path->num_of_paths;
			path->num_of_paths *= 2;
			char** temp = realloc(path->paths, path->num_of_paths * sizeof(char*));
			if(temp != 0) {
				path->paths = temp;
			}

			for(int i = ori_num; i < path->num_of_paths; ++i) {
				int length = strlen(path->paths[i - ori_num]);
				path->paths[i] = malloc(
						(length + 2) * sizeof(char) + 1);
				strcpy(path->paths[i], path->paths[i - ori_num]);
				strcat(path->paths[i], "RR");
			}

			for(int i = 0; i < ori_num; ++i) {
				int length = strlen(path->paths[i]);
				path->paths[i] = realloc(path->paths[i], 
						(length + 2) * sizeof(char) + 1);
				strcat(path->paths[i], "LL");
			}
		}
	}

	else {
		if(path->num_of_paths == 0) {
			path->num_of_paths = 1;
			char** temp = malloc(path->num_of_paths * sizeof(char*));
			if(temp != 0) {
				path->paths = temp;
			}
			path->paths[0] = malloc(2);
		}

		if(from.h ==  0 && from.v ==  1 && to.h ==  1 && to.v ==  0 ||
			 from.h ==  1 && from.v ==  0 && to.h ==  0 && to.v == -1 ||
			 from.h ==  0 && from.v == -1 && to.h == -1 && to.v ==  0 ||
			 from.h == -1 && from.v ==  0 && to.h ==  0 && to.v ==  1) {
			
			for(int i = 0; i < path->num_of_paths; ++i) {
				path->paths[i] = realloc(path->paths[i], 
														(strlen(path->paths[i]) + 1) * sizeof(char) + 1);
				strcat(path->paths[i], "R");
			}
		}

		else {
			for(int i = 0; i < path->num_of_paths; ++i) {
				path->paths[i] = realloc(path->paths[i], 
														(strlen(path->paths[i]) + 1) * sizeof(char) + 1);
				strcat(path->paths[i], "L");
			}
		}
	}
}

void perform_moving(struct path* path, int num_steps) {
	if(num_steps == 0) {
		return;
	}
	for(int j = 0; j < num_steps; ++j) {
		if(path->num_of_paths == 0) {
			path->num_of_paths = 1;
			path->paths = malloc(sizeof(char*));
			path->paths[0] = malloc(2);
			strcpy(path->paths[0], "M");
		}
		else {
			for(int i = 0; i < path->num_of_paths; ++i) {
				path->paths[i] = realloc(path->paths[i],
																(strlen(path->paths[i]) + 1) * sizeof(char) + 1);
				strcat(path->paths[i], "M");
			}
		}
	}
}

struct path merge_paths(struct path path_1, struct path path_2) {
	struct path result_path;
	
	result_path.num_of_paths = path_1.num_of_paths + path_2.num_of_paths;

	result_path.paths = malloc(result_path.num_of_paths * sizeof(char*));

	for(int i = 0; i < path_1.num_of_paths; ++i) {
		result_path.paths[i] = malloc(
				(strlen(path_1.paths[i]) + 1) * sizeof(char) + 1);
		strcpy(result_path.paths[i], path_1.paths[i]);
	}
	
	for(int i = 0; i < path_2.num_of_paths; ++i) {
		result_path.paths[i + path_1.num_of_paths] = malloc(
				(strlen(path_2.paths[i]) + 1) * sizeof(char) + 1);
		strcpy(result_path.paths[i + path_1.num_of_paths], path_2.paths[i]);
	}

	return result_path;
	
}

void print_result(short min_steps, struct path result_path) {
	fprintf(stdout, MINIMUM_STEP, min_steps);
	fprintf(stdout, DISPLAY_PATH);

	for(int i = 0; i < result_path.num_of_paths; ++i) {
		fprintf(stdout, PATH, i + 1, result_path.paths[i]);
	}

	fprintf(stdout, NO_MORE, min_steps);
}
