package wizrole.hosadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.authority.model.getcompay.CompayList;

/**
 * Created by 何人执笔？ on 2018/4/18.
 * liushengping
 * 添加设备--设备类型
 */

public class SpinnerDeviceTypeAdapter extends BaseAdapter {
    public Context context;
    public List<String> compayLists;
    public LayoutInflater layoutInflater;
    public SpinnerDeviceTypeAdapter(Context context, List<String> compayList){
        this.compayLists=compayList;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return compayLists.size();
    }

    @Override
    public Object getItem(int position) {
        return compayLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class Holder{
        TextView textView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if(convertView==null){
            holder=new Holder();
            convertView=layoutInflater.inflate(R.layout.spinner_item_sel,null);
            holder.textView=(TextView)convertView.findViewById(R.id.text_spinner_item);
            convertView.setTag(holder);
        }else{
            holder=(Holder)convertView.getTag();
        }
        String compayList=compayLists.get(position);
        holder.textView.setText(compayList);
        return convertView;
    }
}
