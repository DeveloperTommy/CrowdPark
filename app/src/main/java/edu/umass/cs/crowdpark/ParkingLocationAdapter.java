package edu.umass.cs.crowdpark;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tommy on 4/25/2016.
 */
public class ParkingLocationAdapter extends BaseAdapter {

    //Columns
    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";
    public static final String FOURTH_COLUMN="Fourth";
    public static final String FIFTH_COLUMN="Fifth";
    public static final String SIXTH_COLUMN="Sixth";
    public static final String SEVENTH_COLUMN="Seventh";


    //Textviews for each column.
    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;
    TextView txtThird;
    TextView txtFourth;
    TextView txtFifth;
    TextView txtSixth;
    TextView txtSeventh;

    public ParkingLocationAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){
            convertView=inflater.inflate(R.layout.column_row, null);

            txtFirst = (TextView) convertView.findViewById(R.id.distance);
            txtSecond = (TextView) convertView.findViewById(R.id.name);
            txtThird = (TextView) convertView.findViewById(R.id.cost);
            txtFourth = (TextView) convertView.findViewById(R.id.space);
            txtFifth = (TextView) convertView.findViewById(R.id.time);
            //Sixth and seventh are invisible to grab locations to compute distance
            txtSixth = (TextView) convertView.findViewById(R.id.lat);
            txtSeventh = (TextView) convertView.findViewById(R.id.lon);
        }

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(FIRST_COLUMN));
        txtSecond.setText(map.get(SECOND_COLUMN));
        txtThird.setText(map.get(THIRD_COLUMN));
        txtFourth.setText(map.get(FOURTH_COLUMN));
        txtFifth.setText(map.get(FIFTH_COLUMN));
        txtSixth.setText(map.get(SIXTH_COLUMN));
        txtSeventh.setText(map.get(SEVENTH_COLUMN));

        return convertView;
    }

}
