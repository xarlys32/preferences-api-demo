# preferences-api-demo
## Project structure
The Projects uses an aproach of Hexagonal Architecture with some DDD elements.
I create this project using 2 principal domains, USER and EVENT, like the part of event could be another microservice that consumes a process of update preferences by user part, I use Axon Framework.
Axon Framework allowed me to comunicate the different part of my system through messages of Query and Command and we could use it with another microservices implementing and extra tool like RabbitMq. In this case is for internal comunication and making distinction between layers.

For Database I use Mongo DB where I can store the events changes embedded in a user historical registry.

Testing is being develop with Junit and mockito but I only cover a few cases of example in usescases and controllers part for both domains.

## Demo controller
I include 4 endpoint (3 for user with get, create and update and another for event to get the info more easily in this demo)
Some images:
![imagen](https://github.com/user-attachments/assets/41a4ecda-7fb8-49ed-8c3c-61c269ba5b81)
![imagen](https://github.com/user-attachments/assets/ee353c74-c786-44d2-8bf9-7feb3e66f456)


## Project set up
Before run the app, is necesary to have docker for run a mongo image and follow next steps:
### Download with docker
docker run -d --name mongodb-container -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=adminpass mongo
### Run container 
docker start mongodb-container
### Execute internal scripts 
    docker exec -it mongodb-container mongosh "mongodb://admin:adminpass@localhost:27017"
    use eventsDatabase
    db.createUser({
      user: "preferencesUser",
      pwd: "preferencesPass",
      roles: [{ role: "readWrite", db: "eventsDatabase" }]
    })
    db.createCollection("userPreferences")
    db.createCollection("userEventsHistory")

After generate the DB you can run the app and use Swagger for testing in the url -> http://localhost:8080/swagger-ui/index.html


## Observability
For Observability I think that a good aproach is to use Grafana dashboard with Loki, Mimir and Tempo, and OpenTelemetry to recollect that info for Grafana.

## CI/CD
I use GitHub actions which allow me to execute workflows like run all test in the moment that you create a Pull request to ensure that the new code works. Another use is to periodically execute some workflows to delete cache or refresh data(in my case for 
generate statica pages with updated info)
