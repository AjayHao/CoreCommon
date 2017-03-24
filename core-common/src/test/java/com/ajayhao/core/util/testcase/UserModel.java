package com.ajayhao.core.util.testcase;

import com.ajayhao.core.base.AbstractModel;

/**
 * Created by haozhenjie on 2016/11/22.
 */
public class UserModel extends AbstractModel{
    private String userId;
    private String userName;
    private int age;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString(){
        return super.toString();
    }
}
