package com.example.vincentmoriarty.sound_of_island;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Items> {
    private int resourceId;
    public ItemAdapter(Context context, int textViewResourceId, List<Items>objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Items items = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView itemImage = (ImageView)view.findViewById(R.id.item_image);
        ImageView itemImage2 = (ImageView)view.findViewById(R.id.item_image2);
        TextView itemName = (TextView)view.findViewById(R.id.item_name);
        TextView itemDescription = (TextView)view.findViewById(R.id.item_description);
        itemImage.setImageResource(items.getImageId());
        itemImage2.setImageResource(items.getImage2Id());
        itemName.setText(items.getName());
        itemDescription.setText(items.getDescription());
        return view;
    }
}
