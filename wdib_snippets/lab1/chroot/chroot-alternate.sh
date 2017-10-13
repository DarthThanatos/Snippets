mkdir bin etc lib var home
ln -s lib /usr/lib64
ldd /bin/sh
cp /bin/sh bin
cp /usr/lib64/libselinux.so.1 lib
cp /lib64/libc.so.6 lib
cp /lib64/ld-linux-x86-64.so.2 lib
cp /usr/lib64/libtinfo.so.5 lib
cp /usr/lib64/libdl.so.2 lib
#mount --bind /proc proc
chroot . /bin/sh
#ll /proc/self/root
#sudo ps a | grep "\-i" | awk '{print $1}' | xargs kill -9