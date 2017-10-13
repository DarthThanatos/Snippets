gcc -o seccomp_trial  -lseccomp seccomp_trial.c
gcc -o seccomp_code -lseccomp seccomp_code.c
./seccomp_code
# sudo dnf install libseccomp-devel
# strace -c ./seccomp_trial $1 