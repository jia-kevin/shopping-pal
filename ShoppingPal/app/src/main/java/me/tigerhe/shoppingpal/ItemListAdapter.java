package me.tigerhe.shoppingpal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ItemListAdapter extends ArrayAdapter<String> {

    private HashMap<String, Integer> initialPositionMap;

    private List<String> mList;
    private int checkedCount;

    static class ViewHolder {
        public TextView text;
        public CheckBox checkBox;
    }

    public ItemListAdapter(Context context, List<String> list) {
        super(context, R.layout.item_task, list);
        mList = list;
        checkedCount = 0;
        initialPositionMap = new HashMap<>();
    }

    public void updateList(List<String> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void addChecked(int position) {
        mList.add(mList.get(position));
        mList.remove(position);
        notifyDataSetChanged();
    }

    public void removeChecked(int position) {
        String task = mList.get(position);
        int initialIndex = initialPositionMap.get(task);
        for (int i = 0; i < mList.size() - checkedCount; i++) {
            if (initialPositionMap.get(mList.get(i)) > initialIndex) {
                mList.add(i, task);
            }
        }
        mList.remove(position + 1);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_task, parent, false);

            ViewHolder holder = new ViewHolder();
            holder.text = (TextView) rowView.findViewById(R.id.task_name);
            holder.checkBox = (CheckBox) rowView.findViewById(R.id.task_checkbox);
            rowView.setTag(holder);

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    final int pos = position;
                    if (b) {
                        addChecked(pos);
                        checkedCount++;
                        compoundButton.setChecked(false);

                    } else {
                        removeChecked(pos);
                        checkedCount--;
                    }
                }
            });

            initialPositionMap.put(mList.get(position), position);

        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.text.setText(mList.get(position));

//        TextView text = (TextView) view.findViewById(R.id.task_name);
//        text.setText(mList.get(position));

        return rowView;
    }


}
