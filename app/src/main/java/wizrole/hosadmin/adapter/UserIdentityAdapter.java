package wizrole.hosadmin.adapter;

import android.content.Context;

import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.base.ConcreteAdapter;
import wizrole.hosadmin.adapter.base.ViewHolder;
import wizrole.hosadmin.authority.model.getidentity.IdentityList;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 */

public class UserIdentityAdapter extends ConcreteAdapter<IdentityList> {

    public UserIdentityAdapter(Context context, List<IdentityList> list, int itemLayout) {
        super(context, list, itemLayout);
    }

    @Override
    protected void convert(ViewHolder viewHolder, IdentityList item, int position) {
        viewHolder.setText(item.getIdentityName(), R.id.pop_list_item_type);
    }
}
