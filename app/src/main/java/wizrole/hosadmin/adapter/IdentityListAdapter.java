package wizrole.hosadmin.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.authority.model.getidentity.IdentityList;

/**
 * Created by 何人执笔？ on 2018/4/20.
 * liushengping
 * 角色列表适配器
 */

public class IdentityListAdapter  extends RecyclerView.Adapter<IdentityListAdapter.ViewHolder>{
    public List<IdentityList> identityLists;
    public IdentityListAdapter(List<IdentityList> identityLists){
        this.identityLists=identityLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rolelist,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(identityLists.get(position).getIdentityName());
        holder.no.setText(identityLists.get(position).getIdentityNo());
    }

    @Override
    public int getItemCount() {
        return identityLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView no;
        public ViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.text_role_name);
            no=(TextView)itemView.findViewById(R.id.text_role_no);
        }
    }
}
