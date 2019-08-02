package c.ponom.pocketlibrary.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;


import c.ponom.pocketlibrary.Database.DAO.AuthorDAO;
import c.ponom.pocketlibrary.Database.DAO.BookDAO;
import c.ponom.pocketlibrary.Database.DAO.SubChapterDAO;
import c.ponom.pocketlibrary.Database.RoomEntities.Author;
import c.ponom.pocketlibrary.Database.RoomEntities.Book;
import c.ponom.pocketlibrary.Database.RoomEntities.SubChapter;


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
