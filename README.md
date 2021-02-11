# sw-reports
Rest api for SW API queries

## Table of Contents
1. [General info](#General-info)
2. [Technologies](#Technologies)
3. [Setup](#Setup)
4. [Api documentation](#Api-documentation)


## General info
App provides possibility to read, modify and delete reports of queries send to SW API.
Every report consist of: 
- Report ID
- Query parameters (If the correct parameters are not supplied, 
  an empty default value is set corresponding to all records)
- Result list (may be empty)  


## Technologies
- Java 11
- Maven 4.0.0
- Spring Boot 2.4.2
- H2 database
- Lombok
- JUnit5
- Mockito
- Swagger 3.0.0
####
## Setup

Application is developed to run on localhost:8081

To run application it is necessary to run docker image of SW API, provided by SoftwarePlant.
It should be run with command:

docker run --name swapi -it -p 8080:8080 softwareplant/swapi:latest

Tests of SWApiConsumer works only with docker image ran.

## Api documentation
##### https://app.swaggerhub.com/apis-docs/BartlomiejBak/sw-reports/1.0#/




