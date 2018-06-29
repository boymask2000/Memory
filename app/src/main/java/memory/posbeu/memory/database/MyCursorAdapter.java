package memory.posbeu.memory.database;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import memory.posbeu.memory.R;

public class MyCursorAdapter extends CursorAdapter {
    private LayoutInflater cursorInflater;

    // Default constructor
    public MyCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        TextView textData = (TextView) view.findViewById(R.id.data);
        TextView textMosse = (TextView) view.findViewById(R.id.mosse);
        TextView textTime = (TextView) view.findViewById(R.id.time);
        String data = cursor.getString(2);
        String mosse = cursor.getString(3);
        String time = cursor.getString(4);

        textData.setText(data);
        textMosse.setText(mosse);
        textTime.setText(time);

    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // R.layout.list_row is your xml layout for each row
        return cursorInflater.inflate(R.layout.rigalista, parent, false);
    }
}