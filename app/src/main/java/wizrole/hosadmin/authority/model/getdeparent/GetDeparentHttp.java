package wizrole.hosadmin.authority.model.getdeparent;

import java.util.ArrayList;
import java.util.List;

import wizrole.hosadmin.base.DataBack;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 */

public class GetDeparentHttp {
    public DataBack dataBack;
    public GetDeparentHttp(DataBack dataBack){
        this.dataBack=dataBack;
    }
    public void getDeparent(){
        List<DeparentList> list=new ArrayList<>();
        DeparentBack back=new DeparentBack();
        back.setResultCode("0");
        back.setResultContent("succ");
        for(int i=0;i<50;i++){
            DeparentList deparentList=new DeparentList();
            deparentList.setDeparentName("妇产科门诊"+i);
            list.add(deparentList);
            deparentList=null;
        }
        back.setDeparentLists(list);
        dataBack.getDataBackSucc(back);
    }
}
