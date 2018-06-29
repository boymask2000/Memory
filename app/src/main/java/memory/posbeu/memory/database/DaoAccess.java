package memory.posbeu.memory.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void insert(Partita partita);

    @Query("SELECT * FROM Partita WHERE _id = :id")
    Partita fetch(String id);

    @Query("SELECT * FROM Partita where gameSize= :size")
    Cursor getAll(int size);

    @Update
    void updateMovie(Partita partita);

    @Delete
    void deleteMovie(Partita partita);
}