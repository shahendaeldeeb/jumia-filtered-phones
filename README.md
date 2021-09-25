# spring-boot-docker-jumia-phones
This is a single page application that uses the database (SQLite 3) to list and categorize country phone numbers.

Phone numbers are categorized by country, state (valid or not valid), country code and number.

The page should render a list of all phone numbers available in the DB. It should be possible to filter by country and state.

#Prerequisites:
- [JDK 11](https://www.oracle.com/java/technologies/downloads/#java11)
- [Maven 3](https://maven.apache.org)
- [Docker](https://docs.docker.com/get-docker/)

#How to run this application :

```sh
## Install dependency & create jar file
mvn clean install

## Build Docker images from a Dockerfile
docker build -t jumia/phones .

## Creates a container from a given image and starts the container
docker run -p 8080:8080 jumia/phones

```

Once the service is up, the following URLs will be available

Address | Description
--- | ---
http://localhost:8080/ | Categorized phone numbers page.
