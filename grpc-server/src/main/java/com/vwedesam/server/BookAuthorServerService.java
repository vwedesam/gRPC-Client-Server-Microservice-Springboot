package com.vwedesam.server;

import com.vwedesam.grpc.Author;
import com.vwedesam.grpc.BookAuthorServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

@GrpcService
public class BookAuthorServerService extends BookAuthorServiceGrpc.BookAuthorServiceImplBase {

    @Override
    public void getAuthor(Author request, StreamObserver<Author> responseObserver) {

        String authorId = String.valueOf(request.getAuthorId());

        Optional<Author> author = TempDB.getAuthorsFromTempDb()
                    .stream()
                .filter(author1 -> String.valueOf(author1.getAuthorId()) == authorId)
                        .findFirst();

        responseObserver.onNext(author.get());
        responseObserver.onCompleted();
    }


}
