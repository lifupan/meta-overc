
OBJS = screen-getty agetty-shell

DEST=/

all: $(OBJS)

clean:
	rm -f $(OBJS) *.o

%.o: %.c
	$(CC) $(CFLAGS) -Wall -c $<

screen-getty: screen-getty.o
	$(CC) $(LDFLAGS) -Wall -o $@ $^

agetty-shell: agetty-shell.o
	$(CC) $(LDFLAGS) -Wall -o $@ $^

install: $(OBJS)
	install -d $(DEST)/sbin
	install -d $(DEST)/etc/default
	install -m 0644 screen-gettyrc $(DEST)/etc/default
	install -m 0755 screen-getty $(DEST)/sbin
	install -m 0755 agetty-shell $(DEST)/sbin
