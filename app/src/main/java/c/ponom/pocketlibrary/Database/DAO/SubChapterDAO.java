package c.ponom.pocketlibrary.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import c.ponom.pocketlibrary.Database.RoomEntities.SubChapter;


@Dao
    public interface SubChapterDAO {
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        void insert(SubChapter... subChapters);


        @Update(onConflict = OnConflictStrategy.IGNORE)
        void update(SubChapter... subChapters);


        @Delete
        void delete(SubChapter subChapter);



    @Query("SELECT * FROM subchapters")
    List<SubChapter> getAllSubChapters();

    @Query("SELECT * FROM subchapters")
    LiveData<List<SubChapter>> getAllSubChaptersLiveData();



    @Query("DELETE  FROM subchapters")
    void clearTableSubChapters();

}

