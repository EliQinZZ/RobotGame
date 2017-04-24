#
# Makefile for RobotPath
# Author: Zhizhen (Eli) Qin
# Email: EliQinZZ1997@gmail.com
#

HEADERS		= RobotPath.h, RobotPathStrings.h
C_SRCS		= RobotPath.c

C_OBJS		= $(C_SRCS:.c=.o)
OBJS		= $(C_OBJS)

EXE		= RobotPath

GCC		= gcc

GCC_FLAGS	= -std=c99 -c -g

.c.o:
	@echo "Compiling C cource file ..."
	$(GCC) $(GCC_FLAGS) $<
	@echo ""

$(EXE):	$(OBJS)
	@echo "Linking object modules ..."
	$(GCC) -o $(EXE) $(OBJS)
	@echo ""
	@echo "Compilation Successful!"

#$(C_OBJS):	${HEADERS}

clean:
	@echo "Cleaning up project directory ..."
	rm -f $(EXE) *.o *.gch core a.out
	@echo ""
	@echo "Cleaned."

new:
	make clean
	make

