package com.bcm.account.bmobbean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Bean on 2017/4/26.
 */

public class ABill extends BmobObject{
    public String getWallet_type() {
        return wallet_type;
    }

    public void setWallet_type(String wallet_type) {
        this.wallet_type = wallet_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBill_type() {
        return bill_type;
    }

    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }

    public String getBill_money() {
        return bill_money;
    }

    public void setBill_money(String bill_money) {
        this.bill_money = bill_money;
    }

    public String getBill_logo() {
        return bill_logo;
    }

    public void setBill_logo(String bill_logo) {
        this.bill_logo = bill_logo;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public String wallet_type;
    public String user_id;
    public String bill_type;
    public String bill_money;
    public String bill_logo;
    public String bill_date;

    public String getBill_month() {
        return bill_month;
    }

    public void setBill_month(String bill_month) {
        this.bill_month = bill_month;
    }

    public String bill_month;
}
