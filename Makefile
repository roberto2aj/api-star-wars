build:
	mvn clean package
	mkdir apistarwars
	mv test-reports ./apistarwars
	cp ./target/api-star-wars-0.0.1.jar ./apistarwars/ApiStarWars.jar
	rm -rf target
run:
	cd apistarwars && java -jar ./ApiStarWars.jar

