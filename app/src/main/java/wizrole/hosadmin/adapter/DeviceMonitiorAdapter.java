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
import wizrole.hosadmin.maintenance.model.monitior.MonitiorDetail;
import wizrole.hosadmin.maintenance.model.monitior.MonitiorList;
import wizrole.hosadmin.util.image.ImageLoading;
import wizrole.hosadmin.view.CustGridView;

/**
 * Created by liushengping on 2018/3/14.
 * 何人执笔？
 */

public class DeviceMonitiorAdapter extends BaseAdapter implements DeviceMonDetailAdapter.GridViewItemOnClick{

    public Context context;
    public List<MonitiorList> monitiorLists;
    public LayoutInflater inflater;
    public DeviceMonitiorAdapter(Context context,List<MonitiorList> monitiorLists){
        this.monitiorLists=monitiorLists;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return monitiorLists.size();
    }

    @Override
    public Object getItem(int position) {
        return monitiorLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void gridItemOnClick(MonitiorDetail monitiorDetail) {
        if (gridViewItemOnClick!=null){
            gridViewItemOnClick.gridItemOnClick(monitiorDetail);
        }
    }

    class Holder{
        ImageView img_device_logo;
        CustGridView custGridView;
        TextView text_device_type;
        TextView text_device_group;
        TextView text_device_name;
        TextView text_device_ip;
        TextView text_device_mac;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if(convertView==null){
            holder=new Holder();
            convertView=inflater.inflate(R.layout.list_devicemonitior_item,null);
            holder.img_device_logo=(ImageView)convertView.findViewById(R.id.img_device_logo);
            holder.custGridView=(CustGridView)convertView.findViewById(R.id.grid_item_view);
            holder.text_device_type=(TextView) convertView.findViewById(R.id.text_device_type);
            holder.text_device_group=(TextView) convertView.findViewById(R.id.text_device_group);
            holder.text_device_name=(TextView) convertView.findViewById(R.id.text_device_name);
            holder.text_device_ip=(TextView) convertView.findViewById(R.id.text_device_ip);
            holder.text_device_mac=(TextView) convertView.findViewById(R.id.text_device_mac);
            convertView.setTag(holder);
        }else{
            holder=(Holder)convertView.getTag();
        }
        MonitiorList monitiorList=monitiorLists.get(position);
        ImageLoading.common(context,R.drawable.device_gh,holder.img_device_logo,R.drawable.img_loadfail);
        holder.text_device_name.setText(monitiorList.getDeviceNo());
        holder.text_device_group.setText(monitiorList.getDeviceGroup());
        holder.text_device_ip.setText(monitiorList.getDeviceIP());
        holder.text_device_mac.setText(monitiorList.getDeviceMac());
        holder.text_device_type.setText(monitiorList.getDeviceType());
        DeviceMonDetailAdapter adapter=new DeviceMonDetailAdapter(context,monitiorList.getMonitiorDetailList());
        holder.custGridView.setAdapter(adapter);
        adapter.setGridViewItemOnClick(this);
        return convertView;
    }

    /**
     * 回调到activity，做点击拦截
     */
    public MonViewItemOnClick gridViewItemOnClick;
    public interface MonViewItemOnClick{
        void gridItemOnClick(MonitiorDetail monitiorDetail);
    }

    public void setGridViewItemOnClick(MonViewItemOnClick gridViewItemOnClick) {
        this.gridViewItemOnClick = gridViewItemOnClick;
    }
}
