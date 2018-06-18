package memory.posbeu.memory;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends Activity {

    private SurfacePanel surface;
    private Table table=new Table(8);
  //  private Sudoku sudoku;
  //  private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main1);

   //     setBanner();


        LinearLayout lay = findViewById(R.id.board);

        surface = new SurfacePanel(getBaseContext(), null, this);
        lay.addView(surface);

        table.init();
        handleButtons();

   //     sudoku = new Sudoku(this);

    }
    private void setBanner(){
   /*     MobileAds.initialize(this, "");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
*/
    }


    private void handleButtons() {

        Button solve = findViewById(R.id.solve);
        solve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              /*  if(!table.check()){
                    PopupMessage.info(MainActivity.this, "Combinazione illegale");
                }

                sudoku.go();*/
            }
        });



        Button cleanAll = findViewById(R.id.cleanAll);
        cleanAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             //   sudoku.cleanAll();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //       inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    private static final int RESULT_SETTINGS = 16;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
      /*  switch (item.getItemId()) {
            case R.id.viewFinal:


                return true;
            case R.id.solve:
                //       surface.goSolve();

                return true;
            case R.id.opzioni:

                Intent i = new Intent(this, SettingsActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/
      return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SETTINGS:
                showUserSettings();
                break;

        }

    }

    private void showUserSettings() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        String livello = sharedPrefs.getString("Livello", "NULL");


      //  Heap.getActivity().finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, RESULT_SETTINGS);


    }

    public SurfacePanel getSurface() {
        return surface;
    }


    public Table getTable() {
        return table;
    }

    public void update() {
        surface.update();
    }
}
