package com.bcm.account.bmobbean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Bean on 2017/5/4.
 */

public class ATrans extends BmobObject {
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTrans_wallet() {
        return trans_wallet;
    }

    public void setTrans_wallet(String trans_wallet) {
        this.trans_wallet = trans_wallet;
    }

    public String getTrans_way() {
        return trans_way;
    }

    public void setTrans_way(String trans_way) {
        this.trans_way = trans_way;
    }

    public String getTrans_month() {
        return trans_month;
    }

    public void setTrans_month(String trans_month) {
        this.trans_month = trans_month;
    }

    public String getTrans_remark() {
        return trans_remark;
    }

    public void setTrans_remark(String trans_remark) {
        this.trans_remark = trans_remark;
    }

    public String getTrans_name() {
        return trans_name;
    }

    public void setTrans_name(String trans_name) {
        this.trans_name = trans_name;
    }

    public String getTrans_logo() {
        return trans_logo;
    }

    public void setTrans_logo(String trans_logo) {
        this.trans_logo = trans_logo;
    }

    public String user_id;
    public String trans_wallet;
    public String trans_way;
    public String trans_month;
    public String trans_remark;
    public String trans_name;
    public String trans_logo;

    public String getTrans_money() {
        return trans_money;
    }

    public void setTrans_money(String trans_money) {
        this.trans_money = trans_money;
    }

    public String trans_money;
}
