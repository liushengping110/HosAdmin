package wizrole.hosadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.authority.model.getrolelist.ChildPermissionList;
import wizrole.hosadmin.authority.model.getrolelist.Datas;


public class IDPermissParentAdapter extends BaseExpandableListAdapter {

	private Context mContext;// 上下文

	private List<Datas> mParents;// 数据源

	private OnChildTreeViewClickListener mTreeViewClickListener;// 点击子ExpandableListView子项的监听
	public ExpandableListView expandableListView;
	public IDPermissParentAdapter(Context context, List<Datas> parents, ExpandableListView expandableListView) {
		this.mContext = context;
		this.mParents = parents;
		this.expandableListView=expandableListView;
	}

	@Override
	public ChildPermissionList getChild(int groupPosition, int childPosition) {
		return mParents.get(groupPosition).getChildPermissionList().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mParents.get(groupPosition).getChildPermissionList() != null ? mParents
				.get(groupPosition).getChildPermissionList().size() : 0;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
							 boolean isExpanded, View convertView, ViewGroup parent) {
		final ExpandableListView eListView = getExpandableListView();
		List<ChildPermissionList> childs = new ArrayList<ChildPermissionList>();
		final ChildPermissionList child = getChild(groupPosition, childPosition);
		childs.add(child);
		final IDPermissChildAdapter childAdapter = new IDPermissChildAdapter(mContext,childs,eListView);
		eListView.setAdapter(childAdapter);

		/**
		 * 点击子ExpandableListView子项时，调用回调接口
		 * */
		eListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
										int groupIndex, int childIndex, long arg4) {
				if (mTreeViewClickListener != null) {
					mTreeViewClickListener.onClickPosition(groupPosition,
							childPosition, childIndex,childAdapter);
				}
				return false;
			}
		});


		/**
		 * 子ExpandableListView展开时，因为group只有一项，所以子ExpandableListView的总高度=
		 *（子ExpandableListView的child数量 + 1 ）* 每一项的高度
		 * */
		eListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				LayoutParams lp = new LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, (child
						.getGrandSonPermissionLists().size() + 1)
						* (int) mContext.getResources().getDimension(
						R.dimen.parent_expandable_center));
				eListView.setLayoutParams(lp);
			}
		});

		/**
		 *         子ExpandableListView关闭时，此时只剩下group这一项，
		 *         所以子ExpandableListView的总高度即为一项的高度
		 * */
		eListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {
				LayoutParams lp = new LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext
						.getResources().getDimension(
								R.dimen.parent_expandable_center));
				eListView.setLayoutParams(lp);
			}
		});
		return eListView;

	}

	/**
	 * 动态创建子ExpandableListView
	 * */
	public ExpandableListView getExpandableListView() {
		ExpandableListView mExpandableListView = new ExpandableListView(
				mContext);
		LayoutParams lp = new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext
				.getResources().getDimension(
						R.dimen.parent_expandable_center));
		mExpandableListView.setLayoutParams(lp);
		mExpandableListView.setDividerHeight(0);// 取消group项的分割线
		mExpandableListView.setChildDivider(null);// 取消child项的分割线
		mExpandableListView.setGroupIndicator(null);// 取消展开折叠的指示图标
		return mExpandableListView;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mParents.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return mParents != null ? mParents.size() : 0;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		final GroupHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_permiss_parent_item, null);
			holder = new GroupHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (GroupHolder) convertView.getTag();
		}
		holder.update(mParents.get(groupPosition));
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
	 *  Holder优化
	 * */
	class GroupHolder {
		private TextView parentGroupTV;
		public ImageView checkBox_parent;
		public ImageView img_parent;
		public GroupHolder(View v) {
			parentGroupTV = (TextView) v.findViewById(R.id.parentGroupTV);
			checkBox_parent = (ImageView) v.findViewById(R.id.check_parent);
			img_parent = (ImageView) v.findViewById(R.id.img_parent);
		}
		public void update(Datas model) {
			parentGroupTV.setText(model.getPermissionDec());
		}
		public void kai(){
			checkBox_parent.setVisibility(View.VISIBLE);
			img_parent.setVisibility(View.GONE);
		}
		public void guan(){
			checkBox_parent.setVisibility(View.GONE);
			img_parent.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	/**
	 * 设置点击子ExpandableListView子项的监听
	 * */
	public void setOnChildTreeViewClickListener(OnChildTreeViewClickListener treeViewClickListener) {
		this.mTreeViewClickListener = treeViewClickListener;
	}

	/**
	 *点击子ExpandableListView子项的回调接口
	 * */
	public interface OnChildTreeViewClickListener {
		void onClickPosition(int parentPosition, int groupPosition, int childPosition,IDPermissChildAdapter childAdapter);
	}
}
