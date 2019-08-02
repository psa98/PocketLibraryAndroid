package c.ponom.pocketlibrary.Database.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import c.ponom.pocketlibrary.Database.RoomEntities.Author;


@Dao
    public interface AuthorDAO {
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        void insert(Author... authors);

        @Update(onConflict = OnConflictStrategy.IGNORE)
        void update(Author... authors);

        @Delete
        void delete(Author author);

    @Query("SELECT distinct *  FROM authors")
    LiveData<List<Author>> getAllAuthorsAsLiveData();

    // важно - авторы в таблице  могут дублировться в нескольких разделах - Пушкин есть и в прозе и в поэззии.


    @Query("SELECT *   FROM authors WHERE subChapterName = :subChapter ")
    LiveData<List<Author>> getListOfAuthorsForSubchapters(String subChapter);

    @Query("Delete  FROM authors")
    void clearTableOfAuthors();



}

