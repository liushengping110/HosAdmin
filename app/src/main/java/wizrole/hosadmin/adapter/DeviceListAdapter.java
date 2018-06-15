package wizrole.hosadmin.adapter;

import android.content.Context;

import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.base.ConcreteAdapter;
import wizrole.hosadmin.adapter.base.ViewHolder;
import wizrole.hosadmin.maintenance.model.devicelist.DeviceList;

/**
 * Created by liushengping on 2018/3/7.
 * 何人执笔？
 * 设备列表适配器
 */

public class DeviceListAdapter extends ConcreteAdapter<DeviceList> {
    public DeviceListAdapter(Context context, List<DeviceList> list, int itemLayout) {
        super(context, list, itemLayout);
    }

    @Override
    protected void convert(ViewHolder viewHolder, DeviceList item, int position) {
        viewHolder.setImageView(R.drawable.device_gh,R.id.img_device_logo,R.drawable.img_loadfail)
                .setText(item.getDeviceType(),R.id.text_device_type)
                .setText(item.getDeviceGroup(),R.id.text_device_group)
                .setText(item.getDeviceNo(),R.id.text_device_name)
                .setText(item.getDeviceIP(),R.id.text_device_ip)
                .setText(item.getDeviceMac(),R.id.text_device_mac);
        if(item.getDeviceStatus().equals("1")){//启用
            viewHolder.setText("已启用",R.id.text_device_status,R.drawable.device_on_status,R.color.white);
        }else{//未启用
            viewHolder.setText("未启用",R.id.text_device_status,R.drawable.device_off_status,R.color.white);
        }
    }
}
