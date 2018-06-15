package wizrole.hosadmin.authority.model.getidentity;

import java.util.ArrayList;
import java.util.List;

import wizrole.hosadmin.base.DataBack;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 */

public class GetIdentityHttp {

    public DataBack dataBack;

    public GetIdentityHttp(DataBack dataBack){
        this.dataBack=dataBack;
    }
    public void getIdentity(){
        List<IdentityList> list=new ArrayList<>();
        IdentityBack back=new IdentityBack();
        back.setResultContent("succ");
        back.setResultCode("0");
        for(int i=0;i<2;i++){
            IdentityList identityList=new IdentityList();
            if(i==0){
                identityList.setIdentityNo("user"+i);
                identityList.setIdentityName("管理员");
            }else{
                identityList.setIdentityNo("administrator"+i);
                identityList.setIdentityName("用户");
            }
            list.add(identityList);
            identityList=null;
        }
        back.setIdentityLists(list);
        dataBack.getDataBackSucc(back);
    }
}
