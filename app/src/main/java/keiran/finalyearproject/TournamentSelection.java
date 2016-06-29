package keiran.finalyearproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TournamentSelection extends Activity {

    DrawTree treeDrawing1;
    DrawTree treeDrawing2;

    ImageView treeView1;
    ImageView treeView2;

    Tree tree1;
    Tree tree2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_selection);

        treeView1 = (ImageView) findViewById(R.id.selection1);
        treeView2 = (ImageView) findViewById(R.id.selection2);

        treeDrawing1 = new DrawTree();
        treeDrawing2 = new DrawTree();

        Intent intent = getIntent();
        tree1 = intent.getExtras().getParcelable("tree1");
        tree2 = intent.getExtras().getParcelable("tree2");

        treeDrawing1.drawTree(tree1);
        treeDrawing2.drawTree(tree2);
        Bitmap treeBitmap1 = treeDrawing1.getBitmap();
        Bitmap treeBitmap2 = treeDrawing2.getBitmap();

        treeView1.setImageBitmap(treeBitmap1);
        treeView2.setImageBitmap(treeBitmap2);
    }

    public void select1(View view){
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putParcelable("tree", tree1);
        intent.putExtras(b);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void select2(View view){
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putParcelable("tree", tree2);
        intent.putExtras(b);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
