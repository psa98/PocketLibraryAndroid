package c.ponom.pocketlibrary.data.room_entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "books")

/*,foreignKeys = @ForeignKey(entity = Author.class,
        parentColumns = "authorName",
        childColumns = "authorName",
        onDelete = ForeignKey.CASCADE) )
*/

public class Book extends BaseEntity{

    @ColumnInfo (index = true)
    public String authorName;
    @ColumnInfo (index = true)
    public String subChapterName;
    @PrimaryKey
    @NonNull
    public String bookName;   // пример - Утраченные иллюзии
    public String url;    // http://lib.ru/INOOLD/BALZAK/illusinos.txt
    public Integer sizeInKb=0;
    public String uriToFile=""; //если непусто - то файл должен лежать по ссылке контент-провайдера
}

