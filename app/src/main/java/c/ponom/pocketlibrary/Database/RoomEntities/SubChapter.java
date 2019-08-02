package c.ponom.pocketlibrary.Database.RoomEntities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "subchapters")

public class SubChapter extends BaseEntity implements Serializable {

    public Long id;
    @PrimaryKey
    @NonNull
    public String subChapterName;
    public String url;
    public Integer sizeInMb;
    public boolean isLoaded=false;
}