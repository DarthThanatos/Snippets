gcc -o seccomp_trial  -lseccomp seccomp_trial.c
gcc -o seccomp_code -lseccomp seccomp_code.c
# strace -c -S name -f ./seccomp_code 2>&1 1>/dev/null | tail -n +3 | head -n -2 > strace_columns
./strace_syscalls.sh ./seccomp_code
# sudo dnf install libseccomp-devel
# strace -c ./seccomp_trial $1 