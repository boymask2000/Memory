package memory.posbeu.memory;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

import memory.posbeu.memory.database.DBPartite;
import memory.posbeu.memory.database.DataGetter;
import memory.posbeu.memory.database.MyCursorAdapter;

public class ClassificaActivity extends Activity {

    // This is the Adapter being used to display the list's data


    // These are the Contacts rows that we will retrieve
    static final String[] PROJECTION = new String[]{ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME};


    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifica);
        listView = (ListView) findViewById(R.id.list);
        handleButtons();

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.rigalista, listView, false);
        listView.addHeaderView(header);

        listView.setBackgroundColor(Color.WHITE);
        header.setBackgroundColor(Color.GREEN);
        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if (b != null)
            value = b.getInt("key");


        new DataGetter(listView, this, value).execute();


    }

    private void handleButtons() {

        Button solve = findViewById(R.id.ok);
        solve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


    }

}
