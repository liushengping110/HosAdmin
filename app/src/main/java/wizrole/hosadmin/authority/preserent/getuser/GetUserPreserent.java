package wizrole.hosadmin.authority.preserent.getuser;

import wizrole.hosadmin.base.DataBack;
import wizrole.hosadmin.authority.model.getuser.GetUserBack;
import wizrole.hosadmin.authority.model.getuser.GetUserHttp;
import wizrole.hosadmin.authority.preserent.getuser.*;

/**
 * Created by liushengping on 2018/3/6.
 * 何人执笔？
 */

public class GetUserPreserent implements DataBack {

    public GetUserHttp getUserHttp;
    public GetUserInterface getUserInterface;
    public GetUserPreserent(wizrole.hosadmin.authority.preserent.getuser.GetUserInterface getUserInterface){
        this.getUserInterface=getUserInterface;
        getUserHttp=new GetUserHttp(this);
    }
    public void getUser(){
        getUserHttp.getUserHttp();
    }

    @Override
    public void getDataBackSucc(Object o) {
        getUserInterface.getUserSucc((GetUserBack)o);
    }

    @Override
    public void getDataFail(String msg) {
        getUserInterface.getUserFail(msg);
    }
}
