package wizrole.hosadmin.authority.model.getcompay;

import java.util.ArrayList;
import java.util.List;

import wizrole.hosadmin.authority.preserent.getcompay.GetCompayInterface;
import wizrole.hosadmin.base.DataBack;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 */

public class GetCompayHttp {

    public DataBack dataBack;
    public GetCompayHttp( DataBack dataBack){
        this.dataBack=dataBack;
    }
    public void getCompay(){
        List<CompayList> list=new ArrayList<>();
        CompayBack back=new CompayBack();
        back.setResultCode("0");
        back.setResultContent("succ");
        for (int i=0;i<20;i++){
            CompayList compayList=new CompayList();
            compayList.setCompayName("北京维思陆科技有限公司"+i);
            compayList.setCompayAddress("朝阳区红军营南路"+i);
            list.add(compayList);
            compayList=null;
        }
        back.setCompayLists(list);
        dataBack.getDataBackSucc(back);
    }
}
