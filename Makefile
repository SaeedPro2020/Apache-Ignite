# Define a variable for the Maven command
MVN = mvn

# Define the main class
MAIN_CLASS = com.example.Main

# Default target
.PHONY: run
.PHONY: build

run:
	$(MVN) spring-boot:run

build:
	$(MVN) clean install
