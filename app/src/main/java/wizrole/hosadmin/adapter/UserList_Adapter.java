package wizrole.hosadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import wizrole.hosadmin.R;
import wizrole.hosadmin.adapter.base.ConcreteAdapter;
import wizrole.hosadmin.adapter.base.ViewHolder;
import wizrole.hosadmin.authority.model.getuser.UserList;
import wizrole.hosadmin.util.image.ImageLoading;

/**
 * Created by liushengping on 2018/3/5.
 * 何人执笔？
 */

public class UserList_Adapter extends RecyclerView.Adapter<UserList_Adapter.ViewHolder>{
    public List<UserList> userLists;
    public Context context;
    public UserList_Adapter(Context context,List<UserList> userLists){
        this.userLists=userLists;
        this.context=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text_item_userName.setText(userLists.get(position).getUserName());
        holder.text_item_comName.setText(userLists.get(position).getUserCompanyName());
        holder.text_item_deparName.setText(userLists.get(position).getUserDeparentName());
        holder.text_item_identity.setText(userLists.get(position).getUserIdentity());
        holder.text_item_loginTime.setText(userLists.get(position).getUserLashLoginTime());
        if(userLists.get(position).getUserIdentity().equals("管理员")){
            ImageLoading.commonRound(context,R.drawable.admin,holder.img_user_logo);
        }else{
            ImageLoading.commonRound(context,R.drawable.user,holder.img_user_logo);
        }
    }

    @Override
    public int getItemCount() {
        return userLists == null ? 0 : userLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_user_logo;
        TextView text_item_userName;
        TextView text_item_deparName;
        TextView text_item_comName;
        TextView text_item_identity;
        TextView text_item_loginTime;
        public ViewHolder(View itemView) {
            super(itemView);
            img_user_logo = (ImageView) itemView.findViewById(R.id.img_user_logo);
            text_item_userName = (TextView) itemView.findViewById(R.id.text_item_userName);
            text_item_deparName = (TextView) itemView.findViewById(R.id.text_item_deparName);
            text_item_comName = (TextView) itemView.findViewById(R.id.text_item_comName);
            text_item_identity = (TextView) itemView.findViewById(R.id.text_item_identity);
            text_item_loginTime = (TextView) itemView.findViewById(R.id.text_item_loginTime);
        }
    }
}
