package com.example.mostafa.bookgo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        View listitemview = convertView ;
         if (listitemview == null ){
         listitemview =LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
         }
     Book currentbook = getItem(position);
        ImageView pic = (ImageView)listitemview.findViewById(R.id.pic);
        TextView title = (TextView)listitemview.findViewById(R.id.title);
        TextView auther = (TextView)listitemview.findViewById(R.id.auther);
        TextView price = (TextView)listitemview.findViewById(R.id.price);
        TextView riating =(TextView) listitemview.findViewById(R.id.rating);
        TextView curency = (TextView)listitemview.findViewById(R.id.curuncy) ;



        Picasso.get().load(currentbook.getpic()).into(pic);
       title.setText(currentbook.gettitle());
        auther.setText(currentbook.getauthor());
        price.setText(currentbook.getprice());
        riating.setText(currentbook.getrating());
        curency.setText(currentbook.getcurrencycode());

        return listitemview;
    }
}
