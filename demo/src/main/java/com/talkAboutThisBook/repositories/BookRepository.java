/*
 * This interface extends the JpaRepository and is implemented by BookService
 * to do operations on the database
 */

package com.talkAboutThisBook.repositories;

import com.talkAboutThisBook.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    /*
     * Get books save by a user by the user's id
     */
    Book[] findByUserId(Long userId);

    /*
     * Get a specific book saved by a user by its id and by the user's id
     */
    Book findByUserIdAndId(Long userId, String id);
}
