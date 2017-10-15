strace -c -S name -f $@ 2>&1 1>/dev/null \
	| tail -n +3 \
	| head -n -2 > strace_columns

python add_processor.py out

