package com.vwedesam.client.service;

import com.google.protobuf.Descriptors;
import com.vwedesam.db.TempDB;
import com.vwedesam.grpc.Author;
import com.vwedesam.grpc.Book;
import com.vwedesam.grpc.BookAuthorServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class BookAuthorClientService {

    final String gRPC_SERVER_CHANEL = "grpc-vwedesam-server";

    @GrpcClient(gRPC_SERVER_CHANEL)
    BookAuthorServiceGrpc.BookAuthorServiceBlockingStub synchronousClient;

    @GrpcClient(gRPC_SERVER_CHANEL)
    BookAuthorServiceGrpc.BookAuthorServiceStub asynchronousClient;

    public Map<Descriptors.FieldDescriptor, Object> getAuthor(int authorId) {
        Author authorRequest = Author.newBuilder().setAuthorId(authorId).build();

        Author authorResponse = synchronousClient.getAuthor(authorRequest);

        return authorResponse.getAllFields();
    }

    public List<Map<Descriptors.FieldDescriptor, Object>> getBooksByAuthor(int authorId) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Author authorRequest = Author.newBuilder().setAuthorId(authorId).build();
        List<Map<Descriptors.FieldDescriptor, Object>> response = new ArrayList<>();

        StreamObserver<Book> responseObserver = new StreamObserver<Book>() {
            @Override
            public void onNext(Book book) {
                response.add(book.getAllFields());
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        };

        // new StreamObserver<Book>() {} is a callback function
        // it get called once the asynchronousClient.getBooksByAuthor request is successful
        asynchronousClient.getBooksByAuthor(authorRequest, responseObserver);

        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);

        return await ? response : Collections.emptyList();
    }

    public Map<String, Map<Descriptors.FieldDescriptor, Object>> getExpensiveBook() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        final Map<String, Map<Descriptors.FieldDescriptor, Object>> response = new HashMap<>();

        StreamObserver<Book> responseObserver = new StreamObserver<Book>() {
            @Override
            public void onNext(Book book) {
                response.put("ExpensiveBook", book.getAllFields());
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        };

        // new StreamObserver<Book>() {} is a callback function
        // it get called once the asynchronousClient.getBooksByAuthor request is successful
        StreamObserver<Book> requestObserver = asynchronousClient.getExpensiveBook(responseObserver);

        TempDB.getBooksFromTempDb().forEach(requestObserver::onNext);
        requestObserver.onCompleted();

        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);

        return await ? response : Collections.emptyMap();

    }

    public List<Map<Descriptors.FieldDescriptor, Object>> getBooksByAuthorGender(String gender) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        List<Map<Descriptors.FieldDescriptor, Object>> response = new ArrayList<>();

        StreamObserver<Book> responseObserver = new StreamObserver<Book>() {
            @Override
            public void onNext(Book book) {
                response.add(book.getAllFields());
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        };

        StreamObserver<Book> requestObserver = asynchronousClient.getBooksByAuthorGender(responseObserver);

        TempDB.getAuthorsFromTempDb()
                .stream()
                .filter(author -> author.getGender().equalsIgnoreCase(gender) )
                .forEach(author ->
                        requestObserver.onNext(
                            Book.newBuilder().setAuthorId(author.getAuthorId()).build()
                        )
                );

        requestObserver.onCompleted();

        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);

        return await ? response : Collections.emptyList();

    }

}
