package wizrole.hosadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.maintenance.model.grouplist.ChildBean;
import wizrole.hosadmin.maintenance.model.grouplist.GroupBean;

/**
 * Created by liushengping on 2018/3/9.
 * 何人执笔？
 * 设备群组适配器
 */

public class GroupListAdapter extends BaseExpandableListAdapter {

    private List<GroupBean> list;
    private Context context;
    public ExpandableListView expandableListView;

    public GroupListAdapter(List<GroupBean> list, Context context,ExpandableListView expandableListView) {
        this.list = list;
        this.context = context;
        this.expandableListView=expandableListView;
    }


    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getChildren().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupHolder holder;
        if (convertView == null) {
            holder = new GroupHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.expand_group_item, null);
            holder.title = (TextView) convertView
                    .findViewById(R.id.group_title);
            holder.iv = (ImageView) convertView.findViewById(R.id.group_ico);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.title.setText(list.get(groupPosition).getGroupName());
        if (isExpanded) {
            holder.iv.setImageResource(R.drawable.rounds_open);
        } else {
            holder.iv.setImageResource(R.drawable.rounds_close);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(groupOnClick!=null){
                    groupOnClick.groupOnClick(groupPosition);
                }
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(groupLongOnClick!=null){
                    groupLongOnClick.groupLongClick(groupPosition);
                }
                return true;
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        if (convertView == null) {
            holder = new ChildHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.expand_child_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.child_name);
            holder.sign = (TextView) convertView.findViewById(R.id.child_sign);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        ChildBean cb = list.get(groupPosition).getChildren().get(childPosition);
        holder.name.setText(cb.getName());
        holder.sign.setText("[签名]" + cb.getSign());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(childOnClick!=null){
                    childOnClick.childOnClick(groupPosition,childPosition);
                }
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(childLongClick!=null){
                    childLongClick.childLongClick(groupPosition,childPosition);
                }
                return true;
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }




    class GroupHolder {
        TextView title;
        ImageView iv;
    }

    class ChildHolder {
        TextView name, sign;
    }

    /**
     * 父类 item监听 inteface
     */
    public GroupOnClick groupOnClick;
    public interface GroupOnClick{
        void groupOnClick(int groupPosition);
    }

    public GroupOnClick getGroupOnClick() {
        return groupOnClick;
    }

    public void setGroupOnClick(GroupOnClick groupOnClick) {
        this.groupOnClick = groupOnClick;
    }
    /**
     * 父类长按监听
     */
    public GroupLongOnClick groupLongOnClick;
    public interface GroupLongOnClick{
        void groupLongClick(int groupPosition);
    }

    public GroupLongOnClick getGroupLongOnClick() {
        return groupLongOnClick;
    }

    public void setGroupLongOnClick(GroupLongOnClick groupLongOnClick) {
        this.groupLongOnClick = groupLongOnClick;
    }

    /**
     * 子类item监听 inteface
     */
    public ChildOnClick childOnClick;
    public interface ChildOnClick{
        void childOnClick(int groupPosition,int childPosition);
    }

    public ChildOnClick getChildOnClick() {
        return childOnClick;
    }

    public void setChildOnClick(ChildOnClick childOnClick) {
        this.childOnClick = childOnClick;
    }

    /**
     * 子类 长按监听inteface
     */
    public ChildLongClick childLongClick;
    public interface ChildLongClick{
        void childLongClick(int groupPosition,int childPosition);
    }

    public ChildLongClick getChildLongClick() {
        return childLongClick;
    }

    public void setChildLongClick(ChildLongClick childLongClick) {
        this.childLongClick = childLongClick;
    }
}
