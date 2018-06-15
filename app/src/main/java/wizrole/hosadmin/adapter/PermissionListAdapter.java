package wizrole.hosadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.authority.model.getrolelist.ChildPermissionList;
import wizrole.hosadmin.authority.model.getrolelist.GrandSonPermissionList;
import wizrole.hosadmin.maintenance.model.grouplist.ChildBean;
import wizrole.hosadmin.maintenance.model.grouplist.GroupBean;

/**
 * Created by liushengping on 2018/3/9.
 * 何人执笔？
 * 权限列表
 */

public class PermissionListAdapter extends BaseExpandableListAdapter {

    private List<ChildPermissionList> list;
    private Context context;
    public ExpandableListView expandableListView;
    public String parent_name;//二级分类的上一级父类节点名称
    public PermissionListAdapter(String parent_name,List<ChildPermissionList> list, Context context, ExpandableListView expandableListView) {
        this.parent_name=parent_name;
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
        return list.get(groupPosition).getGrandSonPermissionLists().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getGrandSonPermissionLists().get(childPosition);
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
            convertView = LayoutInflater.from(context).inflate( R.layout.expand_permiss_item, null);
            holder.group_title = (TextView) convertView .findViewById(R.id.group_title);
            holder.group_name = (TextView) convertView .findViewById(R.id.group_name);
            holder.group_url = (TextView) convertView .findViewById(R.id.group_url);
            holder.group_parent_name = (TextView) convertView .findViewById(R.id.group_parent_name);
            holder.iv = (ImageView) convertView.findViewById(R.id.group_ico);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.group_parent_name.setText(parent_name);//父类节点
        holder.group_title.setText(list.get(groupPosition).getPermissionDec());//权限名称
        holder.group_name.setText(list.get(groupPosition).getPermissionName());//权限名
        holder.group_url.setText(list.get(groupPosition).getPermissionUrl());//权限URL
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
                    R.layout.expand_permiss_child_item, null);
            holder.child_name = (TextView) convertView.findViewById(R.id.child_name);
            holder.child_title = (TextView) convertView.findViewById(R.id.child_title);
            holder.child_url = (TextView) convertView.findViewById(R.id.child_url);
            holder.child_parent_name = (TextView) convertView.findViewById(R.id.child_parent_name);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        GrandSonPermissionList cb = list.get(groupPosition).getGrandSonPermissionLists().get(childPosition);
        holder.child_parent_name.setText(list.get(groupPosition).getPermissionDec());
        holder.child_title.setText(cb.getPermissionDec());
        holder.child_url.setText(cb.getPermissionUrl());
        holder.child_name.setText(cb.getPermissionName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(childOnClick!=null){
                    childOnClick.childOnClick(groupPosition,childPosition);
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }




    class GroupHolder {
        TextView group_title;//名称
        TextView group_url;//url
        TextView group_name;//名
        TextView group_parent_name;//父节点名称
        ImageView iv;
    }

    class ChildHolder {//子列表的四项
        TextView child_name;
        TextView child_title;
        TextView child_url;
        TextView child_parent_name;
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
        void childOnClick(int groupPosition, int childPosition);
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
        void childLongClick(int groupPosition, int childPosition);
    }

    public ChildLongClick getChildLongClick() {
        return childLongClick;
    }

    public void setChildLongClick(ChildLongClick childLongClick) {
        this.childLongClick = childLongClick;
    }
}
