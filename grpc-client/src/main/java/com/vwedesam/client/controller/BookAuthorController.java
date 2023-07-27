package com.vwedesam.client.controller;

import com.google.protobuf.Descriptors;
import com.vwedesam.client.service.BookAuthorClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class BookAuthorController {

    BookAuthorClientService bookAuthorClientService;

    @GetMapping("/author/{id}")
    public Map<Descriptors.FieldDescriptor, Object> getAuthor(@PathVariable("id") String authorId){
        return bookAuthorClientService.getAuthor(Integer.parseInt(authorId));
    }

    @GetMapping("/books/{id}")
    public List<Map<Descriptors.FieldDescriptor, Object>> getBookByAuthor(@PathVariable("id") String authorId) throws InterruptedException {
        return bookAuthorClientService.getBooksByAuthor(Integer.parseInt(authorId));
    }

    @GetMapping("/books/expensive/book")
    public Map<String, Map<Descriptors.FieldDescriptor, Object>> getExpensiveBook() throws InterruptedException {
        return bookAuthorClientService.getExpensiveBook();
    }

    @GetMapping("/books/author-gender/{gender}")
    public List<Map<Descriptors.FieldDescriptor, Object>> getBooksByAuthorGender(@PathVariable("gender") String gender) throws InterruptedException {
        return bookAuthorClientService.getBooksByAuthorGender(gender);
    }

}
