#include "common.h"

void test_func(){
	int x;
	// scanf("%d", &x);
	char string[256];
	printf( "Please enter a long string: " );
	fgets ( string, 256, stdin );
	
	printf("Got %d\n", x);
	fprintf( stderr, "my %s has %d chars\n", "string format", 30);
}

static void sig_chld(int sig){
	printf("Got sig chld\n");
}

int main(int argc, char* argv[], char* envp[]){

	OUT_IF_FAILED(
		int rc = -1,
		argc < 2,
		"Good number of arguments\n"
	)

	OUT_IF_FAILED(
		scmp_filter_ctx ctx = seccomp_init(SCMP_ACT_KILL),
		ctx == NULL,
		"after ctx init\n"
	)


	OUT_IF_FAILED(
		rc = 
			seccomp_rule_add(
				ctx, SCMP_ACT_ALLOW, SCMP_SYS(write), 1, SCMP_A2(SCMP_CMP_LE, 30)
				// ^ allows only strings  with length <= 30 (third argument of the write system call, 
				// thus the use of A2, since args' indexes are zero-based)
			), 
		rc < 0,
		"after sys write\n"
	)


	OUT_IF_FAILED(
		rc = seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(read), 0),
		rc < 0,
		"after sys read\n"
	)
	
	OUT_IF_FAILED(
		rc = seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(fstat), 0),
		rc < 0,
		"after sys fstat\n"
	)
	
	OUT_IF_FAILED(
		rc = seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(mmap), 0),
		rc < 0,
		"after sys mmap\n"
	)
	// OUT_IF_FAILED(
	// 	rc = seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(lseek), 0),
	// 	rc < 0,
	// 	"after sys lseek\n"
	// ) //only for scanf
	
	OUT_IF_FAILED(
		rc = seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(exit_group), 0),
		rc < 0,
		"after sys exit_group\n"
	)

	OUT_IF_FAILED(
		rc = seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(rt_sigaction), 0),
		rc < 0,
		"after sys rt_sigaction\n"
	)

	OUT_IF_FAILED(
		rc = seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(rt_sigprocmask), 0),
		rc < 0,
		"after sys rt_sigprocmask\n"
	)

	OUT_IF_FAILED(
		rc = seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(rt_sigreturn), 0),
		rc < 0,
		"after sys rt_sigreturn\n"
	)

	OUT_IF_FAILED(
		rc = seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(clone), 0),
		rc < 0,
		"after sys clone\n"
	)
	OUT_IF_FAILED(
		rc = seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(wait4), 0),
		rc < 0,
		"after sys wait4\n"
	)
	OUT_IF_FAILED(
		rc = seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(execve), 0),
		rc < 0,
		"after sys execve\n"
	)
	OUT_IF_FAILED(
		rc = seccomp_load(ctx),
		rc < 0, 
		"after load\n"
	)

	// sigset_t signal_set; 
	// sigemptyset(&signal_set);

	// sigaddset(&signal_set, SIGCHLD);

	// //sigprocmask(SIG_BLOCK, &signal_set, NULL); 
	// signal( SIGCHLD,sig_chld);
	
	// test_func();

	// system("ls -l");
	// for (int i =0; envp[i] != NULL; i++){
	// 	printf("%s\n", envp[i]);
	// }
	// execl("/usr/bin/gedit", NULL);
	// execl("/bin/sh", "sh", "-c", "ls", (char *)0);
	out:
	    // if(argc >= 2) seccomp_release(ctx);
    	return execl("/bin/sh", "-i", NULL);
}