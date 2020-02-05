clean:
	./gradlew clean
.PHONY: clean

build: clean
	./gradlew clean build
.PHONY: build

run: build
	docker-compose up -d
	java -jar build/libs/*.jar
.PHONY: run

stop:
	docker-compose down
.PHONY: stop
