package c.ponom.pocketlibrary.data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import c.ponom.pocketlibrary.data.RoomEntities.Book;


@Dao
        public interface BookDAO {

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        void insert(Book... books);

        @Update(onConflict = OnConflictStrategy.IGNORE)
        void update(Book... books);

        @Delete
        void delete(Book book);


    @Query("SELECT *   FROM books WHERE authorName = :author ")
    List<Book> getBooksByAuthor(String author);

    @Query("SELECT *   FROM books WHERE authorName = :author ")
    LiveData<List<Book>> getBooksByAuthorLiveData(String author);

    @Query("SELECT *   FROM books WHERE authorName = :author and subChapterName=:subChapter ")
    LiveData<List<Book>> getBooksByAuthorAndChapterLiveData(String author, String subChapter);

    @Query("delete FROM books" )
    void clearBooks();

    @Query("SELECT *   FROM books WHERE sizeInKb>0 order by authorName,bookName")
    LiveData<List<Book>> getLoadedBooksList();


    @Query("SELECT sum(sizeInKb)   FROM books")
    int getTotalLoadedKbForTest();

    @Query("SELECT sum(sizeInKb)   FROM books")
    LiveData<Integer> getLoadedBooksTotalKB();


    @Query("SELECT * FROM books" )
    List<Book> getAllBooks();

    @Query("SELECT * FROM books" )
    LiveData<List<Book>> getAllBooksAsLiveData();


    @Query("SELECT *   FROM books WHERE bookName = :bookName LIMIT  1")
    Book getBookByName(String bookName);
    // тут мы исходим из допущения что книг с одним названием разных авторов нет.
    // Если есть - будет найдена первая


}

