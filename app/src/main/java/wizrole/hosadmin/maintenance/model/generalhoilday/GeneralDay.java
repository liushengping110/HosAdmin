package wizrole.hosadmin.maintenance.model.generalhoilday;

/**
 * Created by liushengping on 2018/3/12.
 * 何人执笔？
 */

public class GeneralDay {

    public String name;
    public boolean show;

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
