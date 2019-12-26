package com.kotlin.freak_ec.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "test")
public class Test {
    @Id
    private Long userId = 0L;
    private String name = null;
    private String gender = null;
    private String password = null;
    private String email = null;

    @Generated(hash = 2050712570)
    public Test(Long userId, String name, String gender, String password,
                String email) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.password = password;
        this.email = email;
    }

    @Generated(hash = 372557997)
    public Test() {
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}