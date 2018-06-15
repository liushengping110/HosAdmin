package wizrole.hosadmin.maintenance.model.monitior;

import android.widget.ListView;

import java.util.List;

/**
 * Created by liushengping on 2018/3/14.
 * 何人执笔？
 */

public class MonitiorList {

    public String DeviceImage;
    public String DeviceType;
    public String DeviceIP;
    public String DeviceMac;
    public String DeviceNo;
    public String DeviceGroup;
    public List<MonitiorDetail> monitiorDetailList;

    public String getDeviceGroup() {
        return DeviceGroup;
    }

    public void setDeviceGroup(String deviceGroup) {
        DeviceGroup = deviceGroup;
    }

    public void setDeviceImage(String deviceImage) {
        DeviceImage = deviceImage;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    public void setDeviceIP(String deviceIP) {
        DeviceIP = deviceIP;
    }

    public void setDeviceMac(String deviceMac) {
        DeviceMac = deviceMac;
    }

    public void setDeviceNo(String deviceNo) {
        DeviceNo = deviceNo;
    }

    public void setMonitiorDetailList(List<MonitiorDetail> monitiorDetailList) {
        this.monitiorDetailList = monitiorDetailList;
    }

    public String getDeviceImage() {

        return DeviceImage;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public String getDeviceIP() {
        return DeviceIP;
    }

    public String getDeviceMac() {
        return DeviceMac;
    }

    public String getDeviceNo() {
        return DeviceNo;
    }

    public List<MonitiorDetail> getMonitiorDetailList() {
        return monitiorDetailList;
    }
}
