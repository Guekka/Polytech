all: build

build:
	docker build -t node-client .

clean:
	docker images -a | grep none | awk '{ print $$3; }' | xargs -t 2>/dev/null docker rmi

mrproper: clean
	rm -r node_modules

run: build
	docker run -t -i --net=mynetwork node-client
	docker exec -t -i node-client /bin/sh