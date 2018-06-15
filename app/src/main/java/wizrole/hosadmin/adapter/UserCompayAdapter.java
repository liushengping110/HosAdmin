package wizrole.hosadmin.adapter;

import android.content.Context;

import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.base.ConcreteAdapter;
import wizrole.hosadmin.adapter.base.ViewHolder;
import wizrole.hosadmin.authority.model.getcompay.CompayList;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 * 用户管理---pop--科室列表
 */

public class UserCompayAdapter extends ConcreteAdapter<CompayList>{

    public UserCompayAdapter(Context context, List<CompayList> list, int itemLayout) {
        super(context, list, itemLayout);
    }

    @Override
    protected void convert(ViewHolder viewHolder, CompayList item, int position) {
        viewHolder.setText(item.getCompayName(), R.id.pop_list_item_type);
    }
}
