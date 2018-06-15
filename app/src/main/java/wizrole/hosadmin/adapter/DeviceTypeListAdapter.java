package wizrole.hosadmin.adapter;

import android.content.Context;

import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.base.ConcreteAdapter;
import wizrole.hosadmin.adapter.base.ViewHolder;

/**
 * Created by liushengping on 2018/3/8.
 * 何人执笔？
 */

public class DeviceTypeListAdapter extends ConcreteAdapter<String> {
    public DeviceTypeListAdapter(Context context, List<String> list, int itemLayout) {
        super(context, list, itemLayout);
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        viewHolder.setText(item, R.id.pop_list_item_type);
    }
}
