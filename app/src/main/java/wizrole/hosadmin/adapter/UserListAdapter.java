//package wizrole.hosadmin.adapter;
//
//import android.content.Context;
//
//import java.util.List;
//
//import wizrole.hosadmin.R;
//import wizrole.hosadmin.adapter.base.ConcreteAdapter;
//import wizrole.hosadmin.adapter.base.ViewHolder;
//import wizrole.hosadmin.authority.model.getuser.UserList;
//import wizrole.hosadmin.util.image.ImageLoading;
//
///**
// * Created by liushengping on 2018/3/5.
// * 何人执笔？
// */
//
//public class UserListAdapter extends ConcreteAdapter<UserList> {
//    public Context context;
//    public UserListAdapter(Context context, List<UserList> list, int itemLayout) {
//        super(context, list, itemLayout);
//        this.context=context;
//    }
//
//    @Override
//    protected void convert(ViewHolder viewHolder, UserList item, int position) {
//        viewHolder.setText(item.getUserName(), R.id.text_item_userName)
//        .setText(item.getUserDeparentName(),R.id.text_item_deparName)
//        .setText(item.getUserCompanyName(),R.id.text_item_comName)
//        .setText(item.getUserIdentity(),R.id.text_item_identity)
//        .setText(item.getUserLashLoginTime(),R.id.text_item_loginTime);
//        if(item.getUserIdentity().equals("管理员")){
//            viewHolder.setCommImageView(R.drawable.admin,R.id.img_user_logo,R.drawable.admin);
//        }else{
//            viewHolder.setCommImageView(R.drawable.user,R.id.img_user_logo,R.drawable.user);
//        }
//    }
//}
