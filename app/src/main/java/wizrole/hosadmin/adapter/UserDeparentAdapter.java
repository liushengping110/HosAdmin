package wizrole.hosadmin.adapter;

import android.content.Context;

import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.base.ConcreteAdapter;
import wizrole.hosadmin.adapter.base.ViewHolder;
import wizrole.hosadmin.authority.model.getdeparent.DeparentList;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 */

public class UserDeparentAdapter extends ConcreteAdapter<DeparentList> {
    public UserDeparentAdapter(Context context, List<DeparentList> list, int itemLayout) {
        super(context, list, itemLayout);
    }

    @Override
    protected void convert(ViewHolder viewHolder, DeparentList item, int position) {
        viewHolder.setText(item.getDeparentName(), R.id.pop_list_item_right);
    }
}
