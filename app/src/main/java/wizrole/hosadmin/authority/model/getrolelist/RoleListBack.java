package wizrole.hosadmin.authority.model.getrolelist;

import java.util.List;

/**
 * Created by 何人执笔？ on 2018/4/20.
 * liushengping
 *权限
 */

public class RoleListBack {

    public String ResultCode;
    public String ResultContent;
    public List<Datas> datas;

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public void setResultContent(String resultContent) {
        ResultContent = resultContent;
    }

    public void setDatas(List<Datas> datas) {
        this.datas = datas;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public String getResultContent() {
        return ResultContent;
    }

    public List<Datas> getDatas() {
        return datas;
    }
}
