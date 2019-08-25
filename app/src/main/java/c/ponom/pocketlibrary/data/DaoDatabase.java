package c.ponom.pocketlibrary.data;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;


import c.ponom.pocketlibrary.data.dao.AuthorDAO;
import c.ponom.pocketlibrary.data.dao.BookDAO;
import c.ponom.pocketlibrary.data.dao.SubChapterDAO;
import c.ponom.pocketlibrary.data.room_entities.Author;
import c.ponom.pocketlibrary.data.room_entities.Book;
import c.ponom.pocketlibrary.data.room_entities.SubChapter;


@Database(entities = {Book.class,
        Author.class, SubChapter.class}, version = 11)
public abstract class DaoDatabase extends RoomDatabase {

    private static DaoDatabase INSTANCE;



    public abstract BookDAO getBooksDAO();
    public abstract AuthorDAO getAuthorDAO();
    public abstract SubChapterDAO getSubChapterDAO();


    public static DaoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DaoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DaoDatabase.class, "mydb")
                            .fallbackToDestructiveMigration()
                            //.allowMainThreadQueries()
                            //.addCallback(sRoomDatabaseCallback)   раскомментить при необходимости
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        /**
         * Called when the database has been opened.
         *
         * @param db The database.
         */
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // может использоваться при необходимости что то сделать при создании /открытии базы
        }

        /**
         * Called when the database is created for the first time. This is called after all the
         * tables are created.
         *
         * @param db The database.
         */
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // может использоваться при необходимости что то сделать при создании /открытии базы
        }
    };




}
