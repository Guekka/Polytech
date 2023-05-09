all: build

build:
	docker build -t node-app .

clean:
	docker images -a | grep none | awk '{ print $$3; }' | xargs -t 2>/dev/null docker rmi

mrproper: clean
	rm -r node_modules

run: build
# bind mount for localtile
	@docker run -t -i -p 8888:8080 \
	node-app