package keiran.finalyearproject;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class initialisePopulationImages extends Activity implements AdapterView.OnItemClickListener {


    DrawTree tree1;
    DrawTree tree2;
    DrawTree tree3;
    DrawTree tree4;
    DrawTree tree5;
    DrawTree tree6;

    DrawTree drawTree;
    GridView gridView;
    Bitmap[] bitmaps;
    Tree selectedTree = null;

    AlertBox popup;

    protected final int gridSize = 6;
    protected final boolean elitism = false;
    protected final int elitismNo = 1; //must be 2 less than gridsize for selection to work
    protected final float Pc = 0.9f;
    protected final float Pm = 0.7f;

    protected final Evolution evolution = new Evolution();
    protected final Random RNG = new Random();

    private int gridIndex;
    private Tree[] newPop;
    private boolean isElitism = false;
    private boolean isCrossover = false;
    private boolean isTournament = false;
    private boolean isMutation = false;

    private int selectedTree1 = -1;
    private int selectedTree2 = -1;

    private int[] elitismTrees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialise_population_images);
        gridView = (GridView) findViewById(R.id.gridView);
        popup = new AlertBox();

        evolution.initializePopulation();
        drawTree = new DrawTree();

        bitmaps = new Bitmap[evolution.getInit_pop_size()];

        tree1 = new DrawTree();
        tree2 = new DrawTree();
        tree3 = new DrawTree();
        tree4 = new DrawTree();
        tree5 = new DrawTree();
        tree6 = new DrawTree();

        gridIndex = 0;
        newPop = new Tree[6];

        createBitmaps();


/*
        try {
            createBitmaps();
        }catch (Exception e){
            e.printStackTrace();
        }
        */
        /*
        for(int i = 0; i < evolution.getInit_pop_size(); i++){
            drawTree.drawTree(evolution.getPop()[i]);
            bitmaps[i] = drawTree.getBitmap();
        }

*/
        //ArrayAdapter<Bitmap> adapter = new ArrayAdapter<Bitmap>(this, android.R.layout.simple_list_item_1, bitmaps);


        initialiseStates();




        //EA();

/*
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Bundle b = new Bundle();
                b.putParcelable("tree", evolution.getPop()[position]);
                intent.putExtras(b);
                //Tree tree = evolution.getPop()[position];
                //intent.putExtra("tree", tree);
                //ArrayList<Tree> tree = new ArrayList<Tree>();
                //tree.add(evolution.getPop()[position]);
                //intent.putParcelableArrayListExtra("tree", tree);
                //intent.putExtra("V",evolution.getPop()[position].getV());
                //intent.putExtra("Tree_String", evolution.getPop()[position].getTree());
                //intent.putParcelableArrayListExtra("tree_Params", evolution.getPop()[position].getParams());
                //intent.putExtra("tree", evolution.getPop()[position]);
                //intent.putExtra("params", );
                //intent.putExtra("tree", evolution.getPop()[position].getParams().toArray(new float[0]));
                startActivity(intent);

            }
        });
        */

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1){
            selectedTree = data.getExtras().getParcelable("tree");
            newPop[gridIndex] =  selectedTree; //value obtained from onActivityResult
            gridIndex++;
            isTournament = false;
            //If new population is full, perform mutation. Else either crossover or tournament again
            changeStates();
        }
    }

    private void createBitmaps() {

        tree1.drawTree(evolution.getPop()[0]);
        tree2.drawTree(evolution.getPop()[1]);
        tree3.drawTree(evolution.getPop()[2]);
        tree4.drawTree(evolution.getPop()[3]);
        tree5.drawTree(evolution.getPop()[4]);
        tree6.drawTree(evolution.getPop()[5]);
        bitmaps[0] = tree1.getBitmap();
        bitmaps[1] = tree2.getBitmap();
        bitmaps[2] = tree3.getBitmap();
        bitmaps[3] = tree4.getBitmap();
        bitmaps[4] = tree5.getBitmap();
        bitmaps[5] = tree6.getBitmap();
        gridView.setAdapter(new GridViewAdapter(this, bitmaps));
        gridView.setOnItemClickListener(this);
        //bitmaps = drawTree.execute(evolution.getPop()).get();
    }
/*
    public void EA() {
        Log.d("keiran.finalyearproject", "EA Started");
        Random RNG = new Random();
        int gridMember = 0;
        int[] elitismTrees;
        final Tree[] newPop = new Tree[gridSize];
        if (elitism) {
            //select trees for elitism
            ElitismOnClickListener elitismOnClickListener = new ElitismOnClickListener();
            gridView.setOnItemClickListener(elitismOnClickListener);
            elitismTrees = new int[elitismNo];
            while (gridMember < elitismNo) {
                if (elitismOnClickListener.getSelectedTree() != -1) {
                    newPop[gridMember] = evolution.getPop()[elitismOnClickListener.getSelectedTree()];
                    elitismTrees[gridMember] = elitismOnClickListener.getSelectedTree();
                    elitismOnClickListener.resetSelectedTree();
                    gridMember++;
                    Log.d("keiran.finalyearproject", "Elitist Tree selected");
                }
            }
        }

        Log.d("keiran.finalyearproject", "crossover started");
        while (gridMember < gridSize) {
            Log.d("keiran.finalyearproject", "begin crossover");

            //Tree newTree = evolution.getPop()[gridMember];
            Tree[] trees;
            if (RNG.nextFloat() < Pc) {
                //perform crossover

                //alert user

                //select trees for crossover
                CrossoverOnClickListener crossoverOnClickListener = new CrossoverOnClickListener();
                gridView.setOnItemClickListener(crossoverOnClickListener);
                Tree selectedTree1 = null;
                Tree selectedTree2 = null;
                boolean selecting = true;
                while (selecting) {
                    if (crossoverOnClickListener.getTreeSelected1() != -1 && selectedTree1 == null)
                        selectedTree1 = evolution.getPop()[crossoverOnClickListener.getTreeSelected1()];
                    if (crossoverOnClickListener.getTreeSelected2() != -1 && selectedTree2 == null) {
                        selectedTree2 = evolution.getPop()[crossoverOnClickListener.getTreeSelected2()];
                        selecting = false;
                    }
                }
                trees = evolution.crossover(selectedTree1, selectedTree2); //pass two selected trees for crossover
                if (gridMember + 1 == gridSize) {
                    Tree newTree = trees[RNG.nextInt(2)];
                    newPop[gridMember] = newTree;
                    gridMember++;
                } else {
                    newPop[gridMember] = trees[0];
                    gridMember++;
                    newPop[gridMember] = trees[1];
                    gridMember++;
                }
            } else {
                //tournament selection
                //randomly select 2 trees between elitismNo and gridSize and select between the 2
                int randomTree1 = RNG.nextInt(gridSize);
                int randomTree2 = RNG.nextInt(gridSize);
                if (elitism) {
                    while(contains(elitismTrees, randomTree1)) {
                        randomTree1 = RNG.nextInt(gridSize);
                    }
                    while(contains(elitismTrees, randomTree2)) {
                        randomTree2 = RNG.nextInt(gridSize);
                    }
                    while (randomTree1 == randomTree2){ //elitismNo needs to be limited so this does not run infinitely
                        randomTree2 = RNG.nextInt(gridSize);
                        while(contains(elitismTrees, randomTree2)) {
                            randomTree2 = RNG.nextInt(gridSize);
                        }
                    }

                } else {
                    while (randomTree1 == randomTree2) {
                        randomTree2 = RNG.nextInt(gridSize);
                    }
                }
                Intent intent = new Intent(getApplicationContext(), TournamentSelection.class);
                Bundle b = new Bundle();
                b.putParcelable("tree1", evolution.getPop()[randomTree1]);
                b.putParcelable("tree2", evolution.getPop()[randomTree2]);
                intent.putExtras(b);
                startActivityForResult(intent, 1);

                newPop[gridMember] = selectedTree;

/*
                if (RNG.nextFloat() < Pm) {
                    //perform mutation
                    trees[0] = evolution.mutateTree();
                    if (trees.length == 2)
                        trees[1] = evolution.mutateTree();

                }
                //insert offspring into population
                newPop[gridMember] = trees[0];
                gridMember++;
                if (trees.length == 2) {
                    newPop[gridMember] = trees[1];
                    gridMember++;
                }

            }
        }
    }*/

    public boolean contains (int[] arr, int val){
        for(int element : arr){
            if(element == val){
                return true;
            }
        }
        return false;
    }

    private void elitism(int position){
        Log.d("elitism", "elitism Start");
        newPop[gridIndex] = evolution.getPop()[position];
        elitismTrees[gridIndex] = position;
        gridIndex++;
        changeStates();
    }

    private void crossover(int position){
        Log.d("Crossover", "Crossover Start");
        if (selectedTree1 == -1) {
            selectedTree1 = position;
            //gridView.getChildAt(position).setBackgroundResource(R.drawable.view_divider);
            Log.d("Crossover", "selectedTree1: " + selectedTree1);

        } else if(selectedTree2 == -1) {
            selectedTree2 = position;
            Log.d("Crossover", "selectedTree2: " + selectedTree2);

            //gridView.getChildAt(position).setBackgroundResource(R.drawable.view_divider);
            Tree[] trees;
            Log.d("Crossover SelectedTree", evolution.getPop()[selectedTree1].getTree().length() + ", " + evolution.getPop()[selectedTree1].getParams().size());
            trees = evolution.crossover(evolution.getPop()[selectedTree1], evolution.getPop()[selectedTree2]);
            Log.d("Crossover SelectedTree", evolution.getPop()[selectedTree1].getTree().length() + ", " + evolution.getPop()[selectedTree1].getParams().size());
            selectedTree1 = -1;
            selectedTree2 = -1;
            //If there is no enough space for both trees then select one at random
            if (gridIndex + 1 == gridSize) {
                newPop[gridIndex] = trees[RNG.nextInt(2)];
                gridIndex++;
            } else {
                newPop[gridIndex] = trees[0];
                gridIndex++;
                newPop[gridIndex] = trees[1];
                gridIndex++;
            }
            //isCrossover = false;

            showImagePopUp(this, "Crossover", "Children Produced!", trees[0], trees[1]);
            //showPopup(this, "Crossover End", "Children Produced!");
            //If new population is full, perform mutation. Else either crossover or tournament again
            //changeStates();
        }
    }

    private void tournamentSelection(){
        Log.d("tournamentSelection", "Tournament Selection Start");
        //randomly select 2 trees between elitismNo and gridSize and select between the 2
        int randomTree1 = RNG.nextInt(gridSize);
        int randomTree2 = RNG.nextInt(gridSize);
        if (elitism) {
            while(contains(elitismTrees, randomTree1)) {
                randomTree1 = RNG.nextInt(gridSize);
            }
            while(contains(elitismTrees, randomTree2)) {
                randomTree2 = RNG.nextInt(gridSize);
            }
            while (randomTree1 == randomTree2){ //elitismNo needs to be limited so this does not run infinitely
                randomTree2 = RNG.nextInt(gridSize);
                while(contains(elitismTrees, randomTree2)) {
                    randomTree2 = RNG.nextInt(gridSize);
                }
            }

        } else {
            while (randomTree1 == randomTree2) {
                randomTree2 = RNG.nextInt(gridSize);
            }
        }
        Intent intent = new Intent(getApplicationContext(), TournamentSelection.class);
        Bundle b = new Bundle();
        b.putParcelable("tree1", evolution.getPop()[randomTree1]);
        b.putParcelable("tree2", evolution.getPop()[randomTree2]);
        intent.putExtras(b);
        startActivityForResult(intent, 1);


    }

    private void mutation(){
        Log.d("Mutation", "Mutation Start");
        int i = 0;
        if(elitism){
            i = elitismNo; //no mutation performed on elitist trees
        }
        while (i < gridSize) {
            if (RNG.nextFloat() < Pm) {
                newPop[i] = evolution.mutateTree(newPop[i]);
            }
            i++;
        }
        isMutation = false;
        evolution.setPop(newPop);

        createBitmaps();
/*
            try{
                createBitmaps();
            }catch (Exception e){
                e.printStackTrace();
            }
*/
        //reinitialize variables for next population once mutation is over and newPop is full
        gridIndex = 0;

        initialiseStates();
    }

    public void changeStates(){
        if(gridIndex >= elitismNo) {
            isElitism = false;
            //If new population is full, perform mutation. Else either crossover or tournament again
            if (gridIndex == gridSize) {
                isCrossover = false;
                isTournament = false;
                isMutation = true;
                showPopup(this, "Mutation", "New population will now\n undergo mutation");
                //mutation();
                Log.d("changeStates", "Mutation Start");
            } else if (RNG.nextFloat() < Pc) {
                isCrossover = true;
                isTournament = false;
                showPopup(this, "Crossover", "Crossover\nSelect 2 trees to breed");
                Log.d("changeStates", "Crossover Start");
            } else {
                isCrossover = false;
                isTournament = true;
                showPopup(this, "Tournament Selection", "Tournament Selection\nSelect a tree to keep");
                Log.d("changeStates", "Tournament Selection Start");
            }
        }
    }

    public void initialiseStates(){
        if(elitism && elitismNo > 0) {
            isElitism = true;
            isCrossover = false;
            isTournament = false;
            elitismTrees = new int[elitismNo];
            //alert user to select elitist trees
            showPopup(this, "Elitism", "Elitism\nSelect trees you wish to keep");
            Log.d("initialiseStates", "Elitism Start");

        }else{
            isElitism = false;
            if(RNG.nextFloat() < Pc) {
                isCrossover = true;
                isTournament = false;
                //alert user for crossover
                showPopup(this, "Crossover", "Crossover\nSelect 2 trees to breed");
                Log.d("initialiseStates", "Crossover Start");

            }else{
                isCrossover = false;
                isTournament = true;
                //alert user for tournament selection
                showPopup(this, "Tournament Selection", "Tournament Selection\nSelect a tree to keep");
                Log.d("initialiseStates", "Tournament Selection start");
                //tournamentSelection();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(isElitism)
            elitism(position);

        if (isCrossover)
            crossover(position);

        if(isTournament)
            tournamentSelection();

        if(isMutation)
            //showPopup(this, "Mutation", "Now mutating new trees");
            mutation();
    }

/*
    public Bitmap resizeBitmap(Bitmap bitmap, int newHeight, int newWidth){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);
        //bitmap.recycle();
        return resizedBitmap;
    }
    */
public void showImagePopUp(Context context, String title, String message,  Tree tree1, Tree tree2){
    final Dialog dialog = new Dialog(context);
    dialog.setContentView(R.layout.image_popup_box);
    dialog.setTitle(title);

    ImageView imageView1 = (ImageView) dialog.findViewById(R.id.image1);
    ImageView imageView2 = (ImageView) dialog.findViewById(R.id.image2);
    DrawTree child1 = new DrawTree();
    DrawTree child2 = new DrawTree();
    child1.drawTree(tree1);
    child2.drawTree(tree2);
    imageView1.setImageBitmap(child1.getBitmap());
    imageView2.setImageBitmap(child2.getBitmap());


    TextView tv_message = (TextView) dialog.findViewById(R.id.message);
    tv_message.setText(message);

    Button ok = (Button) dialog.findViewById(R.id.ok);
    ok.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeStates();
            dialog.dismiss();
        }
    });

    dialog.show();

}
public void showPopup(Context context, final String title, String message)
{
    final Dialog dialog = new Dialog(context);
    dialog.setContentView(R.layout.popup_box);
    dialog.setTitle(title);

    TextView tv_message = (TextView) dialog.findViewById(R.id.message);
    tv_message.setText(message);

    Button ok = (Button) dialog.findViewById(R.id.ok);
    ok.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if(isTournament){
                tournamentSelection();
            }

            if(isMutation)
               mutation();
            dialog.dismiss();
        }
    });

    dialog.show();
}
}
