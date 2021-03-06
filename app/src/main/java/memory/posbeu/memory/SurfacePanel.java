package memory.posbeu.memory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import memory.posbeu.memory.database.DBPartite;
import memory.posbeu.memory.database.Partita;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by gposabella on 30/05/2016.
 */
public class SurfacePanel extends SurfaceView implements SurfaceHolder.Callback {
    private final Context context;
    private final MainActivity mainActivity;
    private MyThread mythread;
    private int screenWidth;
    //   private int screenHeight;
    private int i = 0;
    private Paint mPaint = new Paint();
    private int tentativi;
    private Pair firstCell = null;
    private boolean firstMove = true;
    private int coppie;


    private Date startTime;


    public SurfacePanel(Context ctx, AttributeSet attrSet, MainActivity mainActivity) {
        super(ctx, attrSet);
        context = ctx;
        this.mainActivity = mainActivity;
        screenWidth = mainActivity.getScreenWidth();
        //   getDims();
        // setClipBounds(new Rect(0, 0, screenWidth, screenWidth));
        getHolder().setFixedSize(screenWidth, screenWidth);
//setBackgroundColor(Color.RED);
        SurfaceHolder holder = getHolder();

        holder.addCallback(this);
        startTime = new Date();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        doDraw(canvas);
    }

    //***************************************************************************************
    void doDraw(Canvas canvas) {
        Table tab = mainActivity.getTable();
        int size = tab.getSize();

        int step = screenWidth / size;
        for (int i = 0; i <= size; i++) {
            canvas.drawLine(i * step, 0, i * step, screenWidth, mPaint);
            canvas.drawLine(0, i * step, screenWidth, i * step, mPaint);
        }
        Table table = mainActivity.getTable();
        table.draw(this, canvas, mPaint, screenWidth);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mythread.setRunning(false);
        boolean retry = true;
        while (retry) {
            try {
                mythread.join();

                retry = false;
            } catch (Exception e) {

                Log.v("Exception Occured", e.getMessage());
            }
        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    boolean started = false;

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        Table tab = mainActivity.getTable();

        if (event.getAction() != MotionEvent.ACTION_DOWN) return false;

        float x = event.getX();
        float y = event.getY();

        Pair cella = getCella(x, y);
        if (cella == null) return false;
        TableCell cc = tab.getCell(cella.getX(), cella.getY());
        if (cc.isShow()) return false;

        //   Heap.selectedCell = mainActivity.getTable().getCellByCoord(cella.getX(), cella.getY());

        if (firstMove) {

            firstCell = cella;

            TableCell c1 = tab.getCell(firstCell.getX(), firstCell.getY());
            c1.setShow(true);
            c1.setCandidate(true);

        } else {
            tentativi++;
            mainActivity.setTentativi(tentativi);

            TableCell c1 = tab.getCell(firstCell.getX(), firstCell.getY());
            TableCell c2 = tab.getCell(cella.getX(), cella.getY());
            c2.setCandidate(true);
            c2.setShow(true);
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            c1.setCandidate(false);
            c2.setCandidate(false);
            if (c1.getCurrentVal() != c2.getCurrentVal()) {
                c1.setShow(false);
                c2.setShow(false);
            } else coppie++;
            mainActivity.setCoppie(coppie);

        }
        if (mainActivity.getTable().isRisolto()) {
            PopupMessage.info(mainActivity, "Completato !");
            mainActivity.setGameIsGoing(false);
            updateDb();
        }


        firstMove = !firstMove;

        return true;
    }

    private void updateDb() {
        new Thread() {
            public void run() {
                Partita p = new Partita();
                p.setData(getData());
                p.setNumMosse(tentativi);
                p.setGameSize(mainActivity.getGameSize());
                p.setTime(MainActivity.getTime(startTime));
                p.setSeconds(MainActivity.getTimeSeconds(startTime));

                DBPartite.getDb(mainActivity).daoAccess().insert(p);
            }
        }.start();
    }

    private String getData() {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return DATE_FORMAT.format(new Date());

    }


    private Pair getCella(float x, float y) {
        Table tab = mainActivity.getTable();
        int size = tab.getSize();
        int posx = (int) (x * size / screenWidth);
        int posy = (int) (y * size / screenWidth);

        if (posx < 0 || posx > size) return null;
        if (posy < 0 || posy > size) return null;


        return new Pair(posx, posy);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mythread = new MyThread(holder, context, this);

        mythread.setRunning(true);

        mythread.start();
    }

    public void update() {
        invalidate();
    }

    public void reset() {
        tentativi = 0;
        coppie = 0;
        startTime = new Date();
    }

    public Date getStartTime() {
        return startTime;
    }
}
