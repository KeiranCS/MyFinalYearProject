package keiran.finalyearproject;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Keiran on 24/02/2016.
 */
public class ElitismOnClickListener implements AdapterView.OnItemClickListener {

    private int selectedTree = -1;
    private boolean elitismState;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedTree = position;
    }

    public void resetSelectedTree(){
        this.selectedTree = -1;
    }

    public int getSelectedTree(){
        return selectedTree;
    }

    public void setSelectedTree(int val){
        this.selectedTree = val;
    }
}
