package memory.posbeu.memory;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Table {



    private  int size=0;
    private TableCell[][] table = new TableCell[10][10];
    private TableCell selectedCell;


    public Table(int size) {
        this.size=size;
        table = new TableCell[size][size];
        for (int i = 0; i <size; i++)
            for (int j = 0; j < size; j++)
                table[i][j] = new TableCell(i, j);

    }


    public TableCell getCell(int i, int j) {

        return table[i][j];
    }


    public TableCell getCellByCoord(int x, int y) {
        if (x < 0 || y < 0)
            return null;
        if (x > 450 || y > 450)
            return null;
        int xx = x / 50;
        int yy = y / 50;
        return table[xx][yy];
    }




    public void draw(Canvas canvas, Paint mPaint, int screenWidth) {
        //  setTextSizeForWidth(mPaint,300, "1");
        int cSize = screenWidth / (size+1);
        mPaint.setTextSize(cSize);
        int fattX = screenWidth / size;
        int fattY = screenWidth / size;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                TableCell cell = table[i][j];

                int x = i * fattX + 2;
                int y = j * fattY + 2;
                fill(canvas, screenWidth, x, y, Color.WHITE);



                if (selectedCell != null && selectedCell.equals(cell)) {

                    fill(canvas, screenWidth, x, y, Color.DKGRAY);
                }


                if (cell.isShow()) {

                    canvas.drawText("" + cell.getCurrentVal(), i * fattX + 2, (j + 1) * fattY - 4, mPaint);
                }
                else
                    fill(canvas, screenWidth, x, y, Color.WHITE);

            }
    }

    private void fill(Canvas canvas, int screenWidth, int x, int y, int color) {
        int ss = screenWidth / size - 2;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        canvas.drawRect(x, y, x + ss, y + ss, paint);
    }


    public void init() {
        Randomizer rand = new Randomizer(size*size);
        for (int i = 0; i <size; i++)
            for (int j = 0; j <size; j++) {
                table[i][j].setCurrentVal(rand.getNextRandom());
            }

    }
    public int getSize() {
        return size;
    }
}
