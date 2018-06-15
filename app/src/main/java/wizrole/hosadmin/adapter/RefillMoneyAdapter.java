package wizrole.hosadmin.adapter;

import android.content.Context;

import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.base.ConcreteAdapter;
import wizrole.hosadmin.adapter.base.ViewHolder;
import wizrole.hosadmin.dailyrecord.model.refillmoney.RefillMoneyList;

/**
 * Created by liushengping on 2018/3/15.
 * 何人执笔？
 */

public class RefillMoneyAdapter extends ConcreteAdapter<RefillMoneyList> {
    public RefillMoneyAdapter(Context context, List<RefillMoneyList> list, int itemLayout) {
        super(context, list, itemLayout);
    }

    @Override
    protected void convert(ViewHolder viewHolder, RefillMoneyList item, int position) {
        viewHolder.setText(item.getRefillMoneyname(), R.id.text_rm_name)
                .setText(item.getRefillMoneyNumber(),R.id.text_rm_num)
                .setText(item.getRefillMoneyID(),R.id.text_rm_id);
    }
}
