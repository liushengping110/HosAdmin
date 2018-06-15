package wizrole.hosadmin.maintenance.model.devicelist;

import java.util.List;

/**
 * Created by liushengping on 2018/3/7.
 * 何人执笔？
 */

public class DeviceListBack {

    public String ResultCode;
    public String ResultContent;
    public List<DeviceList> deviceLists;

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public void setResultContent(String resultContent) {
        ResultContent = resultContent;
    }

    public void setDeviceLists(List<DeviceList> deviceLists) {
        this.deviceLists = deviceLists;
    }

    public String getResultCode() {

        return ResultCode;
    }

    public String getResultContent() {
        return ResultContent;
    }

    public List<DeviceList> getDeviceLists() {
        return deviceLists;
    }
}
