#include "common.h"

#define ADD(x)\
	seccomp_rule_add(ctx, SCMP_ACT_ALLOW, SCMP_SYS(x), 0)

int main(int argc, char* argv[], char *envp[]){
	scmp_filter_ctx ctx = seccomp_init(SCMP_ACT_KILL);
	// seccomp_rule_add(ctx, SCMP_ACT_TRACE(157), SCMP_SYS(write), 0);
	ADD(write);
	ADD(read);
	ADD(fstat);
	ADD(mmap);
	ADD(rt_sigaction);
	ADD(rt_sigprocmask);
	ADD(rt_sigreturn);
	ADD(clone);
	ADD(wait4);
	ADD(execve);
	ADD(exit_group);

	ADD(stat);
	ADD(recvmsg);
	ADD(poll);
	ADD(open);
	ADD(futex);
	ADD(read);
	ADD(mmap);
	ADD(close);
	ADD(writev);
	ADD(mprotect);
	ADD(fstat);
	ADD(getdents);
	ADD(access);
	ADD(brk);
	ADD(munmap);
	ADD(fstatfs);
	ADD(lseek);
	ADD(eventfd2);
	ADD(sendto);
	ADD(sendmsg);
	ADD(connect);
	ADD(fcntl);
	ADD(socket);
	ADD(fadvise64);
	ADD(recvfrom);
	ADD(clone);
	ADD(lstat);
	ADD(uname);
	ADD(shmdt);
	ADD(statfs);
	ADD(geteuid);
	ADD(fsync);
	ADD(rename);
	ADD(shmget);
	ADD(shmat);
	ADD(getegid);
	ADD(inotify_rm_watch);
	ADD(rt_sigprocmask);
	ADD(getsockname);
	ADD(fallocate);
	ADD(rt_sigaction);
	ADD(mremap);
	ADD(shmctl);
	ADD(pwrite64);
	ADD(inotify_add_watch);
	ADD(chmod);
	ADD(getpeername);
	ADD(shutdown);
	ADD(getresuid);
	ADD(getresgid);
	ADD(getsockopt);
	ADD(inotify_init1);
	ADD(bind);
	ADD(clock_getres);
	ADD(setsockopt);
	ADD(execve);
	ADD(arch_prctl);
	ADD(getrlimit);
	ADD(set_tid_address);
	ADD(set_robust_list);

	seccomp_load(ctx);
	printf("Hello\n");
	system("ls -l");		
	seccomp_release(ctx);

}