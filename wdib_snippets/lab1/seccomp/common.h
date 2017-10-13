#include <stdlib.h>
#include <stdio.h>
#include <seccomp.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <stddef.h>
#include <err.h>
#include <unistd.h>
#include <signal.h>


#ifndef COMMON_H_
#define COMMON_H_

#define OUT_IF_FAILED(statement, predicate, info)\
	statement;\
	if (predicate){\
		printf("Outing\n");\
		goto out;\
	}\
	printf(info);

#endif /* #ifndef COMMON_H_ */
