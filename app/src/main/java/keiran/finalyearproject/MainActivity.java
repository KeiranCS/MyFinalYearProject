package keiran.finalyearproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {


    private DrawingView drawView1;
    private DrawingView drawView2;
    //private DrawingSurface drawView1;
    //private DrawingSurface drawView2;
    private ProgressBar bar;
    private Button drawButton;
    protected Evolution evolution;
    protected Stack<Tree[]> undoStack = new Stack();
    protected Stack<Tree[]> redoStack = new Stack();
    private int max_stack_size = 3;
    private int stack_size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = (ProgressBar) findViewById(R.id.progressBar);
        //drawView1 = (DrawingView)findViewById(R.id.drawing);
        //drawView2 = (DrawingView)findViewById(R.id.drawing2);
        drawView1 = (DrawingView)findViewById(R.id.drawing);
        drawView2 = (DrawingView) findViewById(R.id.drawing2);
        Intent intent = getIntent();
        //Bundle data = getIntent().getExtras();
        //Tree tree1 = (Tree) data.getParcelableArrayList("tree").get(1);
        Tree tree1 = intent.getExtras().getParcelable("tree");
        Tree tree2 = new Tree(tree1);
        evolution = new Evolution();
        evolution.setPop(new Tree[]{tree1, tree2});

        drawView1.drawTree(evolution.getPop()[0]);
        drawView2.drawTree(evolution.getPop()[1]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.save:
                return true;
            case R.id.undo:
                undo();
                return true;
            case R.id.redo:
                redo();
                return true;
            case R.id.help:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        /*
        //noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
        */
    }
    //NEEDS TO RUN IN ITS OWN THREAD
    public void evolveTree1(View view){
        //undo_push(evolution.getPop());
        bar.setVisibility(ProgressBar.VISIBLE);
        if (stack_size == max_stack_size) {
            undoStack.remove(0);
            stack_size--;
        }
        undoStack.push(evolution.getPop());
        stack_size++;

        evolution.geneticAlgorithm(0);

        drawView1.drawTree(evolution.getPop()[0]);
        drawView2.drawTree(evolution.getPop()[1]);

        bar.setVisibility(ProgressBar.INVISIBLE);
    }


    //NEEDS TO RUN IN ITS OWN THREAD
    public void evolveTree2(View view){
        //undo_push(evolution.getPop());
        bar.setVisibility(ProgressBar.VISIBLE);
        if (stack_size == max_stack_size) {
        undoStack.remove(0);
        stack_size--;
        }
        undoStack.push(evolution.getPop());
        stack_size++;

        evolution.geneticAlgorithm(1);
        drawView1.drawTree(evolution.getPop()[0]);
        drawView2.drawTree(evolution.getPop()[1]);
        bar.setVisibility(ProgressBar.INVISIBLE);
    }
     public void evolveClicked(View view){
         evolution.geneticAlgorithm();
         drawView1.drawTree(evolution.getPop()[0]);
         drawView2.drawTree(evolution.getPop()[1]);
    }

    private void undo(){
        if(!undoStack.empty()) {
            //Tree[] pop = undo_pop();
            evolution.setPop(undoStack.peek());
            //drawView1.drawTree(evolution.getPop()[0]);
            //drawView2.drawTree(evolution.getPop()[1]);
            drawView1.drawTree(undoStack.peek()[0]);
            drawView2.drawTree(undoStack.peek()[1]);
            undoStack.pop();
            stack_size--;
        }
    }

    public void undo1(View view){
        if(!undoStack.empty()) {
            //Tree[] pop = undo_pop();
            evolution.setPop(undoStack.peek());
            //drawView1.drawTree(evolution.getPop()[0]);
            //drawView2.drawTree(evolution.getPop()[1]);
            drawView1.drawTree(undoStack.peek()[0]);
            drawView2.drawTree(undoStack.peek()[1]);
            undoStack.pop();
            stack_size--;
        }
    }

    private void redo(){
        if(!redoStack.empty()){
            Tree[] pop = redoStack.pop();
            evolution.setPop(pop);
            drawView1.drawTree(pop[0]);
            drawView2.drawTree(pop[1]);
           // undo_push(pop);
            if (stack_size == max_stack_size) {
                undoStack.remove(0);
                stack_size--;
            }
            undoStack.push(pop);
            stack_size++;
        }
    }
    private void undo_push(Tree[] pop) {
        if (stack_size == max_stack_size) {
            undoStack.remove(0);
            stack_size--;
        }
        undoStack.push(pop);
        stack_size++;
    }

    private Tree[] undo_pop(){
        stack_size--;
        return undoStack.pop();
    }
}
