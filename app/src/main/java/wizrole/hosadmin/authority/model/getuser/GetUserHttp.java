package wizrole.hosadmin.authority.model.getuser;



import java.util.ArrayList;
import java.util.List;
import wizrole.hosadmin.base.DataBack;

/**
 * Created by liushengping on 2018/3/6.
 * 何人执笔？
 */

public class GetUserHttp {

    public DataBack dataBack;
    public GetUserHttp(DataBack dataBack){
        this.dataBack=dataBack;
    }
    public void getUserHttp(){
        List<UserList> list=new ArrayList<>();
        GetUserBack back=new GetUserBack();
        back.setResultCode("0");
        back.setResultContent("succ");
        for (int i=0;i<20;i++){
            UserList userList=new UserList();
            userList.setUserName("lsp"+i);
            userList.setUserCompanyName("北京维思陆科技有限公司"+i);
            userList.setUserDeparentName("妇产科门诊"+i);
            if(i%2==0){
                userList.setUserIdentity("管理员");
            }else{
                userList.setUserIdentity("用户");
            }
            userList.setUserLashLoginTime("2018-04-17 11:00");
            list.add(userList);
            userList=null;
        }
        back.setUserList(list);
        dataBack.getDataBackSucc(back);
    }
}
