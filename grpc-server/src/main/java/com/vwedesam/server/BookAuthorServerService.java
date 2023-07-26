package com.vwedesam.server;

import com.vwedesam.grpc.Author;
import com.vwedesam.grpc.BookAuthorServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@GrpcService
public class BookAuthorServerService extends BookAuthorServiceGrpc.BookAuthorServiceImplBase {

    private static Logger log = LoggerFactory.getLogger(BookAuthorServerService.class);

    @Override
    public void getAuthor(Author request, StreamObserver<Author> responseObserver) {

        int authorId = request.getAuthorId();

        log.info(String.valueOf(authorId));

        Optional<Author> author = TempDB.getAuthorsFromTempDb()
                    .stream()
                .filter(author1 -> author1.getAuthorId() == authorId)
                        .findFirst();

        Author author1 = Author.newBuilder().build();

        if(author.isPresent()){
            author1 = author.get();
        }

        responseObserver.onNext(author1);
        responseObserver.onCompleted();
    }


}
