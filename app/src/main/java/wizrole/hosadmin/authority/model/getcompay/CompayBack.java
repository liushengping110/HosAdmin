package wizrole.hosadmin.authority.model.getcompay;

import java.util.List;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 * 公司列表
 */

public class CompayBack {

    public String ResultCode;
    public String ResultContent;
    public List<CompayList> compayLists;

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public void setResultContent(String resultContent) {
        ResultContent = resultContent;
    }

    public void setCompayLists(List<CompayList> compayLists) {
        this.compayLists = compayLists;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public String getResultContent() {
        return ResultContent;
    }

    public List<CompayList> getCompayLists() {
        return compayLists;
    }
}
