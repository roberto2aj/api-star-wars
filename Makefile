build:
	echo "Building Docker container"
	docker build -t apistarwars .
	echo "Container build successfully"
run:
	docker run -p 8080:8080 apistarwars


