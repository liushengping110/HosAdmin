package wizrole.hosadmin.dailyrecord.model.refillmoney;

/**
 * Created by liushengping on 2018/3/15.
 * 何人执笔？
 */

public class RefillMoneyList {

    public String RefillMoneyname;
    public String RefillMoneyNumber;
    public String RefillMoneyID;

    public String getRefillMoneyID() {
        return RefillMoneyID;
    }

    public void setRefillMoneyID(String refillMoneyID) {
        RefillMoneyID = refillMoneyID;
    }

    public void setRefillMoneyname(String refillMoneyname) {
        RefillMoneyname = refillMoneyname;
    }

    public void setRefillMoneyNumber(String refillMoneyNumber) {
        RefillMoneyNumber = refillMoneyNumber;
    }

    public String getRefillMoneyname() {

        return RefillMoneyname;
    }

    public String getRefillMoneyNumber() {
        return RefillMoneyNumber;
    }
}
