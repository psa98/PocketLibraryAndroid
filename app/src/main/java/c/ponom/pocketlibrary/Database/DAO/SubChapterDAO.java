package c.ponom.pocketlibrary.Database.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

