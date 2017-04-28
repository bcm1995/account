package com.bcm.account.bmobbean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Bean on 2017/4/28.
 */

public class AWallet extends BmobObject {
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLoan_money() {
        return loan_money;
    }

    public void setLoan_money(String loan_money) {
        this.loan_money = loan_money;
    }

    public String getJoin_money() {
        return join_money;
    }

    public void setJoin_money(String join_money) {
        this.join_money = join_money;
    }

    public String getDebit_money() {
        return debit_money;
    }

    public void setDebit_money(String debit_money) {
        this.debit_money = debit_money;
    }

    public String getCredit_money() {
        return credit_money;
    }

    public void setCredit_money(String credit_money) {
        this.credit_money = credit_money;
    }

    public String getCash_money() {
        return cash_money;
    }

    public void setCash_money(String cash_money) {
        this.cash_money = cash_money;
    }

    public String user_id;
    public String loan_money;
    public String join_money;
    public String debit_money;
    public String credit_money;
    public String cash_money;
}
