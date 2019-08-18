package com.sscompany.ireview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sscompany.ireview.Elements.Item;

import java.util.ArrayList;

public class AdapterForItemList extends ArrayAdapter<Item>
{
    public AdapterForItemList(Context context, ArrayList<Item> items)
    {
        super(context, 0, items);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Item item = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_row, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.textViewName);

        name.setText(item.getTitle());

        return convertView;

    }
}
