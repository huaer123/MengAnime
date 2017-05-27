package com.menganime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.menganime.R;
import com.menganime.bean.CartoonNameByKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/27.
 */

public class EditTextPromptAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<CartoonNameByKey.CartoonName> mList;
    private List<CartoonNameByKey.CartoonName> mUnfilteredData;

    private ArrayFilter mFilter;

    public EditTextPromptAdapter(Context context,List<CartoonNameByKey.CartoonName> mList) {
        this.context = context;
        this.mList = mList;
        this.layoutInflater = LayoutInflater.from(context);
    }



    @Override
    public int getCount() {
        // How many items are in the data set represented by this Adapter.(在此适配器中所代表的数据集中的条目数)
        return mList==null ? 0:mList.size();
    }

    @Override
    public Object getItem(int position) {
        // Get the data item associated with the specified position in the data set.(获取数据集中与指定索引对应的数据项)
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Get the row id associated with the specified position in the list.(取在列表中与指定索引对应的行id)
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_own_dropdown,
                    null);
            holder.tv_keyName = (TextView) convertView
                    .findViewById(R.id.tv_keyName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CartoonNameByKey.CartoonName cartoonInfo = mList.get(position);
        holder.tv_keyName.setText(cartoonInfo.getName());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<CartoonNameByKey.CartoonName>(mList);
            }

            if (prefix == null || prefix.length() == 0) {
                List<CartoonNameByKey.CartoonName> list = mUnfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                //String prefixString = prefix.toString().toLowerCase();

                List<CartoonNameByKey.CartoonName> unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();

                ArrayList<CartoonNameByKey.CartoonName> newValues = new ArrayList<CartoonNameByKey.CartoonName>(count);

                for (int i = 0; i < count; i++) {
                    CartoonNameByKey.CartoonName pc = unfilteredValues.get(i);
                    if (pc != null) {

                        if(pc.getName()!=null){// && pc.getName().startsWith(prefixString)){

                            newValues.add(pc);
                        }else if(pc.getMH_Type_ID()!=null){// && pc.getMH_Type_ID().startsWith(prefixString)){

                            newValues.add(pc);
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            //noinspection unchecked
            mList = (List<CartoonNameByKey.CartoonName>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }

    class ViewHolder {
        TextView tv_keyName;
    }

    public void setList(List<CartoonNameByKey.CartoonName> list) {
        // TODO Auto-generated method stub
        this.mList.clear();
        this.mList = list;
    }
}
