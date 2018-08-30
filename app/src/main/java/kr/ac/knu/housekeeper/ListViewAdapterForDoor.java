package kr.ac.knu.housekeeper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ListViewAdapterForDoor extends BaseAdapter {

    // 내림차순
    class Descending implements Comparator<Date> {
        @Override
        public int compare(Date o1, Date o2) {
            return o2.compareTo(o1);
        }
    }

    private ArrayList<Date> list = new ArrayList<Date>();
    public boolean all_show = false;
    Descending comparable = new Descending();


    @Override
    public Object getItem(int i) {
        Date current_date = list.get(i);
        if(all_show == false && (current_date.getDate() != Calendar.getInstance().getTime().getDate()))
            return null;
        return current_date;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getCount() {
        int add_temp = 0;
        for(Date date : list)
        {
            if(all_show == false && (date.getDate() != Calendar.getInstance().getTime().getDate()))
                add_temp++;
        }
        return list.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        Date current_date = list.get(i);



        if(all_show == false && (current_date.getDate() != Calendar.getInstance().getTime().getDate())) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_view_empty_door, viewGroup, false);
            view.setVisibility(View.INVISIBLE);
            view.setEnabled(false);
        }
        else{
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_view_door, viewGroup, false);
            TextView topView = (TextView) view.findViewById(R.id.top_text);
            TextView textView = (TextView) view.findViewById(R.id.date_text);

            SimpleDateFormat date_format = new SimpleDateFormat("aa KK:mm:ss");
            SimpleDateFormat top_format = new SimpleDateFormat("yyyy-MM-dd");


            if(current_date.getHours() >= 12)
                ((ImageView)view.findViewById(R.id.cute_image)).setImageResource(R.drawable.moon);
            else
                ((ImageView)view.findViewById(R.id.cute_image)).setImageResource(R.drawable.sun);

            textView.setText(date_format.format(current_date).toString());
            topView.setText(top_format.format(current_date).toString());
        }



        return view;
    }

    public void addList(Date new_Date)
    {
        list.add(new_Date);
        Collections.sort(list, comparable);
        this.notifyDataSetChanged();
    }

}
