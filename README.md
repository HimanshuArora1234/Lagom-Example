# Lagom Java Microservices

A java microservice project to explore Lightbend's new Framework Lagom and also to work little bit with event sourcing.
The events are persisted in the embedded cassandra database which comes by default with Lagom.

## To run
The following command runs all the microservices present in the project.

`sbt runAll`

## Endpoints to test the microservice API

`curl http://localhost:9000/api/user/1`

`curl -H "Content-Type: application/json" -X POST -d '{"id":"1","name":"xyz","age":20}' http://localhost:9000/api/user`
