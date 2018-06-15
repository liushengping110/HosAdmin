package wizrole.hosadmin.maintenance.model.monitior;

/**
 * Created by liushengping on 2018/3/14.
 * 何人执笔？
 */

public class MonitiorDetail {
    public String partName;// 	组件名称---凭条打印机
    public String partStatusCode;//	组件状态码
    public String partStatusContent;//	组件状态说明
    public String partRemark;  // 备注

    public String getPartRemark() {
        return partRemark;
    }

    public void setPartRemark(String partRemark) {
        this.partRemark = partRemark;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public void setPartStatusCode(String partStatusCode) {
        this.partStatusCode = partStatusCode;
    }

    public void setPartStatusContent(String partStatusContent) {
        this.partStatusContent = partStatusContent;
    }

    public String getPartName() {
        return partName;
    }

    public String getPartStatusCode() {
        return partStatusCode;
    }

    public String getPartStatusContent() {
        return partStatusContent;
    }
}
