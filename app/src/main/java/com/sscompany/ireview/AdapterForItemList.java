package com.sscompany.ireview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sscompany.ireview.Elements.InterfaceItem;

import java.util.ArrayList;

public class AdapterForItemList extends ArrayAdapter<InterfaceItem>
{
    public AdapterForItemList(Context context, ArrayList<InterfaceItem> items)
    {
        super(context, 0, items);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        InterfaceItem item = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_row, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.textViewName);

        name.setText(item.getName());

        return convertView;

    }
}
