package wizrole.hosadmin.maintenance.model.statutoryhoilday;

/**
 * Created by liushengping on 2018/3/12.
 * 何人执笔？
 */

public class StatutoryDay {

    public String name;
    public boolean show;

    public String getName() {
        return name;
    }

    public boolean isShow() {
        return show;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
