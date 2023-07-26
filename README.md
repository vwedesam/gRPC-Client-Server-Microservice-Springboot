# gRPC client/server Microservice

A Client-Server gRPC Microservice with Java(spring-boot)

## install dependencies

```sh

    ~/proto$ mvn install
    
    ~/grpc-client$ mvn install
    
    ~/grpc-server$ mvn install

```

## Compile .proto file

This will generate proto buff client binding for java

```sh

    ~/proto$ mvn comiple

```

## .proto compile/build help

https://github.com/grpc/grpc-java

## run server/client application

```shell

     ~/grpc-client$ mvn spring-boot:run
    
    ~/grpc-server$ mvn spring-boot:run

```

## Endpoints

```shell

    http://localhost:8080/author/{id}
    

```

