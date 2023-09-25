/*
 * This class was found at https://5balloons.info/consuming-rest-api-in-java-spring-through-resttemplate/
 * It is responsible for calling the Google Books Api and saving the response to the GBConverter class
 * access the above link for more details
 */

package com.talkAboutThisBook.controller;

import com.talkAboutThisBook.dto.BookDto;
import com.talkAboutThisBook.model.GBConverter;
import com.talkAboutThisBook.model.GBItemsWrapper;
import com.talkAboutThisBook.model.GBVolumeInfoWrapper;
import com.talkAboutThisBook.services.BookService;
import com.talkAboutThisBook.services.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
public class BookController {

    // Declaration of the services
    final BookService bookService;
    final UserServiceImpl userServiceImpl;

    public BookController(BookService bookService, UserServiceImpl userServiceImpl) {
        this.bookService = bookService;
        this.userServiceImpl = userServiceImpl;
    }

    /*
     * ModelAndView will be returned if no book is found
     */
    final ModelAndView bad = new ModelAndView("bad");

    /*
     * List of books to be rendered by template
     */
    private final List<GBVolumeInfoWrapper> books = new ArrayList<>();

    /*
     * Initializes GBConverter class
     */
    private final GBConverter gbConverter = new GBConverter();

    /*
     * Responsible for send request to Google Books Api
     * When a user searches a book, it will be sent to the address below with two
     * parameters 'q' has the title of the book and is required for the search
     * 'inauthor' is the author of the book and is not required
     */
    @RequestMapping(path = "/pega/", params = "q", method = RequestMethod.GET)
    public ModelAndView getGoogleBook(@RequestParam(name = "q") String q,
            @RequestParam(name = "inauthor", required = false) String inauthor) {
        // Initializes the RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Calls the Google Book Api with the data from the parameters as a GBConverter
        // class and stores it on the ResponseEntity
        ResponseEntity<GBConverter> entity = restTemplate.getForEntity(
                "https://www.googleapis.com/books/v1/volumes?q=" + q + "+inauthor:" + inauthor + "&maxResults=40",
                GBConverter.class);

        // Set the items returned to entity to the GBConverter list
        gbConverter.setItems(Objects.requireNonNull(entity.getBody()).getItems());

        // Set the total items returned
        gbConverter.setTotalItems(entity.getBody().getTotalItems());

        // If GBConverter is not null add the books to the books List and return the
        // list as an object to the 'index' template
        if (gbConverter.getTotalItems() != 0) {
            addBooks(gbConverter.getItems());
            ModelAndView mav = new ModelAndView("index");
            mav.addObject("books", books);
            return mav;
        } else {

            // If no books are returned, return the 'bad' template
            return notFound();
        }
    }

    /*
     * Return message if no books are found
     */
    public ModelAndView notFound() {
        bad.addObject("message", "0 Books Found!");
        return bad;
    }

    /*
     * ModelAttribute to save a book
     */
    @ModelAttribute("save_book")
    public GBVolumeInfoWrapper bookDto() {
        return new GBVolumeInfoWrapper();
    }

    /*
     * Responsible for saving a book
     * When a user clicks to save a book, it will be sent to the address below with
     * one
     * parameter 'i' has the index of the book on the books list
     * it will be used to get the book from the list and save it to database
     */
    @Transactional
    @PostMapping("/save")
    public ModelAndView saveBook(@RequestParam(name = "i") String i) throws UserPrincipalNotFoundException {

        // Get the index number of the book
        int index1 = Integer.parseInt(i);

        // Save book to a new variable
        GBVolumeInfoWrapper book = books.get(index1);

        // Initialize the 'index' ModelAndView
        ModelAndView mav = new ModelAndView("index");

        Long userId = userServiceImpl.getUserId();

        // Create a BookDto with the book information and the user id
        BookDto bookDto = new BookDto(book.getId(), userId, book.getTitle(),
                book.getPublisher(), book.getPublishedDate(), book.getAuthors(),
                book.getDescription(), book.getImageLinks().get("thumbnail"));

        // Saves the book to database
        bookService.save(bookDto);

        // Add the user saved books object and render the template
        mav.addObject("Sbooks", bookService.getAllBooks(userId));
        return mav;
        
    }

    /*
     * Delete a book by its id and user's id
     */
    @PostMapping("delete")
    public ModelAndView deleteBook(@RequestParam(name = "id") String id) throws UserPrincipalNotFoundException {

        ModelAndView mav = new ModelAndView("mybooks");
        
        if (bookService.delete(userServiceImpl.getUserId(), id)) {
            return mav.addObject("message", "Book Deleted!");
        } else {
            return mav.addObject("message", "Error!!!");
        }

    }

    /*
     * Add the books from the Api to the books list
     */
    private void addBooks(GBItemsWrapper[] gbIW) {

        // Clear the list
        books.clear();

        // Initializes the index variable
        int index = 0;

        // Loop through the GBitems
        for (GBItemsWrapper volInfo : gbIW) {

            // If the book has no thumbnail a default image will be used
            if (volInfo.getVolumeInfo().getImageLinks().isEmpty()) {
                HashMap<String, String> temp = new HashMap<>();
                temp.put("thumbnail", "/images/no-thumbnail.jpg");
                volInfo.getVolumeInfo().setImageLinks(temp);
            }

            // Set the index of the current book
            volInfo.setIndex(index);

            // Add to the books list
            books.add(volInfo.getVolumeInfo());

            // Increase index by one
            index++;
        }
    }

}
