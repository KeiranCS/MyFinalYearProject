package keiran.finalyearproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Keiran on 15/02/2016.
 */
public class DrawTree extends AsyncTask<Tree, Void, Bitmap[]>{

    private Path drawPath;     //drawing path
    private Paint drawPaint, canvasPaint;     //drawing and canvas paint
    private int paintColour = 0xFF660000;    //initial color
    private Canvas drawCanvas;    //canvas
    private Bitmap canvasBitmap;    //canvas bitmap
    private float max_x;
    private float max_y;

    protected Tree tree;

    public DrawTree(){
        drawPath = new Path();
        drawPaint = new Paint();

        drawPaint.setColor(paintColour);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(1);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
        //canvasBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        //drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected Bitmap[] doInBackground(Tree... tree) {
        Bitmap[] bitmaps = new Bitmap[6];
        drawTree(tree[0]);
        for(int i = 0; i < bitmaps.length; i++){
            drawTree(tree[i]);
            bitmaps[i] = getBitmap();
        }


        return bitmaps;
    }

    @Override
    protected void onPostExecute(Bitmap[] bitmaps) {
        super.onPostExecute(bitmaps);
    }

    protected void drawTree(Tree tree){
        this.tree = tree;
        max_x = 0;
        max_y = 0;
        drawPath.rewind();
        /*
        canvasBitmap.eraseColor(Color.WHITE);
        drawCanvas.drawColor(Color.WHITE);
        if(tree != null) {
            int width = drawCanvas.getWidth();
            int height = drawCanvas.getHeight();
            drawPath.moveTo(width / 2, height);
            Turtle turtle = new Turtle(width / 2, height, 5, (float) Math.toRadians(25));
            for (int i = 0; i < this.tree.getTree().length(); i++) {
                if(tree.getTree().toCharArray()[i] == 'F'){
                    turtle.setD(tree.getParams().get(i));
                }else if(tree.getTree().toCharArray()[i] == '+' || tree.getTree().toCharArray()[i] == '-'){
                    turtle.setDelta((float)Math.toRadians(tree.getParams().get(i)));
                }
                if (turtle.rules(this.tree.getTree().toCharArray()[i])) {
                    drawPath.lineTo(turtle.getX(), turtle.getY());
                    if(turtle.getX() > max_x)
                        max_x = turtle.getX();
                    if(turtle.getY() > max_y)
                        max_y = turtle.getY();
                } else {
                    drawPath.moveTo(turtle.getX(), turtle.getY());
                }
            }
        }
        /*/
        if(tree != null) {

            drawPath.moveTo(0,0);
            Turtle turtle = new Turtle(0,0, 5, (float) Math.toRadians(25));
            for (int i = 0; i < this.tree.getTree().length(); i++) {
                if(tree.getTree().toCharArray()[i] == 'F'){
                    turtle.setD(tree.getParams().get(i));
                }else if(tree.getTree().toCharArray()[i] == '+' || tree.getTree().toCharArray()[i] == '-'){
                    turtle.setDelta((float)Math.toRadians(tree.getParams().get(i)));
                }
                if (turtle.rules(this.tree.getTree().toCharArray()[i])) {
                    drawPath.lineTo(turtle.getX(), turtle.getY());
                    if(turtle.getX() > max_x)
                        max_x = turtle.getX();
                    if(turtle.getY() < max_y)
                        max_y = turtle.getY();
                } else {
                    drawPath.moveTo(turtle.getX(), turtle.getY());
                    if(turtle.getX() > max_x)
                        max_x = turtle.getX();
                    if(turtle.getY() < max_y)
                        max_y = turtle.getY();
                }
            }
            Matrix scaleMatrix = new Matrix();
            RectF rectF = new RectF();

            drawPath.computeBounds(rectF, true);
            //scaleMatrix.setScale(1, 1, rectF.centerX(), rectF.centerY());
            scaleMatrix.setTranslate(-rectF.left, -rectF.top);
            drawPath.transform(scaleMatrix);
            //drawPath.computeBounds(rectF, true);
            //max_x = max_x + (max_x/2);
            canvasBitmap = Bitmap.createBitmap((int)rectF.width(), (int)rectF.height(), Bitmap.Config.ARGB_8888);
            drawCanvas = new Canvas(canvasBitmap);
            //drawPath.offset(max_x - max_x/2, 0);
            drawCanvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
            drawCanvas.drawPath(drawPath, drawPaint);
        }


        //*/
        //drawCanvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        //drawCanvas.drawPath(drawPath, drawPaint);
    }

    public Bitmap getBitmap(){
        return this.canvasBitmap;
    }
}
