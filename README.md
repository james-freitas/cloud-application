<h1 align="center">Spring Boot Cloud Application</h1>

## :heavy_check_mark: Requirements
 - Java Development Kit 8 (jdk8)

## :bar_chart: Observability Tools
 - Prometheus: http://localhost:9090/
   - Check the application health on _Status_ > _Targets_
   - > In `docker/docker-compose.yml` replace **172.17.0.1** by your local machine IP or remove the `extra_hosts` configuration

## :airplane: How to run locally
```shell

# start all dependencies
docker-compose -f ./docker/docker-compose.yml up

## start web application
./gradlew bootRun
  
```