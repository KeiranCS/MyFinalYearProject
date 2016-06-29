package keiran.finalyearproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InitialisePopulation extends AppCompatActivity {

    private DrawingView treeView1;
    private DrawingView treeView2;
    private DrawingView treeView3;
    private DrawingView treeView4;
    private DrawingView treeView5;
    private DrawingView treeView6;
    private Evolution evolution;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialise_population);
        treeView1 = (DrawingView)findViewById(R.id.tree1);
        treeView2 = (DrawingView)findViewById(R.id.tree2);
        treeView3 = (DrawingView)findViewById(R.id.tree3);
        treeView4 = (DrawingView)findViewById(R.id.tree4);
        treeView5 = (DrawingView)findViewById(R.id.tree5);
        treeView6 = (DrawingView)findViewById(R.id.tree6);
        evolution = new Evolution();
        treeView1.drawTree(evolution.getPop()[0]);
        treeView2.drawTree(evolution.getPop()[1]);
        treeView3.drawTree(evolution.getPop()[2]);
        treeView4.drawTree(evolution.getPop()[3]);
        treeView5.drawTree(evolution.getPop()[4]);
        treeView6.drawTree(evolution.getPop()[5]);
    }
}
