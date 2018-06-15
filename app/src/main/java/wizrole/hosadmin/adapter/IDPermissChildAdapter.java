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

public class IDPermissChildAdapter extends BaseExpandableListAdapter {

	private Context mContext;// 上下文
	private List<ChildPermissionList> mChilds;// 数据源
	public ExpandableListView expandableListView;
	public IDPermissChildAdapter(Context context, List<ChildPermissionList> childs , ExpandableListView expandableListView) {
		this.mContext = context;
		this.expandableListView=expandableListView;
		this.mChilds = childs;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mChilds.get(groupPosition).getGrandSonPermissionLists() != null ? mChilds
				.get(groupPosition).getGrandSonPermissionLists().size() : 0;
	}

	@Override
	public String getChild(int groupPosition, int childPosition) {
		if (mChilds.get(groupPosition).getGrandSonPermissionLists() != null
				&& mChilds.get(groupPosition).getGrandSonPermissionLists().size() > 0)
			return mChilds.get(groupPosition).getGrandSonPermissionLists()
					.get(childPosition).getPermissionDec().toString();
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition,final int childPosition,
							 boolean isExpanded, View convertView, ViewGroup parent) {
		final ChildHolder holder ;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.list_permiss_grandson_item, null);
			holder = new ChildHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ChildHolder) convertView.getTag();
		}
		holder.update(getChild(groupPosition, childPosition));
		GrandSonPermissionList permissionList=mChilds.get(groupPosition).getGrandSonPermissionLists().get(childPosition);
		if(permissionList.getCheckStates().equals("")){
			holder.check(false);
		}else{
			holder.check(true);
		}
		return convertView;
	}
	/**
	 * Holder优化
	 */
	class ChildHolder {

		private TextView childChildTV;
		private ImageView img_no_sel;
		private ImageView img_sel;

		public ChildHolder(View v) {
			childChildTV = (TextView) v.findViewById(R.id.childChildTV);
			img_no_sel = (ImageView) v.findViewById(R.id.img_no_sel);
			img_sel = (ImageView) v.findViewById(R.id.img_sel);
		}

		public void update(String str) {
			childChildTV.setText(str);
		}
		public void check(boolean status){
			if(status){
				img_sel.setVisibility(View.VISIBLE);
				img_no_sel.setVisibility(View.GONE);
			}else{
				img_no_sel.setVisibility(View.VISIBLE);
				img_sel.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public Object getGroup(int groupPosition) {
		if (mChilds != null && mChilds.size() > 0)
			return mChilds.get(groupPosition);
		return null;
	}

	@Override
	public int getGroupCount() {
		return mChilds != null ? mChilds.size() : 0;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,
							 View convertView, ViewGroup parent) {
		final GroupHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.list_permiss_child_item, null);
			holder = new GroupHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (GroupHolder) convertView.getTag();
		}
		holder.update(mChilds.get(groupPosition));
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!expandableListView.collapseGroup(groupPosition)){
					expandableListView.expandGroup(groupPosition);
					holder.kai();
				}else{
					expandableListView.collapseGroup(groupPosition);
					holder.guan();
				}
			}
		});
		return convertView;
	}

	/**
	 *         Holder优化
	 * */
	class GroupHolder {

		private TextView childGroupTV;
		private ImageView check_child;
		private ImageView img_child;

		public GroupHolder(View v) {
			childGroupTV = (TextView) v.findViewById(R.id.childGroupTV);
			img_child = (ImageView) v.findViewById(R.id.img_child);
			check_child = (ImageView) v.findViewById(R.id.check_child);
		}

		public void update(ChildPermissionList model) {
			childGroupTV.setText(model.getPermissionDec());
		}
		public void kai(){
			check_child.setVisibility(View.VISIBLE);
			img_child.setVisibility(View.GONE);
		}
		public void guan(){
			check_child.setVisibility(View.GONE);
			img_child.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		/**
		 * 此处必须返回true，否则无法响应子项的点击事件
		 **/
		return true;
	}

}
