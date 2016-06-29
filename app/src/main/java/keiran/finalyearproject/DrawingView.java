package keiran.finalyearproject;

/**
 * Created by Keiran on 23/10/2015.
 */

import android.app.*;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

public class DrawingView extends View {

    private Path drawPath;     //drawing path
    private Paint drawPaint, canvasPaint;     //drawing and canvas paint
    private int paintColour = 0xFF660000;    //initial color
    private Canvas drawCanvas;    //canvas
    private Bitmap canvasBitmap;    //canvas bitmap


    //char[] V = {'X', 'F'};
    //String[] productions = {"F-[[X]+X]+F[+FX]-X", "FF"};
    protected Tree tree; //new Tree(V, productions, 'X', 6);
    //Evolution evolution;

    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing(){
/*
        try{
            this.tree = new Tree(V, productions, 'X', 6);
        }catch (MaxProductionsException | InvalidAlphabetException e){
            e.printStackTrace();
        }
*/
        //this.tree = new Tree(V, productions, 'X', 6);
        //this.evolution = new Evolution(this.tree, 1);
        drawPath = new Path();
        drawPaint = new Paint();

        drawPaint.setColor(paintColour);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(1);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
        canvasBitmap = Bitmap.createBitmap(2048, 2048, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }
/*
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }
*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPath.rewind();
        canvasBitmap.eraseColor(Color.WHITE);
        canvas.drawColor(Color.WHITE);
        if(tree != null) {
            int width = canvas.getWidth();
            int height = canvas.getHeight();
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
                } else {
                    drawPath.moveTo(turtle.getX(), turtle.getY());
                }
            }
        }

            canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
            canvas.drawPath(drawPath, drawPaint);
    }



    protected void drawTree(Tree tree) {

        this.tree = tree;
        //evolution.geneticAlgorithm();
        //this.tree = evolution.getTree();
        //this.tree = evolution.getTree();
        //drawCanvas.drawPath(drawPath, drawPaint);
        invalidate(); //runs onDraw

    }

    public Bitmap getBitmap(){
        return this.canvasBitmap;
    }

}
