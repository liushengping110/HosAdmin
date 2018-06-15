package wizrole.hosadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.authority.model.getdeparent.DeparentList;
import wizrole.hosadmin.authority.model.getidentity.IdentityList;

/**
 * Created by 何人执笔？ on 2018/4/18.
 * liushengping
 */

public class SpinnerDeparentAdapter extends BaseAdapter {
    public Context context;
    public List<DeparentList> compayLists;
    public LayoutInflater layoutInflater;
    public SpinnerDeparentAdapter(Context context, List<DeparentList> compayList){
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
        DeparentList compayList=compayLists.get(position);
        holder.textView.setText(compayList.getDeparentName());
        return convertView;
    }
}
