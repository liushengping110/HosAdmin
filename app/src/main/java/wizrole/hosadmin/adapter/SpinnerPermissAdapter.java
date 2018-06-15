package wizrole.hosadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.authority.model.getidentity.IdentityList;
import wizrole.hosadmin.authority.model.getrolelist.Datas;

/**
 * Created by 何人执笔？ on 2018/4/24.
 * liushengping
 */

public class SpinnerPermissAdapter extends BaseAdapter {

    public Context context;
    public List<Datas> compayLists;
    public LayoutInflater layoutInflater;
    public SpinnerPermissAdapter(Context context, List<Datas> compayList){
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
        Datas compayList=compayLists.get(position);
        holder.textView.setText(compayList.getPermissionDec());
        return convertView;
    }
}
