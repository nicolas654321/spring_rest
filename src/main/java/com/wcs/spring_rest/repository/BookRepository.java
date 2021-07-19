package com.wcs.spring_rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wcs.spring_rest.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

 
    
}
