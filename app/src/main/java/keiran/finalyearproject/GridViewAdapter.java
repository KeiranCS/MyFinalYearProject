package keiran.finalyearproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Keiran on 15/02/2016.
 */
public class GridViewAdapter extends BaseAdapter {

    Activity context;
    Bitmap[] gridViewItems;


    public GridViewAdapter(Activity context, Bitmap[] bitmaps){
        this.context = context;
        this.gridViewItems = bitmaps;
    }
    @Override
    public int getCount() {
        return gridViewItems.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return super.hasStableIds();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(500, 500));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(8, 8, 8, 8);


        }else{
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(gridViewItems[position]);
        return imageView;
    }
}
