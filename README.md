# <center>Talk About This Book</center>
Ruan Lima

CS50 Final Project

## [**My Video Demo**](https://youtu.be/ig0PvVn8hrs "See here my video explaining this project")

## Description

You can add Books, and start discussions about the book.

You can also participate on discussions other people started, also about books other people added.

---

## <center>Content</center>

*  Add your favorite Books to the site.
*  Start a *discussion* about a book
*  Comment on *discussions* other people started
*  Follow people and keep seeing their posts

**This application was built with:**
    
    Spring Boot(Spring JPA, Spring Security)
    Thymeleaf
    HTML, CSS and Javascript(Jquery)

**See details about each file below**
<details>
<summary>Models</summary>
<br>

Each of the following classes is a Spring JPA entity.
Except GBConverter, GBItemsWrapper and GBVolumeWrapper
    <details>
    <summary>User</summary>
    <br>
    The User model allows you to interact with the "users" table in the database.
    <br>
    It has user information, such as first and last name, username, password, and user roles.
    <br>
    It has the same columns of the table "users" and has a Many-to-Many column that refers to the "role" table.
    </details>
    <details>
    <summary>Role</summary>
    <br>
    The Role model allows you to interact with the "role" table in the database.
    <br>
    It has the roles of the user.
    <br>
    It has the same columns of the table "role".
    </details>
    <details>
    <summary>Follow</summary>
    <br>
    The Follow model allows you to interact with the "follow" table in the database.
    <br>
    It has the relationship between who follows whom.
    <br>
    The "user_id" column has a foreign key of the "id" of the user who is following another and the "following_id" has a foreign key of the "id" of the user being followed.
    </details>
    <details>
    <summary>GBConverter, GBItemsWrapper and GBVolumeInfoWrapper</summary>
    <br>
    These classes are responsible for taking the JSON returned from Google Books API and turning it into POJO classes.
    <br>
    Here is the link where you can get information about them: [**Consuming REST API in Java Spring through RestTemplate**](https://5balloons.info/consuming-rest-api-in-java-spring-through-resttemplate/ "See about the GBWrapper classes")
    </details>
    <details>
    <summary>Book</summary>
    <br>
    The Book model allows you to interact with the "books" table in the database.
    <br>
    It has the book information that a user saves, such as the id of the book, title, authors, description, publisher, published date, a link for the thumbnail and the user's id.
    <br>
    All book information saved here comes from Google Books API.
    </details>
    <details>
    <summary>Discussion</summary>
    <br>
    The Discussion model allows you to interact with the "discussions" table in the database.
    <br>
    It has the discussions started by each user separated by id.
    <br>
    It has the text of the discussion, the date and time it is being posted, and the user and book id.
    </details>
    <details>
    <summary>Comment</summary>
    <br>
    The Comment model allows you to interact with the "comments" table in the database.
    <br>
    It has the comment id, the text, the date and time it is being posted and the discussion and user id.
    </details>

    
</details>

<details>
<summary>DTOs</summary>
<br>

A DTO is responsible for transporting data between processes. The DTO classes here are responsible for sending data to Model classes and getting data from them.
    <details>
    <summary>UserRegistration</summary>
    <br>
    This DTO takes the registration information sent from the "registration" view.
    <br>
    </details>
    <details>
    <summary>BookDto</summary>
    <br>
    This DTO gets the book information from the book list returned by the Google Books API.
    </details>
    <details>
    <summary>DiscussionDto</summary>
    <br>
    This DTO is responsible for obtaining the information to create a new discussion.
    </details>
    <details>
    <summary>CommentDto</summary>
    <br>
    This DTO is responsible for obtaining the information to create a new comment.
    </details>
    
</details>

<details>
<summary>Services</summary>
<br>
A Service class contains the Business logic.
    <details>
    <summary>UserService</summary>
    <br>
    This is an interface that extends UserDetaislsService from Spring Security and is responsible for saving a new user to database.
    </details>
    <details>
    <summary>UserServiceImpl</summary>
    <br>
    This Service implements UserService and is responsible for getting the user information to save it through the UserService interface.
    <br>
    It is also responsible for accessing user information from the database.
    </details>
    <details>
    <summary>FollowService</summary>
    <br>
    This service contains the logic to save, read and delete follows from the database.
    <br>
    It is responsible for getting the users being followed by the user and getting the users following the user.
    </details>
    <details>
    <summary>BookService</summary>
    <br>
    This service contains the logic to save, read and delete books from the database.
    <br>
    It also return a ModelAndView with the user saved books.
    </details>
    <details>
    <summary>DiscussionService</summary>
    <br>
    This service contains the logic to save, read and delete discussions from the database.
    </details>
    <details>
    <summary>CommentService</summary>
    <br>
    This service contains the logic to save, read and delete comments from the database.
    </details>
    
</details>

<details>
<summary>Repositories</summary>
<br>
The repositories extend JpaRepository, which contains APIs for basic CRUD operations.
    <details>
    <summary>UserRepository</summary>
    <br>
    Get a user from the database by searching for their username.
    </details>
    <details>
    <summary>FollowRepositoy</summary>
    <br>
    Get a list of everyone the user is following.
    <br>
    Get a list of everyone is following the user.
    <br>
    Check if a user is following another.
    <br>
     Get the relationship between these two users.
    </details>
    <details>
    <summary>BookRepository</summary>
    <br>
    Get a book by its id.
    <br>
    Get a book by its id and user id.
    </details>
    <details>
    <summary>DiscussionRepository</summary>
    <br>
    Get a discussion by its id.
    <br>
    Get a discussion by the user id.
    <br>
    Get all discussions sorted by date and time in descending order.
    </details>
    <details>
    <summary>CommentRepository</summary>
    <br>
    Delete all comments from a discussion by discussion id.
    <br>
    Get all comments of discussion by discussion id sorted by date and time in ascending order.
    </details>
    
</details>

<details>
<summary>Controllers</summary>
<br>
The Controller classes are <a href="https://stackabuse.com/controller-and-restcontroller-annotations-in-spring-boot/" title="See more about Spring Boot Controllers"><b>responsible for processing incoming REST API requests, preparing a model, and returning the view to be rendered as a response.</b></a>
    <details>
    <summary>UserRegistrationController</summary>
    <br>
    This controller sends a ModelAttribute from a user to the registration view and after the user registers it saves the user and redirects it back to the login view.
    </details>
    <details>
    <summary>AppController</summary>
    <br>
    Return the login view, the index view, and "mybooks" view.
    </details>
    <details>
    <summary>UserControllers</summary>
    <br>
    Responsible to handle the following and the unfollowing buttons.
    <br>
    It also returns a following list and a follower list of a user in the "following" view.    
    </details>
    <details>
    <summary>BookController</summary>
    <br>
    It's here in this controller where the Google Books API is called.
    <br>
    It takes the JSON returned from Google with the GBConverter class and puts the data in a list of GBVolumeConverterWrapper classes.
    <br>
    These classes are what is sent and shown in the view after a search, and it is also where the book that will be saved is.
    <br>
    It also receives the book information when the user deletes it.
    </details>
    <details>
    <summary>DiscussionController</summary>
    <br>
    This controller is responsible for receiving the requests for creating and deleting a discussion.
    <br>
    It returns all discussions for a user based on user id and all discussions for a book based on book id.
    </details>
    <details>
    <summary>CommentController</summary>
    <br>
    This controller is responsible for receiving the requests for creating and deleting a comment.
    <br>
    It deletes a comment based on comment id.
    </details>
    
</details>
<details>
<summary>Security</summary>
<br>
The SecurityConfiguration class is from <a href="https://www.javaguides.net/2020/06/spring-security-tutorial-with-spring-boot-spring-data-jpa-thymeleaf-and-mysql-database.html" title="Spring Security login and logout"><b>Java Guides</b></a>, and it has the configuration of the login and logout.
</details>
<details>
<summary>Resources</summary>
<br>
The "resources" folder has the frontend template views, the styles and the script.
    <details>
    <summary>Templates</summary>
    <br>
    This folder has all the application's HTML/Thymeleaf code.
        <details>
        <summary>Fragments</summary>
        <br>
        This folder has two files with Thymeleaf fragments to be used in multiple views.
        </details>
    </details>
    <details>
    <summary>Static</summary>
    <br>
    This folder has static files, such as the style files and an image.
        <details>
        <summary>Styles</summary>
        <br>
        This folder has two CSS files for styling the pages.
        </details>
        <details>
        <summary>Javascript</summary>
        <br>
        This folder has a JS file responsible for AJAX requests with Jquery.
        </details>
        <details>
        <summary>Images</summary>
        <br>
        This folder has an image to display when a book has no thumbnail.
        </details>
    </details>
    
</details>

## <center>This Was  CS50!</center>

---
