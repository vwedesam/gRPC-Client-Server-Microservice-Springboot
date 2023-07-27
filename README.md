# gRPC client/server Microservice

A Client-Server gRPC Microservice with Java(spring-boot)

## RPC implementation/Type

1. `Unary RPC`: the client sends a single request and receives a single response.
2. `Server streaming RPC`: the client sends a single request; in return, the server sends a stream of messages.
3. `Client streaming RPC`: the client sends a stream of messages, and the server responds with a single message.
4. `Bidirectional streaming RPC`: in bidirectional streaming, both the client and server send a stream of messages.

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

## Client Endpoints

```shell

    http://localhost:8080/author/{id}
    http://localhost:8080/books/{authorId}

```

