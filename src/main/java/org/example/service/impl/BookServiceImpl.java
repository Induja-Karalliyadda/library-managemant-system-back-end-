package org.example.service.impl;

import org.example.dto.Book;
import org.example.entity.BookEntity;
import org.example.repository.BookRepository;
import org.example.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository repository;

    ModelMapper mapper;
    @Bean
    public void setup(){
        this.mapper=new ModelMapper();
    }
    @Override
    public void addBook(Book book) {
      BookEntity entity= mapper.map(book, BookEntity.class);
      repository.save(entity);
    }

    @Override
    public List<BookEntity> getBooks() {
        return (List<BookEntity>) repository.findAll();
    }

    @Override
    public boolean deleteBook(Long id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Book getBookId(Long id) {
        Optional<BookEntity>byId = repository.findById(id);
        return mapper.map(byId,Book.class);
    }

    @Override
    public void addList(List<Book> bookList) {
        List<BookEntity> entities = new ArrayList<>() ;


        bookList.forEach(Book -> {
            entities.add(mapper.map(Book, BookEntity.class));
        });
        repository.saveAll(entities);

    }  }