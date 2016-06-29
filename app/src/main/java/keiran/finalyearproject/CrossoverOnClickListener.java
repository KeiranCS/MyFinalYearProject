package keiran.finalyearproject;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Keiran on 24/02/2016.
 */
public class CrossoverOnClickListener implements AdapterView.OnItemClickListener {



    private int treeSelected1 = -1;
    private int treeSelected2 = -1;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //USER IS ABLE TO SELECT THE SAME TREE TWICE
        if(treeSelected1 == -1)
            treeSelected1 = position;
        else if(treeSelected2 == -1)
            treeSelected2 = position;

    }

    public void resetTrees(){
        this.treeSelected1 = -1;
        this.treeSelected2 = -1;
    }

    public int getTreeSelected1() {
        return treeSelected1;
    }

    public void setTreeSelected1(int treeSelected1) {
        this.treeSelected1 = treeSelected1;
    }

    public int getTreeSelected2() {
        return treeSelected2;
    }

    public void setTreeSelected2(int treeSelected2) {
        this.treeSelected2 = treeSelected2;
    }

}
