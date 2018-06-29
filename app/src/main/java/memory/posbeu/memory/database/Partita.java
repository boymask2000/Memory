package memory.posbeu.memory.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity
public class Partita {


    @NonNull
    @PrimaryKey(autoGenerate=true)
    private long _id;

    public int gameSize;
    private String data;
    private int numMosse=0;
    private String time;

    public Partita(){

        Date d = new Date();

    }
    public long get_id() {
        return _id;
    }

    public void set_id(@NonNull long _id) {
        this._id = _id;
    }



    public int getGameSize() {
        return gameSize;
    }

    public void setGameSize(int gameSize) {
        this.gameSize = gameSize;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getNumMosse() {
        return numMosse;
    }

    public void setNumMosse(int numMosse) {
        this.numMosse = numMosse;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
