package wizrole.hosadmin.authority.preserent.getrolelist;

import android.content.Context;

import wizrole.hosadmin.authority.model.getrolelist.GetRoleListHttp;
import wizrole.hosadmin.authority.model.getrolelist.RoleListBack;
import wizrole.hosadmin.base.DataBack;

/**
 * Created by 何人执笔？ on 2018/4/20.
 * liushengping
 */

public class GetRoleListPreserent implements DataBack{

    public GetRoleListInterface getRoleListInterface;
    public GetRoleListHttp getRoleListHttp;
    public Context context;
    public GetRoleListPreserent(GetRoleListInterface getRoleListInterface, Context context){
        this.getRoleListInterface=getRoleListInterface;
        this.context=context;
        getRoleListHttp=new GetRoleListHttp(this,context);
    }

    public void  getRoleList(){
        getRoleListHttp.GetRoelList();
    }
    @Override
    public void getDataBackSucc(Object o) {
        getRoleListInterface.getRoleListSucc((RoleListBack)o);
    }

    @Override
    public void getDataFail(String msg) {
        getRoleListInterface.getRoleListFail(msg);
    }
}
