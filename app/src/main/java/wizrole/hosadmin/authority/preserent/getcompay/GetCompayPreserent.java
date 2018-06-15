package wizrole.hosadmin.authority.preserent.getcompay;

import wizrole.hosadmin.authority.model.getcompay.CompayBack;
import wizrole.hosadmin.authority.model.getcompay.GetCompayHttp;
import wizrole.hosadmin.base.DataBack;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 */

public class GetCompayPreserent implements DataBack{
    public GetCompayHttp getCompayHttp;
    public GetCompayInterface getCompayInterface;
    public GetCompayPreserent(GetCompayInterface getCompayInterface){
        this.getCompayHttp=new GetCompayHttp(this);
        this.getCompayInterface=getCompayInterface;
    }

    public void getCompay(){
        getCompayHttp.getCompay();
    }
    @Override
    public void getDataBackSucc(Object o) {
        getCompayInterface.getCompaySucc((CompayBack)o);
    }

    @Override
    public void getDataFail(String msg) {
        getCompayInterface.getCompayFail(msg);
    }
}
