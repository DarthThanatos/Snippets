strace -c -S name -f "${@:2}" 2>&1 1>/dev/null \
	| tail -n +3 \
	| head -n -2 \
	| awk '{printf "ADD(%s);\n",$(NF)}'> $1