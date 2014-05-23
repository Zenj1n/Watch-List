package com.zenjin.watchlist.watchlist;



import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 *
 */


public class Discover extends Fragment {

    ListView list;
    String[] hmTitles;
    String[] hmDescriptions;
    int[]images={R.drawable.arrow, R.drawable.black_ish, R.drawable.constatine,R.drawable.game_of_thrones,R.drawable.gotham,
            R.drawable.gracepoint, R.drawable.lost, R.drawable.supernatural, R.drawable.the_flash};

    public Discover() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false);
        Resources res = getResources();
        hmTitles =res.getStringArray(R.array.Hm_Titles);
        hmDescriptions =res.getStringArray(R.array.Hm_Description);

        list=(ListView) getView().findViewById(R.id.popularListView);

        Hm_Popular_Adaptar adaptar=new Hm_Popular_Adaptar(this, hmTitles, images, hmDescriptions);
        list.setAdapter(adaptar);

    }


}

class Hm_Popular_Adaptar extends ArrayAdapter<String>
{
    Context context;
    int[] images;
    String[] titleArray;
    String[] descriptionArray;
    Hm_Popular_Adaptar(Context c, String[] titles, int imgs[], String[] desc)
    {
        super(c, R.layout.single_row,R.id.titleTextView,titles);
        this.context=c;
        this.images=imgs;
        this.titleArray=titles;
        this.descriptionArray=desc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.single_row,parent,false);
        ImageView popularImage= (ImageView) row.findViewById(R.id.imageView);
        TextView popularTitle = (TextView) row.findViewById(R.id.titleTextView);
        TextView popularDescription= (TextView) row.findViewById(R.id.descriptionTextView3);

        popularImage.setImageResource(images[position]);
        popularTitle.setText(titleArray[position]);
        popularDescription.setText(descriptionArray[position]);

        return row;
    }

}