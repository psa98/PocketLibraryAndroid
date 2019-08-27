package c.ponom.pocketlibrary.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import c.ponom.pocketlibrary.data.room_entities.Author;
import c.ponom.pocketlibrary.data.room_entities.BaseEntity;


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

