package memory.posbeu.memory.database;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

import memory.posbeu.memory.ClassificaActivity;
import memory.posbeu.memory.R;

public class DataGetter extends AsyncTask<Void, Void, Void> {
    private final int size;
    private Cursor cursor;
    private Context ctx;
    private ListView view;

    public DataGetter(ListView view, Context ctx, int size) {
        this.ctx = ctx;
        this.view = view;
        this.size=size;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        cursor = DBPartite.getDb(ctx).daoAccess().getAll(size);
        return null;
    }

    @Override // This is called when doInBackground() is finished
    protected void onPostExecute(Void result) {

        MyCursorAdapter adapter = new MyCursorAdapter(ctx, cursor, 2);
        view.setAdapter(adapter);
    }
}
