package memory.posbeu.memory.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Partita.class}, version = 5, exportSchema = false)


public abstract class DBPartite extends RoomDatabase {
    private static final String DATABASE_NAME = "partite_db";

    public abstract DaoAccess daoAccess();

    private static DBPartite db = null;

    public static DBPartite getDb(Context ctx) {
        if (db == null) db = Room.databaseBuilder(ctx,
                DBPartite.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
        return db;

    }
}