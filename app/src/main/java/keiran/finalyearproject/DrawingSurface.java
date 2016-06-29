package keiran.finalyearproject;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Created by Keiran on 25/01/2016.
 * NOTE: Use this class as an attempt to improve app performance when drawing trees.
 */
public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Path drawPath;     //drawing path
    private Paint drawPaint, canvasPaint;     //drawing and canvas paint
    private int paintColour = 0xFF660000;    //initial color
    private Canvas drawCanvas;    //canvas
    private Bitmap canvasBitmap;    //canvas bitmap
    private Tree tree;
    private SurfaceHolder holder;
    private boolean locker = true;

    private Thread thread;

    public DrawingSurface(Context context, AttributeSet attrs){
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);

        drawPath = new Path();
        drawPaint = new Paint();

        drawPaint.setColor(paintColour);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(1);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawCanvas = holder.lockCanvas(null);
        thread = new Thread(this);
        thread.start();

        //drawTree(tree);
        //holder.unlockCanvasAndPost(drawCanvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        locker = false;
        while(true){
            try {
                //WAIT UNTIL THREAD DIE, THEN EXIT WHILE LOOP AND RELEASE a thread
                thread.join();
            } catch (InterruptedException e) {e.printStackTrace();
            }
            break;
        }
        thread = null;
    }

    //@Override
    public void drawToCanvas(Canvas canvas) {
        //super.onDraw(canvas);
        drawPath.rewind();
        canvasBitmap.eraseColor(Color.WHITE);
        canvas.drawColor(Color.WHITE);
        if(tree != null) {
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            drawPath.moveTo(width/2, height);
            Turtle turtle = new Turtle(width/2, height, 5, (float)Math.toRadians(25));
            for (int i = 0; i < this.tree.getTree().length(); i++) {
                if (turtle.rules(this.tree.getTree().toCharArray()[i])) {
                    drawPath.lineTo(turtle.getX(), turtle.getY());
                } else {
                    drawPath.moveTo(turtle.getX(), turtle.getY());
                }
            }

            canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
            canvas.drawPath(drawPath, drawPaint);
        }
    }

    protected void drawTree(Tree tree){
        this.tree = tree;
        //thread.start();
        this.tree = tree;
        postInvalidate();
    }

    @Override
    public void run() {
        while(locker){
            //checks if the lockCanvas() method will be success,and if not, will check this statement again
            if(!holder.getSurface().isValid()){
                continue;
            }
            Canvas canvas = holder.lockCanvas();

            drawToCanvas(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

}


