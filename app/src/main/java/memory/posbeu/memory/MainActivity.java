package memory.posbeu.memory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Date;
import java.util.List;

import memory.posbeu.memory.database.DBPartite;
import memory.posbeu.memory.database.Partita;


public class MainActivity extends Activity {

    private SurfacePanel surface;
    private Table table = null;

    private TextView textTentativi;
    private TextView textCoppie;
    private TextView textTime;

    private int screenWidth;
    private int screenHeight;


    private boolean useImages;

    private boolean gameIsGoing = true;
    private int gameSize = 8;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main1);
        textTime = (TextView) findViewById(R.id.time);
        setBanner();

        textTentativi = (TextView) findViewById(R.id.tentativi);
        textCoppie = (TextView) findViewById(R.id.coppie);

        FrameLayout lay = findViewById(R.id.board);

        getDims();
        surface = new SurfacePanel(getBaseContext(), null, this);
        lay.addView(surface);
        table = new Table(this, 8);

        handleButtons();

        loadIcons();
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                setTime();
                            }
                        });
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


    }

    private void loadIcons() {

        loadIcon(0, R.mipmap.img_1);
        loadIcon(1, R.mipmap.img_2);
        loadIcon(2, R.mipmap.img_3);
        loadIcon(3, R.mipmap.img_4);
        loadIcon(4, R.mipmap.img_5);
        loadIcon(5, R.mipmap.img_6);
        loadIcon(6, R.mipmap.img_7);
        loadIcon(7, R.mipmap.img_8);
        loadIcon(8, R.mipmap.img_9);
        loadIcon(9, R.mipmap.img_10);
        loadIcon(10, R.mipmap.img_11);
        loadIcon(11, R.mipmap.img_12);
        loadIcon(12, R.mipmap.img_13);
        loadIcon(13, R.mipmap.img_14);
        loadIcon(14, R.mipmap.img_15);
        loadIcon(15, R.mipmap.img_16);
        loadIcon(16, R.mipmap.img_17);
        loadIcon(17, R.mipmap.img_18);
        loadIcon(18, R.mipmap.img_19);
        loadIcon(19, R.mipmap.img_20);
        loadIcon(20, R.mipmap.img_21);
        loadIcon(21, R.mipmap.img_22);
        loadIcon(22, R.mipmap.img_23);
        loadIcon(23, R.mipmap.img_24);
        loadIcon(24, R.mipmap.img_25);
        loadIcon(25, R.mipmap.img_26);
        loadIcon(26, R.mipmap.img_27);
        loadIcon(27, R.mipmap.img_28);
        loadIcon(28, R.mipmap.img_29);
        loadIcon(29, R.mipmap.img_30);
        loadIcon(30, R.mipmap.img_31);
        loadIcon(31, R.mipmap.img_32);
    }

    private void loadIcon(int i, int id) {
        int s = table.getCellSize() - 2;
        Bitmap icon = BitmapFactory.decodeResource(getResources(), id);

        Bitmap bMapScaled = Bitmap.createScaledBitmap(icon, s, s, true);

        Heap.setIcon(i, bMapScaled);
    }

    public void setTentativi(int n) {
        textTentativi.setText("" + n);
    }

    private void setBanner() {
        MobileAds.initialize(this, "ca-app-pub-6114671792914206/1667379087");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    private void handleButtons() {

        Button solve = findViewById(R.id.solve);
        solve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                table.solve();
            }
        });


        Button cleanAll = findViewById(R.id.cleanAll);
        cleanAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reset();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    private static final int RESULT_SETTINGS = 16;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.usaimmagini:
                useImages = true;
                return true;
            case R.id.usanumeri:
                useImages = false;
                return true;
            case R.id.size4:
                gameSize = 4;
                reset();
                break;
            case R.id.size6:
                gameSize = 6;
                reset();
                break;
            case R.id.size8:
                gameSize = 8;
                reset();
                break;
            case R.id.preview:

                table.preview();
                break;
            case R.id.classifica_4: {
                Intent intent = new Intent(this, ClassificaActivity.class);
                Bundle b = new Bundle();
                b.putInt("key", 4); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }
            break;
            case R.id.classifica_6: {
                Intent intent = new Intent(this, ClassificaActivity.class);
                Bundle b = new Bundle();
                b.putInt("key", 6); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }
            break;
            case R.id.classifica_8: {
                Intent intent = new Intent(this, ClassificaActivity.class);
                Bundle b = new Bundle();
                b.putInt("key", 8); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }

            break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

    public void setGameIsGoing(boolean gameIsGoing) {
        this.gameIsGoing = gameIsGoing;
    }

    private void reset() {
        table = new Table(this, gameSize);
        loadIcons();
        table.clean();
        surface.reset();
        setCoppie(0);
        setTentativi(0);
        gameIsGoing = true;
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

    private void getDims() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();
        Point size = new Point();


        display.getSize(size);

        int v1 = getStatusBarHeight();
        int v2 = getNavigationBarHeight();

        screenWidth = size.x;
        screenHeight = size.y - v1 - v2;
        float f = (float) screenHeight / screenWidth;

        //   if (screenHeight >= screenWidth) Heap.setFact(f);
    }

    public boolean isUseImages() {
        return useImages;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getNavigationBarHeight() {
        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0)//&& !hasMenuKey)
        {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setCoppie(int n) {

        textCoppie.setText("" + n);
    }

    public void setTime() {
        if (!gameIsGoing) return;
        String s = getTime(surface.getStartTime());

        if (textTime != null)
            textTime.setText(s);
    }

    public int getGameSize() {
        return gameSize;
    }
    public static long getTimeSeconds(Date startTime) {
        long msec = startTime.getTime();
        long now = (new Date()).getTime();
        long secs = (now - msec) / 1000;
        return secs;
    }
    public static String getTime(Date startTime) {
        long msec = startTime.getTime();
        long now = (new Date()).getTime();
        long secs = (now - msec) / 1000;
        long hours = secs / 3600;
        long diff = secs % 3600;
        long mins = diff / 60;
        long ssecs = diff % 60;
        String s = "";
        if (hours > 0) s += "" + hours;
        if (s.length() > 0) s += ":";
        if (mins > 0) s += "" + mins;
        if (s.length() > 0) s += ":";
        s += ssecs;
        return s;
    }

}
