package c.ponom.pocketlibrary.Database.RoomEntities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "authors",foreignKeys = @ForeignKey(entity = SubChapter.class,
        parentColumns = "subChapterName",
        childColumns = "subChapterName",
        onDelete = ForeignKey.CASCADE))
// в текущей схеме не прописана возможность удаления подразделов библиотеки, кроме очистки ее полностью.
// upserts room поддерживает только через удаление старой записи, что портит связанные тут  записи при обновлении подразделов
// и включенном каскаде. Альтернатива - исключить сторонний ключ вообще

public class Author extends BaseEntity{
    @ColumnInfo(index=true)
    public String subChapterName;
    @PrimaryKey
    @NonNull
    public String authorName;   // пример - Оноре де Бальзак
    public String url;    // http://lib.ru/INOOLD/BALZAK/
    public Integer sizeInMb;
    public boolean isLoaded;

}

