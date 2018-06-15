package wizrole.hosadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.maintenance.model.generalhoilday.GeneralDay;
import wizrole.hosadmin.maintenance.model.statutoryhoilday.StatutoryDay;

/**
 * Created by liushengping on 2018/3/9.
 * 何人执笔？
 * 设备属性适配器
 */

public class GeneralDayAdapter extends BaseAdapter {

    public Context context;
    public List<GeneralDay> stringList;
    public LayoutInflater inflater;
    public boolean showCheck = false;

    public GeneralDayAdapter(Context context, List<GeneralDay> strings) {
        this.stringList = strings;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setShowCheck(boolean showCheck) {
        this.showCheck = showCheck;
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class Holder {
        TextView textView;
        CheckBox img_right_del;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.grid_propert_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.grid_item_name);
            holder.img_right_del = (CheckBox) convertView.findViewById(R.id.img_right_del);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final GeneralDay s = stringList.get(position);
        holder.textView.setText(s.getName());
        if (showCheck) {//显示checkBox
            holder.img_right_del.setVisibility(View.VISIBLE);
        } else {
            holder.img_right_del.setVisibility(View.INVISIBLE);
        }
        holder.img_right_del.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    s.setShow(true);
                } else {
                    s.setShow(false);
                }
            }
        });
        holder.img_right_del.setChecked(s.isShow());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    itemClick.generItemClick(position);
                }
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClick != null) {
                    longClick.generlongClick(position);
                }
                return false;
            }
        });
        return convertView;
    }


    public GenerLongClick longClick;

    public interface GenerLongClick {
        void generlongClick(int postion);
    }

    public GenerLongClick getLongClick() {
        return longClick;
    }

    public void setLongClick(GenerLongClick longClick) {
        this.longClick = longClick;
    }

    public GenerItemClick itemClick;

    public interface GenerItemClick {
        void generItemClick(int poisition);
    }

    public GenerItemClick getItemClick() {
        return itemClick;
    }

    public void setItemClick(GenerItemClick itemClick) {
        this.itemClick = itemClick;
    }
}