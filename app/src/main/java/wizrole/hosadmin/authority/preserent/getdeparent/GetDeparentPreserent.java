package wizrole.hosadmin.authority.preserent.getdeparent;

import wizrole.hosadmin.authority.model.getdeparent.DeparentBack;
import wizrole.hosadmin.authority.model.getdeparent.GetDeparentHttp;
import wizrole.hosadmin.authority.preserent.getidentity.GetIdentityInterface;
import wizrole.hosadmin.base.DataBack;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 */

public class GetDeparentPreserent implements DataBack{

    public GetDeparentHttp getDeparentHttp;
    public GetDeparentInterface getDeparentInterface;
    public GetDeparentPreserent(GetDeparentInterface getDeparentInterface){
        this.getDeparentInterface=getDeparentInterface;
        getDeparentHttp=new GetDeparentHttp(this);
    }
    public void getDeparent(){
        getDeparentHttp.getDeparent();
    }

    @Override
    public void getDataBackSucc(Object o) {
        getDeparentInterface.getDeparentSucc((DeparentBack)o);
    }

    @Override
    public void getDataFail(String msg) {
        getDeparentInterface.getDeparentFail(msg);
    }
}
