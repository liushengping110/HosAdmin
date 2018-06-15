package wizrole.hosadmin.authority.preserent.getidentity;

import wizrole.hosadmin.authority.model.getidentity.GetIdentityHttp;
import wizrole.hosadmin.authority.model.getidentity.IdentityBack;
import wizrole.hosadmin.authority.preserent.getcompay.GetCompayInterface;
import wizrole.hosadmin.base.DataBack;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 */

public class GetIdentityPreserent implements DataBack{

    public GetIdentityInterface getIdentityInterface;
    public GetIdentityHttp getIdentityHttp;
    public GetIdentityPreserent(GetIdentityInterface getIdentityInterface){
        this.getIdentityInterface=getIdentityInterface;
        getIdentityHttp=new GetIdentityHttp(this);
    }

    public void setGetIdentity(){
        getIdentityHttp.getIdentity();
    }
    @Override
    public void getDataBackSucc(Object o) {
        getIdentityInterface.getIdentitySucc((IdentityBack)o);
    }

    @Override
    public void getDataFail(String msg) {
        getIdentityInterface.getIdentityFail(msg);
    }
}
