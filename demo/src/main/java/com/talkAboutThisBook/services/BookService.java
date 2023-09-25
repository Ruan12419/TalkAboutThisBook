/*
 * This class is responsible for operations with books on the database
 */

package com.talkAboutThisBook.services;

import com.talkAboutThisBook.dto.BookDto;
import com.talkAboutThisBook.model.Book;
import com.talkAboutThisBook.repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@Service
public class BookService {

    // Declaration of the services
    final BookRepository bookRepository;
    final UserServiceImpl userServiceImpl;

    public BookService(BookRepository bookRepository, UserServiceImpl userServiceImpl) {
        super();
        this.bookRepository = bookRepository;
        this.userServiceImpl = userServiceImpl;
    }

    /*
     * Save a new book to database
     */
    public void save(BookDto bookDto) {
        Book book = new Book(bookDto.getId(), bookDto.getUserId(),
                bookDto.getTitle(), bookDto.getPublisher(),
                bookDto.getPublishedDate(), bookDto.getAuthors(),
                bookDto.getDescription(), bookDto.getThumbnail());
        bookRepository.save(book);
    }

    /*
     * Delete a book from database by its id and the user's id
     */
    public Boolean delete(Long userId, String id) {
        try {
            bookRepository.delete(bookRepository.findByUserIdAndId(userId, id));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /*
     * Get a list of all books
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /*
     * Get an array of all books saved by a user with the user's id
     */
    public Book[] getAllBooks(Long userId) {
        return bookRepository.findByUserId(userId);
    }

    public Book findById(String id) throws Exception {
        if (bookRepository.findById(id).isPresent()) {
            return bookRepository.findById(id).get();
        } else {
            throw new Exception();
        }
    }

    /*
     * Return a user's saved books as an object in a ModelAndView
     */
    public ModelAndView viewUserBooks(String viewName) throws UserPrincipalNotFoundException {
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("books", getAllBooks(userServiceImpl.getUserId()));
        return mav;
    }
}
