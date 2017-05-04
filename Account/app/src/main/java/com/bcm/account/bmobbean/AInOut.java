package com.bcm.account.bmobbean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Bean on 2017/5/4.
 */

public class AInOut extends BmobObject {
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getIo_type() {
        return io_type;
    }

    public void setIo_type(String io_type) {
        this.io_type = io_type;
    }

    public String getIo_wallet() {
        return io_wallet;
    }

    public void setIo_wallet(String io_wallet) {
        this.io_wallet = io_wallet;
    }

    public String getIo_name() {
        return io_name;
    }

    public void setIo_name(String io_name) {
        this.io_name = io_name;
    }

    public String getIo_money() {
        return io_money;
    }

    public void setIo_money(String io_money) {
        this.io_money = io_money;
    }

    public String getIo_date() {
        return io_date;
    }

    public void setIo_date(String io_date) {
        this.io_date = io_date;
    }

    public String getIo_logo() {
        return io_logo;
    }

    public void setIo_logo(String io_logo) {
        this.io_logo = io_logo;
    }

    public String getIo_remark() {
        return io_remark;
    }

    public void setIo_remark(String io_remark) {
        this.io_remark = io_remark;
    }

    public String user_id;
    public String io_type;
    public String io_wallet;
    public String io_name;
    public String io_money;
    public String io_date;
    public String io_logo;
    public String io_remark;

}
