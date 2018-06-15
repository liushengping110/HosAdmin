package wizrole.hosadmin.maintenance.model.devicelist;

import java.io.Serializable;

/**
 * Created by liushengping on 2018/3/7.
 * 何人执笔？
 */

public class DeviceList implements Serializable{
    public String DeviceStatus;
    public String DeviceImage;
    public String DeviceType;
    public String DeviceGroup;
    public String DeviceIP;
    public String DeviceMac;
    public String DeviceNo;

    public String getDeviceStatus() {
        return DeviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        DeviceStatus = deviceStatus;
    }

    public String getDeviceImage() {
        return DeviceImage;
    }

    public void setDeviceImage(String deviceImage) {
        DeviceImage = deviceImage;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    public void setDeviceGroup(String deviceGroup) {
        DeviceGroup = deviceGroup;
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

    public String getDeviceType() {
        return DeviceType;
    }

    public String getDeviceGroup() {
        return DeviceGroup;
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
}
