package com.bcm.account.bmobbean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Init on 2017/4/12.
 */

public class myUser extends BmobUser {
    private String age;
    private String gender;
    private String signDetails;
    private String location;
    private BmobFile pic;

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return this.age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return this.gender;
    }

    public void setSignDetails(String signDetails) {
        this.signDetails = signDetails;
    }

    public String getSignDetails() {
        return this.signDetails;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public BmobFile getpic() {
        return pic;
    }

    public void setpic(BmobFile pic) {
        this.pic = pic;
    }
}
