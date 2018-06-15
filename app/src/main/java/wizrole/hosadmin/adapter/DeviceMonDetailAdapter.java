package wizrole.hosadmin.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.maintenance.model.monitior.MonitiorDetail;

/**
 * Created by liushengping on 2018/3/14.
 * 何人执笔？
 */

public class DeviceMonDetailAdapter extends BaseAdapter {
    public Context context;
    public List<MonitiorDetail> monitiorDetails;
    public LayoutInflater inflater;
    public DeviceMonDetailAdapter(Context context,List<MonitiorDetail> monitiorDetails){
        this.context=context;
        this.monitiorDetails=monitiorDetails;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return monitiorDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return monitiorDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class Holder{
        TextView text_griditem_status;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if(convertView==null){
            holder=new Holder();
            convertView=inflater.inflate(R.layout.grid_monitior_item,null);
            holder.text_griditem_status=(TextView)convertView.findViewById(R.id.text_griditem_status);
            convertView.setTag(holder);
        }else{
            holder=(Holder)convertView.getTag();
        }
        final MonitiorDetail detail=monitiorDetails.get(position);
        holder.text_griditem_status.setText(detail.getPartName());
        if(detail.getPartStatusContent().equals("正常")){
            holder.text_griditem_status.setBackgroundResource(R.drawable.device_on_status);
        }else{
            holder.text_griditem_status.setBackgroundResource(R.drawable.device_off_status);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gridViewItemOnClick!=null){
                    gridViewItemOnClick.gridItemOnClick(detail);
                }
            }
        });
        return convertView;
    }

    /**
     * 先回调到外层adapter，需要再activity中做点击拦截
     */
    public GridViewItemOnClick gridViewItemOnClick;
    public interface GridViewItemOnClick{
        void gridItemOnClick(MonitiorDetail monitiorDetail);
    }

    public void setGridViewItemOnClick(GridViewItemOnClick gridViewItemOnClick) {
        this.gridViewItemOnClick = gridViewItemOnClick;
    }
}
