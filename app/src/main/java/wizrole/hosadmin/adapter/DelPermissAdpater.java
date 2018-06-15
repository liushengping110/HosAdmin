package wizrole.hosadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.authority.model.getrolelist.Datas;

/**
 * Created by 何人执笔？ on 2018/4/24.
 * liushengping
 * 删除主权限 适配器
 */

public class DelPermissAdpater extends BaseAdapter {
    public Context context;
    public List<Datas> compayLists;
    public LayoutInflater layoutInflater;
    public DelPermissAdpater(Context context,List<Datas> compayList){
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
        ImageView img_no_sel;
        ImageView img_sel;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if(convertView==null){
            holder=new Holder();
            convertView=layoutInflater.inflate(R.layout.lsit_pop_permiss_item,null);
            holder.textView=(TextView)convertView.findViewById(R.id.text_permiss_name);
            holder.img_no_sel=(ImageView)convertView.findViewById(R.id.img_no_sel);
            holder.img_sel=(ImageView)convertView.findViewById(R.id.img_sel);
            convertView.setTag(holder);
        }else{
            holder=(Holder)convertView.getTag();
        }
        Datas compayList=compayLists.get(position);
        holder.textView.setText(compayList.getPermissionDec());
        if(compayList.isSel_status()){
            holder.img_sel.setVisibility(View.VISIBLE);
            holder.img_no_sel.setVisibility(View.INVISIBLE);
        }else{
            holder.img_sel.setVisibility(View.INVISIBLE);
            holder.img_no_sel.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
}
