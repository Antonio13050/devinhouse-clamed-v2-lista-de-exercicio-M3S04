package com.example.miniprojetoavaliacoess4.services;

import com.example.miniprojetoavaliacoess4.exceptions.BookNotFoundException;
import com.example.miniprojetoavaliacoess4.exceptions.PersonNotFoundException;
import com.example.miniprojetoavaliacoess4.model.Book;
import com.example.miniprojetoavaliacoess4.model.Person;
import com.example.miniprojetoavaliacoess4.model.Review;
import com.example.miniprojetoavaliacoess4.model.transport.BookDTO;
import com.example.miniprojetoavaliacoess4.model.transport.BookListDTO;
import com.example.miniprojetoavaliacoess4.model.transport.operations.CreateBookDTO;
import com.example.miniprojetoavaliacoess4.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final PersonService personService;

    public BookService(BookRepository bookRepository, PersonService personService){
        this.bookRepository = bookRepository;
        this.personService = personService;
    }

    public BookDTO findByGuid(String guid) throws BookNotFoundException {
        return this.bookRepository.findByGuid(guid).map(BookDTO::new)
                .orElseThrow(() -> new BookNotFoundException("Livro não encontrado"));
    }

    public Book findByGuidBook(String guid) throws BookNotFoundException {
        return this.bookRepository.findByGuid(guid)
                .orElseThrow(() -> new BookNotFoundException("Livro não encontrado"));
    }

    @Transactional
    public BookDTO create(CreateBookDTO body, UserDetails userInSession) throws PersonNotFoundException {
        Person person = this.personService.findByEmail(userInSession.getUsername());
        Book book = new Book(body, person);
        this.bookRepository.save(book);
        return new BookDTO(book);
    }

    public List<BookListDTO> listAll (){
        List<Book> books = this.bookRepository.findAll();
        return books.stream().map(BookListDTO::new).toList();
    }



}
