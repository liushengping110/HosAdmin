package wizrole.hosadmin.authority.model.getdeparent;

import java.util.List;

/**
 * Created by 何人执笔？ on 2018/4/17.
 * liushengping
 */

public class DeparentBack {
    public String ResultCode;
    public String ResultContent;
    public List<DeparentList> deparentLists;

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public void setResultContent(String resultContent) {
        ResultContent = resultContent;
    }

    public void setDeparentLists(List<DeparentList> deparentLists) {
        this.deparentLists = deparentLists;
    }

    public String getResultCode() {

        return ResultCode;
    }

    public String getResultContent() {
        return ResultContent;
    }

    public List<DeparentList> getDeparentLists() {
        return deparentLists;
    }
}
